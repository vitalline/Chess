package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.XPRules;
import com.syntech.chess.rules.chess.PawnType;

import java.awt.*;

public class FAForcedPawn extends FAForcedPiece {

    public FAForcedPawn(Side side) {
        super(side);
        movementType = new PawnType(side);
        maxXP = XPRules.PAWNLEVELUP;
    }

    public FAForcedPawn(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new PawnType(side);
        maxXP = XPRules.PAWNLEVELUP;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
