package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.rules.chess.KnightType;

public class PegasusType extends KnightType {

    @Override
    public PieceType getType() {
        return PieceType.PEGASUS;
    }
}
