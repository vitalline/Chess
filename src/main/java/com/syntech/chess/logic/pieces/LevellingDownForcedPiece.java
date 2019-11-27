package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class LevellingDownForcedPiece extends LevellingForcedPiece {

    public LevellingDownForcedPiece(Side side, MovementType movementType) {
        this(side, movementType, null);
    }

    public LevellingDownForcedPiece(Side side, MovementType movementType, PromotionInfo promotionInfo) {
        this(side, movementType, promotionInfo, null);
    }

    public LevellingDownForcedPiece(Side side, MovementType movementType, PromotionInfo promotionInfo, Point initialPosition) {
        super(side, movementType, promotionInfo, 0, 0, initialPosition);
        baseType = PieceBaseType.LEVELLING_DOWN_FORCED_PIECE;
    }

    @Override
    public String getLabel() {
        String label = super.getLabel().split("\n")[0];
        if (!doesNotLevelDown()) {
            label += String.format("\nStarted on %s", Board.getCoordinates(initialPosition));
        }
        return label;
    }

    @Override
    protected void levelUp(@NotNull Board board) {
    }

    @Override
    @Nullable
    @Contract(pure = true)
    protected PromotionInfo getPromotionInfo(PieceType newPieceType) {
        if (newPieceType == PieceType.PAWN) {
            return new PromotionInfo(side == Side.WHITE ? 4 : 1, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN);
        }
        return null;
    }

    @Override
    protected void morph(@NotNull Board board, PieceType newPieceType, PromotionInfo promotionInfo, int xp, Point position) {
        board.placePiece(PieceFactory.piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, newPieceType, side, promotionInfo, xp, initialPosition), position);
    }

    @Override
    @Contract(pure = true)
    protected boolean doesNotLevelUp() {
        return true;
    }
}
