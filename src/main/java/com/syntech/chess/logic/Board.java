package com.syntech.chess.logic;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.PromotablePiece;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.jetbrains.annotations.Contract;
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
    private boolean gameEnded;

    private Point selectedPiece = new Point(-1, -1);
    private static final Point pieceNone = new Point(-1, -1);

    private ArrayList<Point> availableMoves = new ArrayList<>();
    private ArrayList<Point> availableCaptures = new ArrayList<>();

    public Board(@NotNull Piece[][] board, boolean initialize) {
        height = board.length;
        width = board[0].length;
        this.board = new Piece[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                try {
                    this.board[row][col] = (Piece) board[row][col].clone();
                } catch (CloneNotSupportedException ignore) {
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
                } catch (CloneNotSupportedException ignore) {
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
        imGui.getStyle().setItemSpacingX(0);
        imGui.getStyle().setItemSpacingY(0);
        imGui.begin(name, new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
        imGui.pushStyleColor(JImStyleColors.WindowBg, com.syntech.chess.graphic.Color.NONE.getColor());
        for (int row = height - 1; row >= 0; row--) {
            for (int col = 0; col < width - 1; col++) {
                displayCell(imGui, size, row, col);
                imGui.sameLine();
            }
            int col = width - 1;
            displayCell(imGui, size, row, col);
        }
        imGui.end();
        imGui.getStyle().setItemSpacingX(spacingX);
        imGui.getStyle().setItemSpacingY(spacingY);
        if (displayPromotionPopup) {
            imGui.openPopup("Promote");
        }
        if (displayResultPopup) {
            imGui.openPopup("Result");
        }
        if (imGui.beginPopup("Promote", JImWindowFlags.AlwaysAutoResize)) {
            for (Piece piece : ((PromotablePiece) getSelectedPiece()).getPromotionPieces()) {
                if (CellGraphics.display(imGui, piece, size, getColor(selectedPiece.x, selectedPiece.y).toSide().toColor(), -1)) {
                    placePiece(PieceFactory.piece(piece.getBaseType(), piece.getType(), piece.getSide()), selectedPiece);
                    displayPromotionPopup = false;
                    imGui.closeCurrentPopup();
                    advanceTurn();
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
        if (CellGraphics.display(imGui, board[row][col], size, getColor(row, col), col * width + row)) {
            analyzeMove(row, col);
        }
    }

    private void analyzeMove(int row, int col) {
        if (isSelected(row, col)) {
            deselectPiece();
        } else if (getTurnSide() == getSide(row, col)) {
            selectPiece(row, col);
        } else if (availableMoves.contains(new Point(row, col)) || availableCaptures.contains(new Point(row, col))) {
            moveAndCheckStatusConditions(selectedPiece.x, selectedPiece.y, row, col, getSelectedPiece().getSide().getOpponent());
        }
    }

    private void move(int fromrow, int fromcol, int torow, int tocol) {
        getPiece(fromrow, fromcol).move(this, torow, tocol);
        selectedPiece = new Point(torow, tocol);
        if (getSelectedPiece().canBePromoted(this)) {
            displayPromotionPopup = true;
        } else {
            advanceTurn();
        }
    }

    private void moveAndCheckStatusConditions(int fromrow, int fromcol, int torow, int tocol, Side side) {
        move(fromrow, fromcol, torow, tocol);
        checkStatusConditions(side);
    }

    private void checkStatusConditions(Side side) {
        status = getStatusConditions(side);
        if (gameEnded) {
            displayResultPopup = true;
        }
    }

    @NotNull
    @Contract(pure = true)
    public static String getCoordinates(@NotNull Point position) {
        return (char) (position.y + 'a') + String.valueOf(position.x + 1);
    }

    @Nullable
    private String getStatusConditions(Side side) {
        if (getAllAvailableMoves(side).size() == 0 && getAllAvailableCaptures(side).size() == 0) {
            gameEnded = true;
            if (isInCheck(side)) {
                return String.format("Checkmate! %s wins!", side.getOpponent().getProperName());
            } else {
                return String.format("%s has stalemated %s!", side.getOpponent().getProperName(), side.getProperName());
            }
        } else {
            if (isInCheck(side)) {
                return String.format("%s is in check!", side.getProperName());
            } else {
                return null;
            }
        }
    }

    public String getStatusString() {
        return status;
    }

    public void placePiece(Piece piece, int row, int col) {
        try {
            board[row][col] = piece;
            board[row][col].setPosition(row, col);
        } catch (ArrayIndexOutOfBoundsException ignore) {
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
        } else {
            if (availableMoves.contains(new Point(row, col))) {
                return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.MOVE_WHITE : com.syntech.chess.graphic.Color.MOVE_BLACK;
            } else if (availableCaptures.contains(new Point(row, col))) {
                return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.CAPTURE_WHITE : com.syntech.chess.graphic.Color.CAPTURE_BLACK;
            }
        }
        return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.WHITE : Color.BLACK;
    }

    @NotNull
    private ArrayList<Point> getAllAvailableMovesWithoutSpecialRules(Side side) {
        ArrayList<Point> moves = new ArrayList<>();
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
    private ArrayList<Point> getAllAvailableCapturesWithoutSpecialRules(Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(board[row][col].getAvailableCapturesWithoutSpecialRules(this));
                }
            }
        }
        return moves;
    }

    public ArrayList<Point> getAllAvailableMoves(Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(board[row][col].getAvailableMoves(this));
                }
            }
        }
        return moves;
    }

    public ArrayList<Point> getAllAvailableCaptures(Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    moves.addAll(board[row][col].getAvailableCaptures(this));
                }
            }
        }
        return moves;
    }

    public ArrayList<Point> getAllControlledCells(Side side) {
        ArrayList<Point> pieces = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    pieces.addAll(board[row][col].getControlledCells(this));
                }
            }
        }
        return pieces;
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
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return false;
        }
        return true;
    }

    public boolean isEnemy(int row, int col, Side side) {
        return !isFree(row, col) && (getSide(row, col) != side) && (getSide(row, col) != Side.NEUTRAL);
    }

    public Piece getPiece(int row, int col) {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException ignore) {
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
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
    }

    private void deselectPiece() {
        selectedPiece = pieceNone;
        availableMoves = new ArrayList<>();
        availableCaptures = new ArrayList<>();
    }

    @Contract(pure = true)
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

    public Board getNextTurn(int fromrow, int fromcol, int torow, int tocol) {
        Board nextTurn = new Board(board);
        nextTurn.move(fromrow, fromcol, torow, tocol);
        return nextTurn;
    }

    public boolean isInCheck(@NotNull Side side) {
        ArrayList<Point> captures = getAllAvailableCapturesWithoutSpecialRules(side.getOpponent());
        for (Point capture : captures) {
            if (getType(capture.x, capture.y) == PieceType.KING) {
                return true;
            }
        }
        return false;
    }
}
