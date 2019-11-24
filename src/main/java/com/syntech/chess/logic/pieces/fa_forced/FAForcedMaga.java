package com.syntech.chess.logic.pieces.fa_forced;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.FAForcedPiece;
import com.syntech.chess.rules.chess.MagaType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FAForcedMaga extends FAForcedPiece {

    public FAForcedMaga(Side side) {
        super(side);
        movementType = new MagaType();
        maxXP = -1;
    }

    public FAForcedMaga(Side side, int xp, Point initialPosition) {
        super(side, xp, initialPosition);
        movementType = new MagaType();
        maxXP = -1;
    }

    @Override
    public PieceType getType() {
        return PieceType.MAGA;
    }

    @Override
    protected void levelUp(@NotNull Board board) {
        xp = 0;
    }
}
