package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class LevellingUpForcedPiece extends LevellingForcedPiece {

    public LevellingUpForcedPiece(Side side, MovementType movementType) {
        this(side, movementType, 0);
    }

    public LevellingUpForcedPiece(Side side, MovementType movementType, int xp) {
        super(side, movementType, xp, null);
        baseType = PieceBaseType.LEVELLING_UP_FORCED_PIECE;
    }

    @Override
    protected void respawn(@NotNull Board board) {
    }

    @Override
    protected void morph(@NotNull Board board, PieceType newPieceType, PromotionInfo promotionInfo, int xp, Point position) {
        board.placePiece(PieceFactory.piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, newPieceType, side, promotionInfo, xp, initialPosition), position);
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelDown() {
        return true;
    }
}
