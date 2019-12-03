package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.chess.KnightType;

public class PegasusType extends KnightType {

    public PegasusType(Side side) {
        super(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.PEGASUS;
    }
}
