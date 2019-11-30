package com.syntech.chess.logic;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementRules;
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
import java.util.ArrayList;

public class Board implements Cloneable {
    private static final Point pieceNone = new Point(-1, -1);
    protected int width, height;
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
    private ArrayList<String> moveLog = new ArrayList<>();

    public Board(@NotNull Piece[][] board, Translation translation, boolean initialize) {
        this(board, translation);
        if (initialize) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    this.board[row][col].setPosition(row, col);
                }
            }
        }
    }

    public Board(@NotNull Piece[][] board, Translation translation) {
        this.translation = translation;
        height = board.length;
        width = board[0].length;
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
    }

    @NotNull
    private static String getRow(int row) {
        return String.valueOf(row + 1);
    }

    @NotNull
    private static String getColumn(int col) {
        return String.valueOf((char) (col + 'a'));
    }

    @NotNull
    public static String getCoordinates(@NotNull Point position) {
        return getColumn(position.y) + getRow(position.x);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board clone = (Board) super.clone();
        Board copy = new Board(board, translation);
        clone.board = copy.board;
        clone.previousBoard = previousBoard;
        clone.enPassantPointWhite = new Point(enPassantPointWhite);
        clone.enPassantPointBlack = new Point(enPassantPointBlack);
        clone.moveLog = new ArrayList<>(moveLog);
        return clone;
    }

    public Piece[][] getBoard() {
        return board;
    }

    protected Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
        status = getStatusConditions(turnIndicator);
    }

    public void display(@NotNull JImGui imGui, String name, float size) {
        float spacingX = imGui.getStyle().getItemSpacingX();
        float spacingY = imGui.getStyle().getItemSpacingY();
        float paddingX = imGui.getStyle().getFramePaddingX();
        float paddingY = imGui.getStyle().getFramePaddingY();
        imGui.getStyle().setItemSpacingX(0);
        imGui.getStyle().setItemSpacingY(0);
        imGui.getStyle().setFramePaddingX(0);
        imGui.getStyle().setFramePaddingY(0);
        imGui.begin(name, new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
        displayLabelRow(imGui, size);
        for (int row = height - 1; row >= 0; row--) {
            displayLabel(imGui, getRow(row), size / 2, size);
            imGui.sameLine();
            for (int col = 0; col < width; col++) {
                displayCell(imGui, size, row, col);
                imGui.sameLine();
            }
            displayLabel(imGui, getRow(row), size / 2, size);
        }
        displayLabelRow(imGui, size);
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
                    appendToLog(pieceType.getShortNameTag());
                    displayPromotionPopup = false;
                    JImGuiGen.closeCurrentPopup();
                    advanceTurn();
                    checkStatusConditions();
                    break;
                }
            }
            JImGuiGen.endPopup();
        }
        if (imGui.beginPopup("Result", JImWindowFlags.AlwaysAutoResize)) {
            imGui.text(status);
            if (imGui.button("OK")) {
                displayResultPopup = false;
                JImGuiGen.closeCurrentPopup();
            }
            JImGuiGen.endPopup();
        }
    }

    private void displayCell(JImGui imGui, float size, int row, int col) {
        if (CellGraphics.display(imGui, getPiece(row, col), getLabel(row, col), size, getColor(row, col), col * height + row)) {
            analyzeMove(row, col);
        }
    }

    private void displayLabel(@NotNull JImGui imGui, String label, float x, float y) {
        imGui.pushStyleColor(JImStyleColors.Button, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.NONE.getColor());
        imGui.button(label, x, y);
        JImGuiGen.popStyleColor(3);
    }

    private void displayLabelRow(JImGui imGui, float size) {
        displayLabel(imGui, "", size / 2, size / 2);
        imGui.sameLine();
        for (int col = 0; col < width; col++) {
            displayLabel(imGui, "" + getColumn(col), size, size / 2);
            imGui.sameLine();
        }
        displayLabel(imGui, "", size / 2, size / 2);
    }

    public void displayLog(@NotNull JImGui imGui, int width, int height, int posX, int posY) {
        imGui.setWindowSize("Turn Info", width, height);
        imGui.setWindowPos("Turn Info", posX, posY);
        imGui.begin("Turn Info", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.NoResize);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < moveLog.size(); i++) {
            if (i % 2 == 0) {
                sb.append(i / 2 + 1);
                sb.append(". ");
            }
            String[] move = moveLog.get(i).split("\\|");
            for (String s : move) {
                sb.append(translation.get(s));
            }
            if (i % 10 == 9) {
                sb.append("\n");
            } else if (i % 2 == 1) {
                sb.append('\t');
            } else {
                sb.append(' ');
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

    private void analyzeMove(int row, int col) {
        if (isSelected(row, col)) {
            deselectPiece();
        } else if (getTurnSide() == getSide(row, col) && getType(row, col) != PieceType.EMPTY) {
            selectPiece(row, col);
        } else if (Move.contains(availableMoves, row, col) || Move.contains(availableCaptures, row, col)) {
            moveLogAndCheckStatusConditions(selectedPiece.x, selectedPiece.y, row, col);
        }
    }

    protected void move(int fromrow, int fromcol, int torow, int tocol) {
        try {
            previousBoard = (Board) clone();
        } catch (CloneNotSupportedException ignore) {
            previousBoard = null;
        }
        Piece piece = getPiece(fromrow, fromcol);
        Point enPassantPoint = getEnPassantPoint(getTurnSide().getOpponent());
        Piece enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPoint.x == torow && enPassantPoint.y == tocol
                && (piece.getType() == PieceType.PAWN)
                && fromcol != tocol) {
            placePiece(PieceFactory.cell(), torow + MovementRules.getPawnMoveDirection(enPassantPiece.getSide()), tocol);
        }
        enPassantPoint = getEnPassantPoint(getTurnSide());
        if (enPassantPiece.getType() == PieceType.EMPTY) {
            placePiece(PieceFactory.cell(), enPassantPoint);
        }
        setEnPassantPoint(getTurnSide(), pieceNone);
        if (piece.getMovementType() instanceof DoublePawnType && torow - fromrow == 2 * MovementRules.getPawnMoveDirection(piece.getSide())) {
            setEnPassantPoint(getTurnSide(), new Point(torow - MovementRules.getPawnMoveDirection(piece.getSide()), tocol));
            placePiece(PieceFactory.piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, piece.getSide()), getEnPassantPoint(getTurnSide()));
        }
        if (piece.getType() == PieceType.KING && tocol - fromcol == 2) {
            getPiece(fromrow, fromcol + 3).move(this, fromrow, fromcol + 1);
        }
        if (piece.getType() == PieceType.KING && tocol - fromcol == -2) {
            getPiece(fromrow, fromcol - 4).move(this, fromrow, fromcol - 1);
        }
        piece.move(this, torow, tocol);
        selectedPiece = new Point(torow, tocol);
        if (getSelectedPiece().canBePromoted()) {
            displayPromotionPopup = true;
        } else {
            advanceTurn();
        }
    }

    private void moveLogAndCheckStatusConditions(int fromrow, int fromcol, int torow, int tocol) {
        String move = getMove(fromrow, fromcol, torow, tocol);
        move(fromrow, fromcol, torow, tocol);
        moveLog.add(move);
        if (!displayPromotionPopup) {
            checkStatusConditions();
        }
    }

    @NotNull
    private String getMove(int fromrow, int fromcol, int torow, int tocol) {
        if (getType(fromrow, fromcol) == PieceType.KING) {
            if (tocol - fromcol == 2) {
                return "O-O";
            }
            if (fromcol - tocol == 2) {
                return "O-O-O";
            }
        }
        String move = getType(fromrow, fromcol).getShortNameTag();
        move += getCoordinates(new Point(fromrow, fromcol));
        move += getSide(fromrow, fromcol).getOpponent() == getSide(torow, tocol) ? "|log_capture|" : "|log_move|";
        move += getCoordinates(new Point(torow, tocol));
        return move;
    }

    private void appendToLog(String str) {
        moveLog.set(moveLog.size() - 1, moveLog.get(moveLog.size() - 1) + str);
    }

    private void checkStatusConditions() {
        status = getStatusConditions(turnIndicator);
        if (isInCheck(getTurnSide())) {
            if (gameEnded) {
                appendToLog("|log_checkmate|");
            } else {
                appendToLog("|log_check|");
            }
        }
        if (gameEnded) {
            displayResultPopup = true;
        }
    }

    @Nullable
    private String getStatusConditions(Side side) {
        if (getAllAvailableMoves(side).size() == 0 && getAllAvailableCaptures(side).size() == 0) {
            gameEnded = true;
            if (isInCheck(side)) {
                statusSide = side.getOpponent();
                statusPiece = PieceType.QUEEN;
                return String.format(translation.get("status_checkmate"), side.getOpponent().getProperName(translation));
            } else {
                statusSide = side.getOpponent();
                statusPiece = PieceType.KING;
                return String.format(translation.get("status_stalemate"),
                        side.getOpponent().getProperName(translation),
                        side.getProperName(translation));
            }
        } else {
            if (isInCheck(side)) {
                statusSide = side;
                statusPiece = PieceType.KING;
                return String.format(translation.get("status_check"), side.getProperName(translation));
            } else {
                statusSide = side;
                statusPiece = PieceType.PAWN;
                return null;
            }
        }
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

    private com.syntech.chess.graphic.Color getColor(int row, int col) {
        if (nothingIsSelected()) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.WHITE : com.syntech.chess.graphic.Color.BLACK;
        } else if (isSelected(row, col)) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.SELECTED_WHITE : com.syntech.chess.graphic.Color.SELECTED_BLACK;
        } else if (Move.contains(availableMoves, row, col)) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.MOVE_WHITE : com.syntech.chess.graphic.Color.MOVE_BLACK;
        } else if (Move.contains(availableCaptures, row, col)) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.CAPTURE_WHITE : com.syntech.chess.graphic.Color.CAPTURE_BLACK;
        }
        return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.WHITE : Color.BLACK;
    }

    @NotNull
    private ArrayList<Move> getAllAvailableMovesWithoutSpecialRules(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(getPiece(row, col).getAvailableMovesWithoutSpecialRules(this));
                }
            }
        }
        return moves;
    }

    @NotNull
    private ArrayList<Move> getAllAvailableCapturesWithoutSpecialRules(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(getPiece(row, col).getAvailableCapturesWithoutSpecialRules(this));
                }
            }
        }
        return moves;
    }

    public ArrayList<Move> getAllAvailableMoves(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(getPiece(row, col).getAvailableMoves(this));
                }
            }
        }
        return MovePriorities.topPriorityMoves(moves);
    }

    public ArrayList<Move> getAllAvailableCaptures(Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(getPiece(row, col).getAvailableCaptures(this));
                }
            }
        }
        return MovePriorities.topPriorityMoves(moves);
    }

    public boolean isFree(int row, int col) {
        return getPiece(row, col).getType() == PieceType.EMPTY && isOnBoard(row, col);
    }

    public Side getSide(int row, int col) {
        return getPiece(row, col).getSide();
    }

    public PieceType getType(int row, int col) {
        return getPiece(row, col).getType();
    }

    protected boolean isOnBoard(int row, int col) {
        return row >= 0 && col >= 0 && row < height && col < width;
    }

    public Piece getPiece(int row, int col) {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return PieceFactory.cell();
        }
    }

    private String getLabel(int row, int col) {
        return getPiece(row, col).getLabel(translation);
    }

    private Piece getSelectedPiece() {
        return getPiece(selectedPiece.x, selectedPiece.y);
    }

    protected void selectPiece(int row, int col) {
        try {
            getPiece(row, col).getType();
            selectedPiece = new Point(row, col);
            availableMoves = getSelectedPiece().getAvailableMoves(this);
            availableCaptures = getSelectedPiece().getAvailableCaptures(this);
            ArrayList<Move> allAvailableMoves = getAllAvailableMoves(getSide(row, col));
            ArrayList<Move> allAvailableCaptures = getAllAvailableCaptures(getSide(row, col));
            int topPriority = Math.max(MovePriorities.getTopPriority(allAvailableMoves), MovePriorities.getTopPriority(allAvailableCaptures));
            if (MovePriorities.getTopPriority(availableMoves) < topPriority) {
                availableMoves = new ArrayList<>();
            }
            if (MovePriorities.getTopPriority(availableCaptures) < topPriority) {
                availableCaptures = new ArrayList<>();
            }
            availableMoves = MovePriorities.topPriorityMoves(availableMoves);
            availableCaptures = MovePriorities.topPriorityMoves(availableCaptures);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    private void deselectPiece() {
        selectedPiece = pieceNone;
        availableMoves = new ArrayList<>();
        availableCaptures = new ArrayList<>();
    }

    private boolean nothingIsSelected() {
        return selectedPiece.equals(pieceNone);
    }

    private boolean isSelected(int row, int col) {
        return selectedPiece.equals(new Point(row, col));
    }

    public Side getTurnSide() {
        return turnIndicator;
    }

    private void advanceTurn() {
        deselectPiece();
        turnIndicator = turnIndicator.getOpponent();
    }

    protected Board getNextTurn(int fromrow, int fromcol, int torow, int tocol) {
        Board nextTurn = new Board(board, translation);
        nextTurn.move(fromrow, fromcol, torow, tocol);
        return nextTurn;
    }

    public boolean isInCheck(@NotNull Side side) {
        ArrayList<Move> captures = getAllAvailableCapturesWithoutSpecialRules(side.getOpponent());
        for (Move capture : captures) {
            if (getType(capture.getRow(), capture.getCol()) == PieceType.KING) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public ArrayList<Move> excludeMovesThatLeaveKingInCheck(@NotNull Point position, Side side, @NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (!getNextTurn(position.x, position.y, move.getRow(), move.getCol()).isInCheck(side)) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public Board getPreviousBoard() {
        return previousBoard;
    }
}
