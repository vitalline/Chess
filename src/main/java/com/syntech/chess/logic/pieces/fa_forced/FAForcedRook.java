package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.RookType;

import java.awt.*;

public class FAForcedRook extends FAForcedPiece {

    public FAForcedRook(Side side) {
        super(side);
        movementType = new RookType();
        maxXP = XPRules.ROOKLEVELUP;
    }

    public FAForcedRook(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new RookType();
        maxXP = XPRules.ROOKLEVELUP;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

}
