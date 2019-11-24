package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.QueenType;

import java.awt.*;

public class FAForcedQueen extends FAForcedPiece {

    public FAForcedQueen(Side side) {
        super(side);
        movementType = new QueenType();
        maxXP = XPRules.QUEEN_LEVEL_UP;
    }

    public FAForcedQueen(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new QueenType();
        maxXP = XPRules.QUEEN_LEVEL_UP;
    }
}
