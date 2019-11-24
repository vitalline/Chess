package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.KnightType;

import java.awt.*;

public class FAForcedKnight extends FAForcedPiece {

    public FAForcedKnight(Side side) {
        super(side);
        movementType = new KnightType();
        maxXP = XPRules.KNIGHTLEVELUP;
    }

    public FAForcedKnight(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new KnightType();
        maxXP = XPRules.KNIGHTLEVELUP;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
