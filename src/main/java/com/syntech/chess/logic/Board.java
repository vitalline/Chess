package com.syntech.chess.logic;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.chess.DoublePawnType;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private Piece[][] board;
    private int width, height;
    private Side turnIndicator = Side.WHITE;

    private boolean displayPromotionPopup = false;
    private boolean displayResultPopup = false;
    private String status;
    private Side statusSide = Side.WHITE;
    private PieceType statusPiece = PieceType.PAWN;
    private boolean gameEnded;

    private Point selectedPiece = new Point(-1, -1);
    private static final Point pieceNone = new Point(-1, -1);
    private Point enPassantPoint = new Point(-1, -1);

    private ArrayList<Move> availableMoves = new ArrayList<>();
    private ArrayList<Move> availableCaptures = new ArrayList<>();

    public Board(@NotNull Piece[][] board, boolean initialize) {
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
        if (initialize) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    this.board[row][col].setPosition(row, col);
                }
            }
        }
    }

    public Board(@NotNull Piece[][] board) {
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

    public Piece[][] getBoard() {
        return board;
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
        imGui.end();
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
                if (CellGraphics.display(imGui, getSelectedPiece().getSide(), pieceType, size, getColor(selectedPiece.x, selectedPiece.y).toSide().toColor(), -1)) {
                    getSelectedPiece().promoteTo(pieceType);
                    displayPromotionPopup = false;
                    imGui.closeCurrentPopup();
                    advanceTurn();
                    checkStatusConditions();
                    break;
                }
            }
            imGui.endPopup();
        }
        if (imGui.beginPopup("Result", JImWindowFlags.AlwaysAutoResize)) {
            imGui.text(status);
            if (imGui.button("OK")) {
                displayResultPopup = false;
                imGui.closeCurrentPopup();
            }
            imGui.endPopup();
        }
    }

    private void displayCell(JImGui imGui, float size, int row, int col) {
        if (CellGraphics.display(imGui, board[row][col], size, getColor(row, col), col * height + row)) {
            analyzeMove(row, col);
        }
    }

    private void displayLabel(@NotNull JImGui imGui, String label, float x, float y) {
        imGui.pushStyleColor(JImStyleColors.Button, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.NONE.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.NONE.getColor());
        imGui.button(label, x, y);
        imGui.popStyleColor(3);
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

    private void analyzeMove(int row, int col) {
        if (isSelected(row, col)) {
            deselectPiece();
        } else if (getTurnSide() == getSide(row, col)) {
            selectPiece(row, col);
        } else if (Move.contains(availableMoves, row, col) || Move.contains(availableCaptures, row, col)) {
            moveAndCheckStatusConditions(selectedPiece.x, selectedPiece.y, row, col);
        }
    }

    private void move(int fromrow, int fromcol, int torow, int tocol) {
        Piece piece = getPiece(fromrow, fromcol);
        Piece enPassantPiece = getPiece(enPassantPoint.x, enPassantPoint.y);
        if (enPassantPoint.x == torow && enPassantPoint.y == tocol
                && (piece.getType() == PieceType.PAWN || piece.getMovementType() instanceof DoublePawnType)
                && fromcol != tocol) {
            placePiece(PieceFactory.cell(), torow + MovementRules.getPawnMoveDirection(enPassantPiece.getSide()), tocol);
        }
        if (enPassantPiece.getType() == PieceType.EMPTY && enPassantPiece.getSide() == piece.getSide()) {
            placePiece(PieceFactory.cell(), enPassantPoint);
            enPassantPoint = pieceNone;
        }
        if (piece.getMovementType() instanceof DoublePawnType && torow - fromrow == 2 * MovementRules.getPawnMoveDirection(piece.getSide())) {
            enPassantPoint = new Point(torow - MovementRules.getPawnMoveDirection(piece.getSide()), tocol);
            placePiece(PieceFactory.piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, piece.getSide()), enPassantPoint);
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

    private void moveAndCheckStatusConditions(int fromrow, int fromcol, int torow, int tocol) {
        move(fromrow, fromcol, torow, tocol);
        if (!displayPromotionPopup) {
            checkStatusConditions();
        }
    }

    private void checkStatusConditions() {
        status = getStatusConditions(turnIndicator);
        if (gameEnded) {
            displayResultPopup = true;
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

    @Nullable
    private String getStatusConditions(Side side) {
        if (getAllAvailableMoves(side).size() == 0 && getAllAvailableCaptures(side).size() == 0) {
            gameEnded = true;
            if (isInCheck(side)) {
                statusSide = side.getOpponent();
                statusPiece = PieceType.QUEEN;
                return String.format("Checkmate! %s wins!", side.getOpponent().getProperName());
            } else {
                statusSide = side.getOpponent();
                statusPiece = PieceType.KING;
                return String.format("%s has stalemated %s!", side.getOpponent().getProperName(), side.getProperName());
            }
        } else {
            if (isInCheck(side)) {
                statusSide = side;
                statusPiece = PieceType.KING;
                return String.format("%s is in check!", side.getProperName());
            } else {
                statusSide = side;
                statusPiece = PieceType.PAWN;
                return null;
            }
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
            board[row][col].setPosition(row, col);
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
                    moves.addAll(board[row][col].getAvailableMovesWithoutSpecialRules(this));
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
                    moves.addAll(board[row][col].getAvailableCapturesWithoutSpecialRules(this));
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
                    moves.addAll(board[row][col].getAvailableMoves(this));
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
                    moves.addAll(board[row][col].getAvailableCaptures(this));
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

    public boolean isOnBoard(int row, int col) {
        try {
            board[row][col].getType();
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return false;
        }
        return true;
    }

    public Piece getPiece(int row, int col) {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return PieceFactory.cell();
        }
    }

    private Piece getSelectedPiece() {
        return getPiece(selectedPiece.x, selectedPiece.y);
    }

    private void selectPiece(int row, int col) {
        try {
            board[row][col].getType();
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

    private Board getNextTurn(int fromrow, int fromcol, int torow, int tocol) {
        Board nextTurn = new Board(board);
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
}
