package com.syntech.chess.logic.pieces;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.logic.*;
import com.syntech.chess.rules.ForcedXPRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class LevellingForcedPiece extends ForcedPiece {
    private int xp;
    protected Point initialPosition;
    private boolean checkHappened = false;

    public LevellingForcedPiece(Side side, MovementType movementType) {
        this(side, movementType, 0, null);
    }

    public LevellingForcedPiece(Side side, MovementType movementType, int xp, Point initialPosition) {
        this(side, movementType, null, xp, initialPosition);
    }

    public LevellingForcedPiece(Side side, MovementType movementType, PromotionInfo promotionInfo, int xp, Point initialPosition) {
        super(side, movementType, promotionInfo);
        baseType = PieceBaseType.LEVELLING_FORCED_PIECE;
        this.xp = xp == 0 ? getMinXP() : xp;
        this.initialPosition = initialPosition;
    }

    private int getMinXP() {
        return ForcedXPRules.getPreviousLevelXP(getType());
    }

    private int getMaxXP() {
        return ForcedXPRules.getNextLevelXP(getType());
    }

    private int getCurrentXP() {
        return xp - getMinXP();
    }

    private int getXPDifference() {
        return getMaxXP() - getMinXP();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LevellingForcedPiece clone = (LevellingForcedPiece) super.clone();
        clone.initialPosition = initialPosition;
        return clone;
    }

    @Override
    public String getName() {
        if (doesNotLevelUp()) {
            return super.getName();
        }
        return String.format("%s%s%d", getSide().getName(), getType().getName(),
                CellGraphics.XP_BAR_STAGES * getCurrentXP() / getXPDifference());
    }

    @Override
    public String getLabel() {
        String label = super.getLabel();
        if (!doesNotLevelUp()) {
            label += String.format("\n[ XP: %d/%d ]", getCurrentXP(), getXPDifference());
        }
        if (!doesNotLevelDown()) {
            label += String.format("\nStarted on %s", Board.getCoordinates(initialPosition));
        }
        if (ForcedXPRules.getPieceXPWorth(getType()) != 0) {
            label += String.format("\nWorth %d XP", ForcedXPRules.getPieceXPWorth(getType()));
        }
        return label;
    }

    @Override
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
        if (this.initialPosition == null) {
            this.initialPosition = new Point(x, y);
        }
    }

    @Override
    public void move(@NotNull Board board, int row, int col) {
        if (movementType instanceof SpecialFirstMoveType) {
            ((SpecialFirstMoveType) movementType).move();
        }
        boolean xpChanged = false;
        board.placePiece(PieceFactory.cell(), position);
        if (board.getPiece(row, col) instanceof LevellingForcedPiece) {
            int captureXP = ForcedXPRules.getPieceXPWorth(board.getType(row, col));
            if (captureXP != 0) {
                xp += captureXP;
                xpChanged = true;
            }
            ((LevellingForcedPiece) board.getPiece(row, col)).respawn(board);
        }
        board.placePiece(this, row, col);
        if (board.isInCheck(side.getOpponent())) {
            xp += ForcedXPRules.getCheckXPWorth();
            checkHappened = true;
            xpChanged = true;
        } else {
            checkHappened = false;
        }
        if (!xpChanged) {
            xp += ForcedXPRules.getMoveXPWorth();
        }
        if (doesNotLevelUp()) {
            xp = getMinXP();
        } else {
            if (xp >= getMaxXP()) {
                levelUp(board);
            }
        }
    }

    protected void respawn(@NotNull Board board) {
        if (board.isFree(initialPosition.x, initialPosition.y)) {
            int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
            if (currentLevel > 0) {
                PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel - 1);
                morph(board, newPieceType, getPromotionInfo(newPieceType), 0, initialPosition);
            }
        }
    }

    protected void levelUp(@NotNull Board board) {
        int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
        if (currentLevel < ForcedXPRules.LEVELS.size() - 1) {
            PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel + 1);
            morph(board, newPieceType, getPromotionInfo(newPieceType), xp, position);
            LevellingForcedPiece newPiece = ((LevellingForcedPiece) board.getPiece(position.x, position.y));
            if (board.isInCheck(side.getOpponent()) && !checkHappened) {
                newPiece.xp += ForcedXPRules.getCheckXPWorth();
                newPiece.checkHappened = true;
            }
            if (checkHappened) {
                newPiece.checkHappened = true;
            }
            if (!newPiece.doesNotLevelUp()) {
                if (newPiece.xp >= newPiece.getMaxXP()) {
                    newPiece.levelUp(board);
                }
            }
            checkHappened = false;
        }
    }

    @Nullable
    @Contract(pure = true)
    protected PromotionInfo getPromotionInfo(PieceType newPieceType) {
        return null;
    }

    protected void morph(@NotNull Board board, PieceType newPieceType, PromotionInfo promotionInfo, int xp, Point position) {
        board.placePiece(PieceFactory.piece(PieceBaseType.LEVELLING_FORCED_PIECE, newPieceType, side, promotionInfo, xp, initialPosition), position);
    }

    @Contract(pure = true)
    protected boolean doesNotLevelUp() {
        return ForcedXPRules.LEVELS.indexOf(getType()) == -1
                || ForcedXPRules.LEVELS.indexOf(getType()) == ForcedXPRules.LEVELS.size() - 1;
    }

    @Contract(pure = true)
    protected boolean doesNotLevelDown() {
        return ForcedXPRules.LEVELS.indexOf(getType()) <= 0;
    }
}
