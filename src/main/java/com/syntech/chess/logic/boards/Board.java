package com.syntech.chess.logic.boards;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.*;
import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.rules.chess.DoublePawnType;
import com.syntech.chess.rules.chess.KnightType;
import com.syntech.chess.rules.chess.QueenType;
import com.syntech.chess.text.Translation;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Cloneable {
    private static final Point pieceNone = new Point(-1, -1);
    private final int width, height;
    protected final boolean priority;
    protected ArrayList<Point> whitePieces;
    protected ArrayList<Point> blackPieces;
    protected Point whiteKing = null;
    protected Point blackKing = null;
    protected Boolean whiteKingIsInCheck = null;
    protected Boolean blackKingIsInCheck = null;
    private ArrayList<Point> movablePieces = new ArrayList<>();
    protected ArrayList<Move> availableMoves = new ArrayList<>();
    protected ArrayList<Move> availableCaptures = new ArrayList<>();
    protected ArrayList<Move> allAvailableWhiteMoves = null;
    protected ArrayList<Move> allAvailableBlackMoves = null;
    private Side[][] sideCache;
    private PieceType[][] typeCache;
    private Board previousBoard = null;
    protected Piece[][] board;
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

    private Board(@NotNull Piece @NotNull [] @NotNull [] board, Boolean priority, Boolean initialize, Boolean update, int turn) {
        this.translation = Translation.EN_US;
        this.priority = priority;
        this.turn = turn;
        height = board.length;
        width = board.length > 0 ? board[0].length : 0;
        this.board = new Piece[height][width];
        this.sideCache = new Side[height][width];
        this.typeCache = new PieceType[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                try {
                    this.board[row][col] = (Piece) board[row][col].clone();
                } catch (CloneNotSupportedException ignored) {
                    this.board[row][col] = board[row][col];
                }
                this.sideCache[row][col] = board[row][col].getSide();
                this.typeCache[row][col] = board[row][col].getType();
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

    public Board(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        this(board, priority, initialize, update, 0);
    }

    public Board(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        this(board, priority, false, false, turn);
    }

    public Board(@NotNull String errorMessage, Object... errorArguments) {
        this(new Piece[0][0], false, false, false, 0);
        this.errorMessage = errorMessage;
        this.errorArguments = errorArguments;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board clone = (Board) super.clone();
        Board copy = new Board(board, priority, turn);
        clone.board = copy.board;
        clone.previousBoard = previousBoard;
        clone.selectedPiece = pieceNone;
        clone.enPassantPointWhite = new Point(enPassantPointWhite);
        clone.enPassantPointBlack = new Point(enPassantPointBlack);
        clone.sideCache = copy.sideCache;
        clone.typeCache = copy.typeCache;
        clone.whiteKing = copy.whiteKing;
        clone.blackKing = copy.blackKing;
        clone.whiteKingIsInCheck = null;
        clone.blackKingIsInCheck = null;
        clone.allAvailableWhiteMoves = null;
        clone.allAvailableBlackMoves = null;
        //clone.moveLog = new ArrayList<>(moveLog);
        return clone;
    }

    public void recreateMoveLog() {
        moveLog = new ArrayList<>(moveLog);
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

    public boolean hasPriority() {
        return priority;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
        status = getStatusConditions(getTurnSide());
    }

    public boolean display(String name, float size) {

        float spacingX = ImGui.getStyle().getItemSpacingX();
        float spacingY = ImGui.getStyle().getItemSpacingY();
        float paddingX = ImGui.getStyle().getFramePaddingX();
        float paddingY = ImGui.getStyle().getFramePaddingY();

        ImGui.getStyle().setItemSpacing(0, 0);
        ImGui.getStyle().setFramePadding(0, 0);

        ImGui.begin(name, new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.AlwaysAutoResize);

        boolean inputReceived = displayBoard(size);

        windowWidth = ImGui.getWindowWidth();
        windowHeight = ImGui.getWindowHeight();
        ImGui.end();

        ImGui.getStyle().setItemSpacing(spacingX, spacingY);
        ImGui.getStyle().setFramePadding(paddingX, paddingY);

        if (displayPromotionPopup) {
            ImGui.openPopup("Promote");
        }

        if (displayResultPopup) {
            ImGui.openPopup("Result");
        }

        if (ImGui.beginPopup("Promote", ImGuiWindowFlags.AlwaysAutoResize)) {
            for (PieceType pieceType : getSelectedPiece().getPromotionTypes()) {
                if (CellGraphics.display(getSelectedPiece().getSide(), pieceType, pieceType.getProperName(translation),
                        size, getColor(selectedPiece.x, selectedPiece.y).toSide().toColor(), -1)) {
                    getSelectedPiece().promoteTo(pieceType);
                    updatePiece(selectedPiece.x, selectedPiece.y);
                    displayPromotionPopup = false;
                    ImGui.closeCurrentPopup();
                    Move newMove = new Move(moveLog.get(turn));
                    newMove.setPromotion(pieceType);
                    updateMove(newMove);
                    advanceTurn();
                    checkStatusConditions();
                    break;
                }
            }
            ImGui.endPopup();
        }

        if (ImGui.beginPopup("Result", ImGuiWindowFlags.AlwaysAutoResize)) {
            ImGui.text(status);
            if (ImGui.button(translation.get("action.ok"))) {
                displayResultPopup = false;
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        return inputReceived;
    }

    protected boolean displayBoard(float size) {

        boolean inputReceived = false;

        displayLabelRow(size, 0);
        for (int row = getHeight() - 1; row >= 0; row--) {
            displayLabel(Move.getRow(row), size / 2, size, 0);
            ImGui.sameLine();
            for (int col = 0; col < getWidth(); col++) {
                if (displayCell(size, row, col)) {
                    inputReceived = true;
                }
                ImGui.sameLine();
            }
            displayLabel(Move.getRow(row), size / 2, size, 1);
        }
        displayLabelRow(size, 1);

        return inputReceived;
    }

    protected boolean displayCell(float size, int row, int col) {
        if (CellGraphics.display(getPiece(row, col), getLabel(row, col), size, getColor(row, col), col * getHeight() + row)) {
            analyzeInput(row, col);
            return true;
        }
        return false;
    }

    protected void displayLabel(String label, float x, float y, int id) {
        ImGui.pushStyleColor(ImGuiCol.Button, Color.NONE.getColor());
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, Color.NONE.getColor());
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, Color.NONE.getColor());
        ImGui.pushID(id);
        ImGui.button(label, x, y);
        ImGui.popID();
        ImGui.popStyleColor(3);
    }

    protected void displayLabelRow(float size, int id) {
        displayLabel("", size / 2, size / 2, id * 2);
        ImGui.sameLine();
        for (int col = 0; col < getWidth(); col++) {
            displayLabel("" + Move.getColumn(col), size, size / 2, id);
            ImGui.sameLine();
        }
        displayLabel("", size / 2, size / 2, id * 2 + 1);
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

    protected void move(int startRow, int startCol, int endRow, int endCol, boolean dataNeeded) {
        Move move = new Move(getType(startRow, startCol), startRow, startCol, endRow, endCol);
        if (dataNeeded) {
            move.setData(this);
            try {
                previousBoard = (Board) clone();
            } catch (CloneNotSupportedException ignore) {
                previousBoard = null;
            }
        } else {
            previousBoard = null;
        }
        Piece piece = getPiece(startRow, startCol);
        Point enPassantPoint = getEnPassantPoint(getTurnSide().getOpponent());
        Piece enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPoint.x == endRow && enPassantPoint.y == endCol
                && (piece.getType() == PieceType.PAWN)
                && startCol != endCol) {
            Point captured = new Point(endRow + MovementRules.getPawnMoveDirection(enPassantPiece.getSide()), endCol);
            placePiece(PieceFactory.cell(), captured.x, captured.y);
            move.setCaptureFlag();
        }
        enPassantPoint = getEnPassantPoint(getTurnSide());
        enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPiece.getType() == PieceType.EMPTY) {
            placePiece(PieceFactory.cell(), enPassantPoint.x, enPassantPoint.y);
        }
        setEnPassantPoint(getTurnSide(), pieceNone);
        if (piece.getMovementType() instanceof DoublePawnType && endRow - startRow == 2 * MovementRules.getPawnMoveDirection(piece.getSide())) {
            setEnPassantPoint(getTurnSide(), new Point(endRow - MovementRules.getPawnMoveDirection(piece.getSide()), endCol));
            placePiece(PieceFactory.piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, piece.getSide()), getEnPassantPoint(getTurnSide()).x, getEnPassantPoint(getTurnSide()).y);
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
        if (getSelectedPiece().canBePromoted(this)) {
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

    public boolean canRedo() {
        return moveLog.size() > turn;
    }

    public void redo() {
        if (canRedo()) {
            Move move = moveLog.get(turn);
            move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol());
            promoteIfNeeded(move);
            checkStatusConditions();
        }
    }

    public void simulatedRedo() {
        if (canRedo()) {
            Move move = moveLog.get(turn);
            move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
            promoteIfNeeded(move);
        }
    }

    private void promoteIfNeeded(@NotNull Move move) {
        if (getPiece(move.getEndRow(), move.getEndCol()).canBePromoted(this) && move.getPromotion() != PieceType.NONE) {
            getPiece(move.getEndRow(), move.getEndCol()).promoteTo(move.getPromotion());
            updatePiece(move.getEndRow(), move.getEndCol());
            advanceTurn();
        }
        displayPromotionPopup = false;
    }

    public void checkStatusConditions() {
        whiteKingIsInCheck = null;
        blackKingIsInCheck = null;
        updateMovablePieces();
        status = getStatusConditions(getTurnSide());
        if (isInCheck(getTurnSide())) {
            if (!moveLog.isEmpty()) moveLog.get(turn - 1).setCheckFlag();
        }
        if (gameEnded) {
            if (!moveLog.isEmpty()) moveLog.get(turn - 1).setGameEndFlag();
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
    public String getResultString() {
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
            updatePiece(row, col);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    protected boolean cellColorIsWhite(int row, int col) {
        return (getWidth() - col + row) % 2 != 0;
    }

    private Color getColor(int row, int col) {
        if (nothingIsSelected()) {
            return cellColorIsWhite(row, col) ? Color.WHITE : Color.BLACK;
        } else if (!selectedPieceIsValid() && movablePieces.contains(new Point(row, col))) {
            return cellColorIsWhite(row, col) ? Color.MOVABLE_WHITE : Color.MOVABLE_BLACK;
        } else if (isSelected(row, col)) {
            return cellColorIsWhite(row, col) ? Color.SELECTED_WHITE : Color.SELECTED_BLACK;
        } else if (Move.contains(availableMoves, row, col)) {
            return cellColorIsWhite(row, col) ? Color.MOVE_WHITE : Color.MOVE_BLACK;
        } else if (Move.contains(availableCaptures, row, col)) {
            return cellColorIsWhite(row, col) ? Color.CAPTURE_WHITE : Color.CAPTURE_BLACK;
        }
        return cellColorIsWhite(row, col) ? Color.WHITE : Color.BLACK;
    }

    public void updatePiece(int row, int col) {
        if (whiteKing != null && whiteKing.x == row && whiteKing.y == col) whiteKing = null;
        if (blackKing != null && blackKing.x == row && blackKing.y == col) blackKing = null;
        Piece piece = getPiece(row, col);
        sideCache[row][col] = piece.getSide();
        typeCache[row][col] = piece.getType();
    }

    public void addPiece(int row, int col) {
        if (getSide(row, col) == Side.WHITE) {
            whitePieces.add(new Point(row, col));
            if (getType(row, col) == PieceType.KING) whiteKing = new Point(row, col);
        }
        if (getSide(row, col) == Side.BLACK) {
            blackPieces.add(new Point(row, col));
            if (getType(row, col) == PieceType.KING) blackKing = new Point(row, col);
        }
    }

    public void updatePieces() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (!isFree(row, col)) {
                    addPiece(row, col);
                }
            }
        }
        whiteKingIsInCheck = null;
        blackKingIsInCheck = null;
        allAvailableWhiteMoves = null;
        allAvailableBlackMoves = null;
    }

    protected void updateMovablePieces() {
        movablePieces = new ArrayList<>();
        for (Point p : getPositions(getTurnSide())) {
            ArrayList<Move> moves = new ArrayList<>();
            moves.addAll(getAvailableMoves(p.x, p.y));
            moves.addAll(getAvailableCaptures(p.x, p.y));
            if (!moves.isEmpty()) {
                movablePieces.add(p);
            }
        }
    }

    protected ArrayList<MovementType> checkMoves(Side side) {
        return new ArrayList<>(Arrays.asList(new QueenType(side), new KnightType(side)));
    }

    private boolean isInCheck(@NotNull Piece king) {
        Side side = king.getSide();
        if (side == Side.WHITE && whiteKingIsInCheck != null) return whiteKingIsInCheck;
        if (side == Side.BLACK && blackKingIsInCheck != null) return blackKingIsInCheck;
        for (MovementType movement : checkMoves(side)) {
            for (Move detect : king.getAvailableCapturesWithoutSpecialRules(this, movement)) {
                Piece piece = getPiece(detect.getEndRow(), detect.getEndCol());
                for (Move move : piece.getAvailableCapturesWithoutSpecialRules(this)) {
                    if (getPiece(move.getEndRow(), move.getEndCol()) == king) {
                        if (piece instanceof LevellingForcedPiece && king instanceof LevellingForcedPiece) {
                            if (((LevellingForcedPiece) piece).getPowerLevel() <= ((LevellingForcedPiece) king).getResistanceLevel()) {
                                break;
                            }
                        }
                        if (side == Side.WHITE) whiteKingIsInCheck = true;
                        if (side == Side.BLACK) blackKingIsInCheck = true;
                        return true;
                    }
                }
            }
        }
        if (side == Side.WHITE) whiteKingIsInCheck = false;
        if (side == Side.BLACK) blackKingIsInCheck = false;
        return false;
    }

    public ArrayList<Move> getAllAvailableMoves(Side side) {
        if (side == Side.WHITE && allAvailableWhiteMoves != null) return allAvailableWhiteMoves;
        if (side == Side.BLACK && allAvailableBlackMoves != null) return allAvailableBlackMoves;
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : getPositions(side)) {
            moves.addAll(getPiece(p.x, p.y).getAvailableMoves(this));
            moves.addAll(getPiece(p.x, p.y).getAvailableCaptures(this));
        }
        if (priority) moves = MovePriorities.topPriorityMoves(moves);
        if (side == Side.WHITE) allAvailableWhiteMoves = moves;
        if (side == Side.BLACK) allAvailableBlackMoves = moves;
        return moves;
    }

    public boolean isFree(int row, int col) {
        return getType(row, col) == PieceType.EMPTY;
    }

    public boolean isCapturable(int row, int col, @NotNull Side by) {
        Side side = getSide(row, col);
        return side == by.getOpponent() || side == Side.NEUTRAL;
    }

    public Side getSide(int row, int col) {
        try {
            return sideCache[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return Side.NONE;
        }
    }

    public PieceType getType(int row, int col) {
        try {
            return typeCache[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return PieceType.NONE;
        }
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

    protected Piece getSelectedPiece() {
        return getPiece(selectedPiece.x, selectedPiece.y);
    }

    protected void selectPiece(int row, int col) {
        selectedPiece = new Point(row, col);
        availableMoves = getAvailableMoves(row, col);
        availableCaptures = getAvailableCaptures(row, col);
    }

    @NotNull
    private ArrayList<Move> getAvailableMoves(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableMoves = getPiece(row, col).getAvailableMoves(this);
            if (priority) availableMoves = topPriorityMoves(availableMoves, row, col);
            return availableMoves;
        }
        return new ArrayList<>();
    }

    @NotNull
    private ArrayList<Move> getAvailableCaptures(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableCaptures = getPiece(row, col).getAvailableCaptures(this);
            if (priority) availableCaptures = topPriorityMoves(availableCaptures, row, col);
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

    protected void advanceTurn() {
        deselectPiece();
        ++turn;
        turnIndicator = turnIndicator.getOpponent();
        updatePieces();
    }

    public Board getMoveResultWithoutPromotion(Move move) {
        Board moveResult = null;
        try {
            moveResult = this.getClass().getDeclaredConstructor(Piece[][].class, Boolean.class, Integer.class).newInstance(board, priority, turn);
            if (move != null) {
                moveResult.move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return moveResult;
    }

    public int getPromotionCoordinate(@NotNull Point position) {
        return position.x;
    }

    public boolean isInCheck(@NotNull Side side) {
        Point king = null;
        if (side == Side.WHITE) {
            king = whiteKing;
        }
        if (side == Side.BLACK) {
            king = blackKing;
        }
        if (king == null) return true;
        return isInCheck(getPiece(king.x, king.y));
    }

    public boolean isSafe(@NotNull Side side) {
        return !isInCheck(side);
    }

    @NotNull
    public ArrayList<Move> excludeMovesThatLeaveKingInCheck(Side side, @NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (getMoveResultWithoutPromotion(move).isSafe(side)) {
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

    public ArrayList<Point> getPositions(Side side) {
        if (side == Side.WHITE) return whitePieces;
        if (side == Side.BLACK) return blackPieces;
        return new ArrayList<>();
    }

    public ArrayList<Piece> getAllPieces(Side side) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Point point : getPositions(side)) {
            pieces.add(getPiece(point.x, point.y));
        }
        return pieces;
    }

    public ArrayList<Move> getMoveLog() {
        return new ArrayList<>(moveLog);
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
        printWriter.print(sb);
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
