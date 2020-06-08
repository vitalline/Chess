package com.syntech.chess.logic;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.rules.chess.DoublePawnType;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Cloneable {
    private static final Point pieceNone = new Point(-1, -1);
    private int width, height;
    private ArrayList<Point> pieces;
    private ArrayList<Point> movablePieces = new ArrayList<>();
    protected ArrayList<Move> availableMoves = new ArrayList<>();
    protected ArrayList<Move> availableCaptures = new ArrayList<>();
    private Board previousBoard = null;
    private Piece[][] board;
    private float windowWidth, windowHeight;
    private Side turnIndicator = Side.WHITE;
    private boolean displayPromotionPopup = false;
    private boolean displayResultPopup = false;
    private String status;
    private Side statusSide = Side.WHITE;
    private PieceType statusPiece = PieceType.PAWN;
    private boolean gameEnded;
    private Translation translation;
    private Point selectedPiece = new Point(-1, -1);
    private Point enPassantPointWhite = new Point(-1, -1);
    private Point enPassantPointBlack = new Point(-1, -1);
    protected int turn;
    private ArrayList<Move> moveLog = new ArrayList<>();
    private String errorMessage = null;
    private Object[] errorArguments = null;
    private static final Piece none = PieceFactory.none();

    private Board(@NotNull Piece[][] board, boolean initialize, boolean update, int turn) {
        this.translation = Translation.EN_US;
        this.turn = turn;
        height = board.length;
        width = board.length > 0 ? board[0].length : 0;
        this.board = new Piece[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                try {
                    this.board[row][col] = (Piece) board[row][col].clone();
                } catch (CloneNotSupportedException ignored) {
                    this.board[row][col] = board[row][col];
                }
            }
        }
        if (initialize) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    this.board[row][col].setPosition(row, col);
                }
            }
        }
        updatePieces();
        if (update) {
            updateMovablePieces();
        }
    }

    public Board(@NotNull Piece[][] board, boolean initialize, boolean update) {
        this(board, initialize, update, 0);
    }

    public Board(@NotNull Piece[][] board, int turn) {
        this(board, false, false, turn);
    }

    public Board(@NotNull String errorMessage, Object... errorArguments) {
        this(new Piece[0][0], false, false, 0);
        this.errorMessage = errorMessage;
        this.errorArguments = errorArguments;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board clone = (Board) super.clone();
        Board copy = new Board(board, turn);
        clone.board = copy.board;
        clone.previousBoard = previousBoard;
        clone.selectedPiece = pieceNone;
        clone.enPassantPointWhite = new Point(enPassantPointWhite);
        clone.enPassantPointBlack = new Point(enPassantPointBlack);
        //clone.moveLog = new ArrayList<>(moveLog);
        return clone;
    }

    public void recreateMoveLog() {
        moveLog = new ArrayList<>(moveLog);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
        status = getStatusConditions(getTurnSide());
    }

    public boolean display(@NotNull JImGui imGui, String name, float size) {

        float spacingX = imGui.getStyle().getItemSpacingX();
        float spacingY = imGui.getStyle().getItemSpacingY();
        float paddingX = imGui.getStyle().getFramePaddingX();
        float paddingY = imGui.getStyle().getFramePaddingY();

        imGui.getStyle().setItemSpacingX(0);
        imGui.getStyle().setItemSpacingY(0);
        imGui.getStyle().setFramePaddingX(0);
        imGui.getStyle().setFramePaddingY(0);

        boolean inputReceived = false;

        imGui.begin(name, new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
        displayLabelRow(imGui, size, 0);
        for (int row = height - 1; row >= 0; row--) {
            displayLabel(imGui, Move.getRow(row), size / 2, size, 0);
            imGui.sameLine();
            for (int col = 0; col < width; col++) {
                if (displayCell(imGui, size, row, col)) {
                    inputReceived = true;
                }
                imGui.sameLine();
            }
            displayLabel(imGui, Move.getRow(row), size / 2, size, 1);
        }
        displayLabelRow(imGui, size, 1);
        windowWidth = JImGuiGen.getWindowWidth();
        windowHeight = JImGuiGen.getWindowHeight();
        JImGuiGen.end();

        imGui.getStyle().setItemSpacingX(spacingX);
        imGui.getStyle().setItemSpacingY(spacingY);
        imGui.getStyle().setFramePaddingX(paddingX);
        imGui.getStyle().setFramePaddingY(paddingY);

        if (displayPromotionPopup) {
            imGui.openPopup("Promote");
        }

        if (displayResultPopup) {
            imGui.openPopup("Result");
        }

        if (imGui.beginPopup("Promote", JImWindowFlags.AlwaysAutoResize)) {
            for (PieceType pieceType : getSelectedPiece().getPromotionTypes()) {
                if (CellGraphics.display(imGui, getSelectedPiece().getSide(), pieceType, pieceType.getProperName(translation),
                        size, getColor(selectedPiece.x, selectedPiece.y).toSide().toColor(), -1)) {
                    getSelectedPiece().promoteTo(pieceType);
                    displayPromotionPopup = false;
                    JImGuiGen.closeCurrentPopup();
                    Move newMove = new Move(moveLog.get(turn));
                    newMove.setPromotion(pieceType);
                    updateMove(newMove);
                    advanceTurn();
                    checkStatusConditions();
                    break;
                }
            }
            JImGuiGen.endPopup();
        }

        if (imGui.beginPopup("Result", JImWindowFlags.AlwaysAutoResize)) {
            imGui.text(status);
            if (imGui.button(translation.get("action.ok"))) {
                displayResultPopup = false;
                JImGuiGen.closeCurrentPopup();
            }
            JImGuiGen.endPopup();
        }

        return inputReceived;
    }

    private boolean displayCell(JImGui imGui, float size, int row, int col) {
        if (CellGraphics.display(imGui, getPiece(row, col), getLabel(row, col), size, getColor(row, col), col * height + row)) {
            analyzeInput(row, col);
            return true;
        }
        return false;
    }

    private void displayLabel(@NotNull JImGui imGui, String label, float x, float y, int id) {
        imGui.pushStyleColor(JImStyleColors.Button, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.NONE.getColor());
        JImGui.pushID(id);
        imGui.button(label, x, y);
        JImGuiGen.popID();
        JImGuiGen.popStyleColor(3);
    }

    private void displayLabelRow(JImGui imGui, float size, int id) {
        displayLabel(imGui, "", size / 2, size / 2, id * 2);
        imGui.sameLine();
        JImGui.pushID(id);
        for (int col = 0; col < width; col++) {
            displayLabel(imGui, "" + Move.getColumn(col), size, size / 2, id);
            imGui.sameLine();
        }
        JImGuiGen.popID();
        displayLabel(imGui, "", size / 2, size / 2, id * 2 + 1);
    }

    public void displayLog(@NotNull JImGui imGui, float width, float height, float posX, float posY, int characterWidth) {
        imGui.setWindowSize("Turn Info", width, height);
        imGui.setWindowPos("Turn Info", posX, posY);
        imGui.begin("Turn Info", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.NoResize);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turn; i++) {
            String move = moveLog.get(i).toNotation(translation);
            String paddedMove;
            if (i % 2 == 0) {
                move = String.format("%d. ", i / 2 + 1) + move;
            }
            paddedMove = move;
            if (i != 0) {
                if (i % 2 == 0) {
                    paddedMove = "   " + paddedMove;
                } else {
                    paddedMove = " " + paddedMove;
                }
            }
            if (sb.length() - sb.lastIndexOf("\n") + paddedMove.length() > characterWidth) {
                sb.append("\n");
                sb.append(move);
            } else {
                sb.append(paddedMove);
            }
        }
        String result = getResultString();
        if (result.length() > 1) {
            if (turn % 2 == 0) {
                result = "   " + result;
            } else {
                result = " " + result;
            }
            if (sb.length() - sb.lastIndexOf("\n") + result.length() > characterWidth) {
                sb.append("\n");
                sb.append(getResultString());
            } else {
                sb.append(result);
            }
        }
        imGui.text(sb.toString());
        JImGuiGen.end();
    }

    public float getWindowWidth() {
        return windowWidth;
    }

    public float getWindowHeight() {
        return windowHeight;
    }

    private void analyzeInput(int row, int col) {
        if (isSelected(row, col)) {
            deselectPiece();
        } else {
            if (selectedPieceIsValid()
                    && (Move.contains(availableMoves, row, col)
                    || Move.contains(availableCaptures, row, col))) {
                moveAndCheckStatusConditions(selectedPiece.x, selectedPiece.y, row, col);
            } else {
                selectPiece(row, col);
            }
        }
    }

    protected void move(int startRow, int startCol, int endRow, int endCol) {
        move(startRow, startCol, endRow, endCol, true);
    }

    protected void move(int startRow, int startCol, int endRow, int endCol, boolean moveDataNeeded) {
        Move move = new Move(getType(startRow, startCol), startRow, startCol, endRow, endCol);
        if (moveDataNeeded) {
            move.setData(this);
        }
        try {
            previousBoard = (Board) clone();
        } catch (CloneNotSupportedException ignore) {
            previousBoard = null;
        }
        Piece piece = getPiece(startRow, startCol);
        Point enPassantPoint = getEnPassantPoint(getTurnSide().getOpponent());
        Piece enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPoint.x == endRow && enPassantPoint.y == endCol
                && (piece.getType() == PieceType.PAWN)
                && startCol != endCol) {
            placePiece(PieceFactory.cell(), endRow + MovementRules.getPawnMoveDirection(enPassantPiece.getSide()), endCol);
            move.setCaptureFlag();
        }
        enPassantPoint = getEnPassantPoint(getTurnSide());
        enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPiece.getType() == PieceType.EMPTY) {
            placePiece(PieceFactory.cell(), enPassantPoint);
        }
        setEnPassantPoint(getTurnSide(), pieceNone);
        if (piece.getMovementType() instanceof DoublePawnType && endRow - startRow == 2 * MovementRules.getPawnMoveDirection(piece.getSide())) {
            setEnPassantPoint(getTurnSide(), new Point(endRow - MovementRules.getPawnMoveDirection(piece.getSide()), endCol));
            placePiece(PieceFactory.piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, piece.getSide()), getEnPassantPoint(getTurnSide()));
        }
        if (piece.getType() == PieceType.KING && endCol - startCol == 2) {
            getPiece(startRow, startCol + 3).move(this, startRow, startCol + 1);
        }
        if (piece.getType() == PieceType.KING && endCol - startCol == -2) {
            getPiece(startRow, startCol - 4).move(this, startRow, startCol - 1);
        }
        piece.move(this, endRow, endCol);
        selectedPiece = new Point(endRow, endCol);
        updateMove(move);
        if (getSelectedPiece().canBePromoted()) {
            updatePieces();
            displayPromotionPopup = true;
        } else {
            advanceTurn();
        }
    }

    private void moveAndCheckStatusConditions(int startRow, int startCol, int endRow, int endCol) {
        move(startRow, startCol, endRow, endCol);
        if (!displayPromotionPopup) {
            checkStatusConditions();
        }
    }

    public void updateMove(Move newMove) {
        if (moveLog.size() > turn) {
            if (moveLog.get(turn).hasDifferentMoveData(newMove)) {
                moveLog.subList(turn, moveLog.size()).clear();
                moveLog.add(newMove);
            }
        } else {
            moveLog.add(newMove);
        }
    }

    public boolean hasPromotion() {
        return displayPromotionPopup;
    }

    public boolean canRedo() {
        return moveLog.size() > turn;
    }

    public void redo() {
        if (canRedo()) {
            Move move = moveLog.get(turn);
            move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol());
            if (getPiece(move.getEndRow(), move.getEndCol()).canBePromoted() && move.getPromotion() != PieceType.NONE) {
                getPiece(move.getEndRow(), move.getEndCol()).promoteTo(move.getPromotion());
                advanceTurn();
            }
            displayPromotionPopup = false;
            checkStatusConditions();
        }
    }

    public void simulatedRedo() {
        if (canRedo()) {
            Move move = moveLog.get(turn);
            move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
            if (getPiece(move.getEndRow(), move.getEndCol()).canBePromoted() && move.getPromotion() != PieceType.NONE) {
                getPiece(move.getEndRow(), move.getEndCol()).promoteTo(move.getPromotion());
                advanceTurn();
            }
            displayPromotionPopup = false; //not sure if we need this, since this board shouldn't be displayed anyway
        }
    }

    public void checkStatusConditions() {
        updateMovablePieces();
        status = getStatusConditions(getTurnSide());
        if (isInCheck(getTurnSide())) {
            moveLog.get(turn - 1).setCheckFlag();
        }
        if (gameEnded) {
            moveLog.get(turn - 1).setGameEndFlag();
            displayResultPopup = true;
        }
    }

    @Nullable
    private String getStatusConditions(Side side) {
        if (getAllAvailableMoves(side).size() == 0) {
            gameEnded = true;
            if (isInCheck(side)) {
                statusSide = side.getOpponent();
                statusPiece = PieceType.QUEEN;
                return translation.get("status.checkmate", side.getOpponent().getTranslationString());
            } else {
                statusSide = side.getOpponent();
                statusPiece = PieceType.KING;
                return translation.get("status.stalemate",
                        side.getOpponent().getTranslationString(),
                        side.getTranslationString());
            }
        } else {
            if (isInCheck(side)) {
                statusSide = side;
                statusPiece = PieceType.KING;
                return translation.get("status.check", side.getTranslationString());
            } else {
                statusSide = side;
                statusPiece = PieceType.PAWN;
                return null;
            }
        }
    }

    @NotNull
    private String getResultString() {
        if (gameEnded) {
            if (isInCheck(getTurnSide())) {
                if (getTurnSide() == Side.WHITE) {
                    return "0-1";
                } else {
                    return "1-0";
                }
            }
            return "1/2-1/2";
        }
        return "*";
    }

    private Point getEnPassantPoint(Side side) {
        return side == Side.WHITE ? enPassantPointWhite : side == Side.BLACK ? enPassantPointBlack : pieceNone;
    }

    private void setEnPassantPoint(Side side, Point point) {
        if (side == Side.WHITE) {
            enPassantPointWhite = point;
        }
        if (side == Side.BLACK) {
            enPassantPointBlack = point;
        }
    }

    public String getStatusString() {
        return status;
    }

    public PieceType getStatusPiece() {
        return statusPiece;
    }

    public Side getStatusSide() {
        return statusSide;
    }

    public void placePiece(Piece piece, int row, int col) {
        try {
            board[row][col] = piece;
            getPiece(row, col).setPosition(row, col);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public void placePiece(Piece piece, @NotNull Point pos) {
        placePiece(piece, pos.x, pos.y);
    }

    private Color getColor(int row, int col) {
        if (nothingIsSelected()) {
            return (width - col + row) % 2 != 0 ? Color.WHITE : Color.BLACK;
        } else if (!selectedPieceIsValid() && movablePieces.contains(new Point(row, col))) {
            return (width - col + row) % 2 != 0 ? Color.MOVABLE_WHITE : Color.MOVABLE_BLACK;
        } else if (isSelected(row, col)) {
            return (width - col + row) % 2 != 0 ? Color.SELECTED_WHITE : Color.SELECTED_BLACK;
        } else if (Move.contains(availableMoves, row, col)) {
            return (width - col + row) % 2 != 0 ? Color.MOVE_WHITE : Color.MOVE_BLACK;
        } else if (Move.contains(availableCaptures, row, col)) {
            return (width - col + row) % 2 != 0 ? Color.CAPTURE_WHITE : Color.CAPTURE_BLACK;
        }
        return (width - col + row) % 2 != 0 ? Color.WHITE : Color.BLACK;
    }

    private void updatePieces() {
        pieces = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!isFree(row, col)) {
                    getPiece(row, col).resetMoveCache();
                    pieces.add(new Point(row, col));
                }
            }
        }
    }

    private void updateMovablePieces() {
        movablePieces = new ArrayList<>();
        for (Point p : this.pieces) {
            if (getSide(p.x, p.y) == getTurnSide()) {
                ArrayList<Move> moves = new ArrayList<>();
                moves.addAll(getAvailableMoves(p.x, p.y));
                moves.addAll(getAvailableCaptures(p.x, p.y));
                if (!moves.isEmpty()) {
                    movablePieces.add(p);
                }
            }
        }
    }

    @NotNull
    private ArrayList<Move> getAllAvailableMovesWithoutSpecialRules(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : this.pieces) {
            if (getSide(p.x, p.y) == side) {
                moves.addAll(getPiece(p.x, p.y).getAvailableMovesWithoutSpecialRules(this));
            }
        }
        return moves;
    }

    @NotNull
    private ArrayList<Move> getAllAvailableCapturesWithoutSpecialRules(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : this.pieces) {
            if (getSide(p.x, p.y) == side) {
                moves.addAll(getPiece(p.x, p.y).getAvailableCapturesWithoutSpecialRules(this));
            }
        }
        return moves;
    }

    public ArrayList<Move> getAllAvailableMoves(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : this.pieces) {
            if (getSide(p.x, p.y) == side) {
                moves.addAll(getPiece(p.x, p.y).getAvailableMoves(this));
                moves.addAll(getPiece(p.x, p.y).getAvailableCaptures(this));
            }
        }
        return MovePriorities.topPriorityMoves(moves);
    }

    public boolean isFree(int row, int col) {
        return getPiece(row, col).getType() == PieceType.EMPTY;
    }

    public Side getSide(int row, int col) {
        return getPiece(row, col).getSide();
    }

    public PieceType getType(int row, int col) {
        return getPiece(row, col).getType();
    }

    public Piece getPiece(int row, int col) {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return none;
        }
    }

    private String getLabel(int row, int col) {
        return getPiece(row, col).getLabel(translation);
    }

    private Piece getSelectedPiece() {
        return getPiece(selectedPiece.x, selectedPiece.y);
    }

    protected void selectPiece(int row, int col) {
        getPiece(row, col).getType();
        selectedPiece = new Point(row, col);
        availableMoves = getAvailableMoves(row, col);
        availableCaptures = getAvailableCaptures(row, col);
    }

    @NotNull
    private ArrayList<Move> getAvailableMoves(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableMoves = getPiece(row, col).getAvailableMoves(this);
            availableMoves = topPriorityMoves(availableMoves, row, col);
            return availableMoves;
        }
        return new ArrayList<>();
    }

    @NotNull
    private ArrayList<Move> getAvailableCaptures(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableCaptures = getPiece(row, col).getAvailableCaptures(this);
            availableCaptures = topPriorityMoves(availableCaptures, row, col);
            return availableCaptures;
        }
        return new ArrayList<>();
    }

    @NotNull
    private ArrayList<Move> topPriorityMoves(ArrayList<Move> moves, int row, int col) {
        ArrayList<Move> allAvailableMoves = getAllAvailableMoves(getSide(row, col));
        int topPriority = MovePriorities.getTopPriority(allAvailableMoves);
        if (MovePriorities.getTopPriority(moves) < topPriority) {
            moves = new ArrayList<>();
        }
        return MovePriorities.topPriorityMoves(moves);
    }

    private void deselectPiece() {
        selectedPiece = pieceNone;
        availableMoves = new ArrayList<>();
        availableCaptures = new ArrayList<>();
    }

    private boolean nothingIsSelected() {
        return selectedPiece.equals(pieceNone);
    }

    private boolean selectedPieceIsValid() {
        return movablePieces.contains(selectedPiece);
    }

    private boolean isSelected(int row, int col) {
        return selectedPiece.equals(new Point(row, col)) && selectedPieceIsValid();
    }

    public int getTurn() {
        return turn;
    }

    public Side getTurnSide() {
        return turnIndicator;
    }

    private void advanceTurn() {
        deselectPiece();
        ++turn;
        turnIndicator = turnIndicator.getOpponent();
        updatePieces();
    }

    public Board getMoveResultWithoutPromotion(Move move) {
        Board moveResult = new Board(board, turn);
        if (move != null) {
            moveResult.move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
        }
        return moveResult;
    }

    public boolean isInCheck(@NotNull Side side) {
        boolean kingIsPresent = false;
        for (Point p : pieces) {
            if (getType(p.x, p.y) == PieceType.KING && getSide(p.x, p.y) == side) {
                kingIsPresent = true;
            }
        }
        if (!kingIsPresent) {
            return true;
        }
        ArrayList<Move> captures = getAllAvailableCapturesWithoutSpecialRules(side.getOpponent());
        for (Move capture : captures) {
            if (getType(capture.getEndRow(), capture.getEndCol()) == PieceType.KING) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public ArrayList<Move> excludeMovesThatLeaveKingInCheck(Side side, @NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (!getMoveResultWithoutPromotion(move).isInCheck(side)) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public Board getPreviousBoard() {
        if (previousBoard != null) {
            previousBoard.windowWidth = windowWidth;
            previousBoard.windowHeight = windowHeight;
        }
        return previousBoard;
    }

    protected Move getRandomMove() {
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : movablePieces) {
            moves.addAll(getAvailableMoves(p.x, p.y));
            moves.addAll(getAvailableCaptures(p.x, p.y));
        }
        if (moves.size() > 0) {
            return moves.get((int) (Math.random() * moves.size()));
        }
        return null;
    }

    public void makeRandomMove() {
        Move move = getRandomMove();
        if (move != null) {
            updateMove(move);
            redo();
        }
    }

    public Board getInitialBoard() {
        if (getPreviousBoard() != null) {
            return getPreviousBoard().getInitialBoard();
        } else {
            return this;
        }
    }

    public void redoAll() {
        while (canRedo()) {
            redo();
        }
    }

    public ArrayList<Piece> getAllPieces(Side side) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Point point : this.pieces) {
            if (getSide(point.x, point.y) == side) {
                pieces.add(getPiece(point.x, point.y));
            }
        }
        return pieces;
    }

    public void saveToPGN(@NotNull String path, Setup setup) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("[Event \"-\"]\n");
        printWriter.print("[Site \"-\"]\n");
        printWriter.printf("[Date \"%s\"]\n", java.time.LocalDate.now().toString().replace('-', '.'));
        printWriter.print("[Round \"1\"]\n");
        printWriter.print("[White \"Player 1\"]\n");
        printWriter.print("[Black \"Player 2\"]\n");
        printWriter.printf("[Result \"%s\"]\n", getResultString());
        if (setup != Setup.CHESS) {
            printWriter.printf("[Variant \"%s\"]\n", setup.getGameType(Translation.EN_US));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < turn; i++) {
            if (i % 14 == 0) {
                sb.append("\n");
            }
            String move = moveLog.get(i).toPGN() + ' ';
            if (i % 2 == 0) {
                move = String.format("%d. ", i / 2 + 1) + move;
            }
            sb.append(move);
        }
        String result = getResultString();
        if (result.length() > 1) {
            sb.append(result);
        }
        printWriter.print(sb.toString());
        printWriter.close();
    }

    @NotNull
    public static Board getGameFromPGN(@NotNull String game) {
        long count = game.chars().filter(ch -> ch == '\0').count();
        if (count > 0) {
            return new Board("error.pgn.invalid_file");
        }
        game = game.replaceAll("(;[^}\\r\\n]+?)?\\r?\\n", " ");
        game = game.replaceAll("\\$\\d+", " ");
        game = game.replaceAll("\\r", " ");
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(game.split("[\\[\\]]|\\{.+?}")));
        tokens.removeIf(String::isEmpty);
        Setup setup = Setup.getSetupFromPGN(game);
        if (setup == null) {
            return new Board("error.pgn.invalid_variant");
        }
        Board board = setup.getBoard();
        for (String token : tokens) {
            if (!token.matches(".+ \".*\"")) {
                ArrayList<String> moves = new ArrayList<>(Arrays.asList(token.split("\\d+\\.|\\s|0-1|1-0|1/2-1/2")));
                moves.removeIf(String::isEmpty);
                for (String moveString : moves) {
                    Move move = Move.fromPGN(moveString, board);
                    if (move != null) {
                        board.updateMove(move);
                        board.redo();
                    } else {
                        if (board.getTurn() > 0) {
                            return new Board("error.pgn.invalid_move", board.getTurn());
                        } else {
                            return new Board("error.pgn.invalid_file");
                        }
                    }
                }
            }
        }
        return board;
    }

    public boolean isErroneous() {
        return board.length == 0;
    }

    public String getErrorMessage(Translation translation) {
        return isErroneous() ? translation.get(errorMessage, errorArguments) : "";
    }
}
