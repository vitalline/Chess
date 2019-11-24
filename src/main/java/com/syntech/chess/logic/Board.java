package com.syntech.chess.logic;

import com.syntech.chess.graphic.Cell;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.pieces.EmptyCell;
import com.syntech.chess.logic.pieces.Piece;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.JImStyleColors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private Piece[][] board;
    private int width, height;
    private Side turnIndicator = Side.WHITE;

    private Point selectedPiece = new Point(-1, -1);
    private static final Point pieceNone = new Point(-1, -1);
    private Point[] availableMoves;

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

    public void display(@NotNull JImGui imGui, float size) {
        imGui.pushStyleColor(JImStyleColors.WindowBg, com.syntech.chess.graphic.Color.NONE.getColor());
        for (int row = height - 1; row >= 0; row--) {
            for (int col = 0; col < width - 1; col++) {
                displayCell(imGui, size, row, col);
                JImGuiGen.sameLine(0, 4);
            }
            int col = width - 1;
            displayCell(imGui, size, row, col);
        }
    }

    private void displayCell(JImGui imGui, float size, int row, int col) {
        if (Cell.display(imGui, board[row][col], size, getColor(row, col), col * width + row)) {
            analyzeMove(row, col);
        }
    }

    private void analyzeMove(int row, int col) {
        if (isSelected(row, col)) {
            deselectPiece();
        } else if (getTurnSide() == getSide(row, col)) {
            selectPiece(row, col);
        } else if (getSelectedPiece().getAvailableMoves(this).contains(new Point(row, col))
                || getSelectedPiece().getAvailableCaptures(this).contains(new Point(row, col))) {
            getSelectedPiece().move(this, row, col);
            advanceTurn();
        }
    }

    private void move(int fromrow, int fromcol, int torow, int tocol) {
        getPiece(fromrow, fromcol).move(this, torow, tocol);
    }

    public void placePiece(Piece piece, int row, int col) {
        try {
            board[row][col] = piece;
            board[row][col].setPosition(row, col);
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
    }

    private com.syntech.chess.graphic.Color getColor(int row, int col) {
        if (nothingIsSelected()) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.WHITE : com.syntech.chess.graphic.Color.BLACK;
        } else if (isSelected(row, col)) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.SELECTEDWHITE : com.syntech.chess.graphic.Color.SELECTEDBLACK;
        } else if (getSelectedPiece().getAvailableMoves(this).contains(new Point(row, col))) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.MOVEWHITE : com.syntech.chess.graphic.Color.MOVEBLACK;
        } else if (getSelectedPiece().getAvailableCaptures(this).contains(new Point(row, col))) {
            return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.CAPTUREWHITE : com.syntech.chess.graphic.Color.CAPTUREBLACK;
        }
        return (width - col + row) % 2 != 0 ? com.syntech.chess.graphic.Color.WHITE : Color.BLACK;
    }

    @NotNull
    private ArrayList<Point> getAllAvailableMovesWithoutSpecialRules(Side side) {
        ArrayList<Point> captures = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    captures.addAll(board[row][col].getAvailableMovesWithoutSpecialRules(this));
                }
            }
        }
        return captures;
    }

    @NotNull
    private ArrayList<Point> getAllAvailableCapturesWithoutSpecialRules(Side side) {
        ArrayList<Point> captures = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    captures.addAll(board[row][col].getAvailableCapturesWithoutSpecialRules(this));
                }
            }
        }
        return captures;
    }

    public ArrayList<Point> getAllAvailableMoves(Side side) {
        ArrayList<Point> captures = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    captures.addAll(board[row][col].getAvailableMoves(this));
                }
            }
        }
        return captures;
    }

    public ArrayList<Point> getAllAvailableCaptures(Side side) {
        ArrayList<Point> captures = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getSide(row, col) == side) {
                    captures.addAll(board[row][col].getAvailableCaptures(this));
                }
            }
        }
        return captures;
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
        return getPiece(row, col).getType() == PieceType.NONE && isOnBoard(row, col);
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
        return !isFree(row, col) && (getSide(row, col) != side) && (getSide(row, col) != Side.NONE);
    }

    public Piece getPiece(int row, int col) {
        try {
            return board[row][col];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return new EmptyCell();
        }
    }

    private Piece getSelectedPiece() {
        return getPiece(selectedPiece.x, selectedPiece.y);
    }

    private void selectPiece(int row, int col) {
        try {
            board[row][col].getType();
            selectedPiece = new Point(row, col);
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
    }

    public void deselectPiece() {
        selectedPiece = pieceNone;
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
