package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CyclicBoard extends Board {
    public CyclicBoard(@NotNull Piece[][] board, Translation translation, boolean initialize, boolean update) {
        super(board, translation, initialize, update);
    }

    public CyclicBoard(@NotNull Piece[][] board, Translation translation, int turn) {
        super(board, translation, turn);
    }

    private int getColumn(int col) {
        while (col < 0) {
            col += width;
        }
        while (col >= width) {
            col -= width;
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
    public ArrayList<Move> getAllAvailableCaptures(Side side) {
        ArrayList<Move> moves = super.getAllAvailableCaptures(side);
        format(moves);
        return moves;
    }

    @Override
    protected Board getNextTurn(Move move) {
        CyclicBoard nextTurn = new CyclicBoard(getBoard(), getTranslation(), turn);
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

    private void format(ArrayList<Move> moves) {
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
