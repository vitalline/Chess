package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class PromotableForcedPiece extends PromotablePiece {

    public PromotableForcedPiece(Side side, MovementType movementType, Integer promotionRow, Piece... pieces) {
        super(side, movementType, promotionRow, pieces);
        baseType = PieceBaseType.PROMOTABLE_FORCED_PIECE;
    }

    public ArrayList<Point> getAvailableMoves(@NotNull Board board) {
        if (board.getAllAvailableCaptures(side).isEmpty()) {
            ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(board);
            return excludeMovesThatLeaveKingInCheck(board, moves);
        }
        return new ArrayList<>();
    }
}
