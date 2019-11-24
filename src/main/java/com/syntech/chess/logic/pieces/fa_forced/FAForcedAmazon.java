package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.chess.AmazonType;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public class FAForcedAmazon extends FAForcedPiece {

    public FAForcedAmazon(Side side) {
        super(side);
        movementType = new AmazonType();
    }

    public FAForcedAmazon(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new AmazonType();
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelUp() {
        return true;
    }
}
