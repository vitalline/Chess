package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.PawnType;
import org.jetbrains.annotations.Contract;

import java.awt.*;

public class FAForcedPawn extends FAForcedPiece {

    public FAForcedPawn(Side side) {
        super(side);
        movementType = new PawnType(side);
        maxXP = XPRules.PAWN_LEVEL_UP;
    }

    public FAForcedPawn(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new PawnType(side);
        maxXP = XPRules.PAWN_LEVEL_UP;
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelDown() {
        return true;
    }
}
