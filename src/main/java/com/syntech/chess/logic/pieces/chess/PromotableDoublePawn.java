package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.PromotablePiece;
import com.syntech.chess.rules.chess.DoublePawnType;

public class PromotableDoublePawn extends PromotablePiece {

    public PromotableDoublePawn(Side side, Integer promotionRow, Piece... pieces) {
        super(side, promotionRow, pieces);
        movementType = new DoublePawnType(side);
    }
}
