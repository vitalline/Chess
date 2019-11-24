package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public abstract class PromotableForcedPiece extends PromotablePiece {

    public PromotableForcedPiece(Side side, Integer promotionRow, Piece... pieces) {
        super(side, promotionRow, pieces);
        baseType = PieceBaseType.PROMOTABLE_FORCED_PIECE;
    }

    @Override
    public String getLabel() {
        return "Forced " + super.getLabel();
    }

    public ArrayList<Point> getAvailableMoves(@NotNull Board board) {
        if (board.getAllAvailableCaptures(side).isEmpty()) {
            ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(board);
            return excludeMovesThatLeaveKingInCheck(board, moves);
        }
        return new ArrayList<>();
    }
}
