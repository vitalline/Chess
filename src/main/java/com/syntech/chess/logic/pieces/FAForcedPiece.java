package com.syntech.chess.logic.pieces;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.logic.*;
import com.syntech.chess.rules.ForcedXPRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FAForcedPiece extends ForcedPiece {
    private int xp = 0, maxXP;
    private Point initialPosition;
    private boolean checkHappened = false;

    public FAForcedPiece(Side side, MovementType movementType) {
        super(side, movementType);
        baseType = PieceBaseType.FA_FORCED_PIECE;
        this.maxXP = 0;
    }

    public FAForcedPiece(Side side, MovementType movementType, int maxXP) {
        super(side, movementType);
        baseType = PieceBaseType.FA_FORCED_PIECE;
        this.maxXP = maxXP;
    }

    public FAForcedPiece(Side side, MovementType movementType, int xp, int maxXP, Point initialPosition) {
        super(side, movementType);
        baseType = PieceBaseType.FA_FORCED_PIECE;
        this.xp = xp;
        this.maxXP = maxXP;
        this.initialPosition = initialPosition;
    }

    @Override
    public String getName() {
        if (doesNotLevelUp()) {
            return super.getName();
        }
        return String.format("%s%s%d", getSide().getName(), getType().getName(), CellGraphics.BARSTAGES * xp / maxXP);
    }

    @Override
    public String getLabel() {
        String label = super.getLabel();
        if (!doesNotLevelUp()) {
            label += String.format("\n[ XP: %d/%d ]", xp, maxXP);
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
        if (board.getPiece(row, col) instanceof FAForcedPiece) {
            int captureXP = ForcedXPRules.getPieceXPWorth(board.getType(row, col));
            if (captureXP != 0) {
                xp += captureXP;
                xpChanged = true;
            }
            ((FAForcedPiece) board.getPiece(row, col)).respawn(board);
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
            xp = 0;
        } else {
            if (xp >= maxXP) {
                levelUp(board);
            }
        }
    }

    private void respawn(@NotNull Board board) {
        if (board.isFree(initialPosition.x, initialPosition.y)) {
            int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
            if (currentLevel > 0) {
                PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel - 1);
                morph(board, newPieceType, 0, initialPosition);
            }
        }
    }

    private void levelUp(@NotNull Board board) {
        int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
        if (currentLevel < ForcedXPRules.LEVELS.size() - 1) {
            int diff = xp - maxXP;
            PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel + 1);
            morph(board, newPieceType, diff, position);
            FAForcedPiece newPiece = ((FAForcedPiece) board.getPiece(position.x, position.y));
            if (board.isInCheck(side.getOpponent()) && !checkHappened) {
                newPiece.xp += ForcedXPRules.getCheckXPWorth();
                newPiece.checkHappened = true;
            }
            if (checkHappened) {
                newPiece.checkHappened = true;
            }
            if (!newPiece.doesNotLevelUp()) {
                if (newPiece.xp >= newPiece.maxXP) {
                    newPiece.levelUp(board);
                }
            }
            checkHappened = false;
        }
    }

    private void morph(@NotNull Board board, PieceType newPieceType, int xp, Point position) {
        board.placePiece(PieceFactory.piece(PieceBaseType.FA_FORCED_PIECE, newPieceType, side, xp, initialPosition), position);
    }

    @Contract(pure = true)
    private boolean doesNotLevelUp() {
        return maxXP == 0;
    }

    @Contract(pure = true)
    private boolean doesNotLevelDown() {
        return ForcedXPRules.LEVELS.indexOf(getType()) <= 0;
    }
}
