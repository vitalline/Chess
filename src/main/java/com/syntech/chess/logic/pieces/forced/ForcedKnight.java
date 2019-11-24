package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.KnightType;

public class ForcedKnight extends ForcedPiece {

    public ForcedKnight(Side side) {
        super(side);
        movementType = new KnightType();
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
