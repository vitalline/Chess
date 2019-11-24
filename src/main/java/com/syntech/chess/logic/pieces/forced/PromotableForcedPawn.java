package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.PromotableForcedPiece;
import com.syntech.chess.rules.chess.PawnType;

public class PromotableForcedPawn extends PromotableForcedPiece {

    public PromotableForcedPawn(Side side, Integer promotionRow, Piece... pieces) {
        super(side, promotionRow, pieces);
        movementType = new PawnType(side);
    }
}
