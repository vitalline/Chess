package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceFactory;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class HillBoard extends AIBoard {

    public HillBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public HillBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    @NotNull
    public ArrayList<Move> excludeMovesThatLeaveKingInCheck(Side side, @NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (!((HillBoard)getMoveResultWithoutPromotion(move)).isInDanger(side)) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public boolean isInDanger(@NotNull Side side) {
        for (Point p : getPositions(side)) {
            if (getType(p.x, p.y) == PieceType.KING) {
                if (side == Side.WHITE && (p.x == 0 || p.x == getHeight() - 1) && turn % getWidth() == getWidth() - 1) return true;
            }
        }
        return isInCheck(side);
    }

    @Override
    protected void doAIAction() {
        if (turn % getWidth() == getWidth() - 1) {
            for (int row = 0; row < getHeight() / 2; row++) {
                for (int col = 0; col < getWidth(); col++) {
                    if (row == 0) {
                        placePiece(PieceFactory.cell(), row, col);
                    } else {
                        getPiece(row, col).move(this, row - 1, col);
                    }
                }
            }
            for (int row = getHeight() - 1; row >= getHeight() / 2; row--) {
                for (int col = 0; col < getWidth(); col++) {
                    if (row == getHeight() - 1) {
                        placePiece(PieceFactory.cell(), row, col);
                    } else {
                        getPiece(row, col).move(this, row + 1, col);
                    }
                }
            }
            updatePieces();
        }
    }

}
