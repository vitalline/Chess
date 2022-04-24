package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CyclicBoard extends Board {
    public CyclicBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public CyclicBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
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
    public Side getSide(int row, int col) {
        return super.getSide(row, getColumn(col));
    }

    @Override
    public PieceType getType(int row, int col) {
        return super.getType(row, getColumn(col));
    }

    @Override
    public void placePiece(Piece piece, int row, int col) {
        super.placePiece(piece, row, getColumn(col));
    }

    public void updatePiece(int row, int col) {
        super.updatePiece(row, getColumn(col));
    }

    @Override
    public ArrayList<Move> getAllAvailableMoves(Side side) {
        ArrayList<Move> moves = super.getAllAvailableMoves(side);
        format(moves);
        return moves;
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

    public Move getRandomMove() {
        Move move = super.getRandomMove();
        move.setEndCol(getColumn(move.getEndCol()));
        return move;
    }
}
