package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CyclicBoard extends Board {
    public CyclicBoard(@NotNull Piece[][] board, Translation translation, boolean initialize) {
        super(board, translation, initialize);
    }

    public CyclicBoard(@NotNull Piece[][] board, Translation translation) {
        super(board, translation);
    }

    @Override
    protected boolean isOnBoard(int row, int col) {
        return row >= 0 && row < height;
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
    protected Board getNextTurn(int fromrow, int fromcol, int torow, int tocol) {
        CyclicBoard nextTurn = new CyclicBoard(getBoard(), getTranslation());
        nextTurn.move(fromrow, fromcol, torow, tocol);
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
            move.setCol(getColumn(move.getCol()));
        }
    }
}
