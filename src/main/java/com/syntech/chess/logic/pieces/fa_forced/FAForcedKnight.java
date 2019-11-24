package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.KnightType;

import java.awt.*;

public class FAForcedKnight extends FAForcedPiece {

    public FAForcedKnight(Side side) {
        super(side);
        movementType = new KnightType();
        maxXP = XPRules.KNIGHT_LEVEL_UP;
    }

    public FAForcedKnight(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new KnightType();
        maxXP = XPRules.KNIGHT_LEVEL_UP;
    }
}
