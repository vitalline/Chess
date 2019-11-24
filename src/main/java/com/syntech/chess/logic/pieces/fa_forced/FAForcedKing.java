package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.chess.KingType;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public class FAForcedKing extends FAForcedPiece {

    public FAForcedKing(Side side) {
        super(side);
        movementType = new KingType(side);
    }

    public FAForcedKing(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new KingType(side);
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelUp() {
        return true;
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelDown() {
        return true;
    }
}
