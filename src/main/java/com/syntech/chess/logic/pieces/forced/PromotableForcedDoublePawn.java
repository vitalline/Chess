package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.PromotableForcedPiece;
import com.syntech.chess.rules.chess.DoublePawnType;

public class PromotableForcedDoublePawn extends PromotableForcedPiece {

    public PromotableForcedDoublePawn(Side side, Integer promotionRow, Piece... pieces) {
        super(side, promotionRow, pieces);
        movementType = new DoublePawnType(side);
    }
}
