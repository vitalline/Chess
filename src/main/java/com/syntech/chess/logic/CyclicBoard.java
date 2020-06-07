package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CyclicBoard extends Board {
    public CyclicBoard(@NotNull Piece[][] board, boolean initialize, boolean update) {
        super(board, initialize, update);
    }

    public CyclicBoard(@NotNull Piece[][] board, int turn) {
        super(board, turn);
    }

    private int getColumn(int col) {
        while (col < 0) {
            col += getWidth();
        }
        while (col >= getWidth()) {
            col -= getWidth();
        }
        return col;
    }

    @Override
    public Piece getPiece(int row, int col) {
        return super.getPiece(row, getColumn(col));
    }

    @Override
    public void placePiece(Piece piece, int row, int col) {
        super.placePiece(piece, row, getColumn(col));
    }

    @Override
    public ArrayList<Move> getAllAvailableMoves(Side side) {
        ArrayList<Move> moves = super.getAllAvailableMoves(side);
        format(moves);
        return moves;
    }

    @Override
    public Board getNextTurn(Move move) {
        CyclicBoard nextTurn = new CyclicBoard(getBoard(), turn);
        if (move != null) {
            nextTurn.move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
        }
        return nextTurn;
    }

    @Override
    protected void selectPiece(int row, int col) {
        super.selectPiece(row, col);
        format(availableMoves);
        format(availableCaptures);
    }

    private void format(@NotNull ArrayList<Move> moves) {
        for (Move move : moves) {
            move.setEndCol(getColumn(move.getEndCol()));
        }
    }

    protected Move getRandomMove() {
        Move move = super.getRandomMove();
        move.setEndCol(getColumn(move.getEndCol()));
        return move;
    }
}
