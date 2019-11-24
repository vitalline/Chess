package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.chess.KingType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FAForcedKing extends FAForcedPiece {

    public FAForcedKing(Side side) {
        super(side);
        movementType = new KingType(side);
        maxXP = -1;
    }

    public FAForcedKing(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new KingType(side);
        maxXP = -1;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    protected void levelUp(@NotNull Board board) {
        xp = 0;
    }
}
