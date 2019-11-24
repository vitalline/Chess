package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.BishopType;

public class ForcedBishop extends ForcedPiece {

    public ForcedBishop(Side side) {
        super(side);
        movementType = new BishopType();
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
