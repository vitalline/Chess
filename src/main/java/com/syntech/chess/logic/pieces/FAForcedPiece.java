package com.syntech.chess.logic.pieces;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.fa_forced.*;
import com.syntech.chess.rules.XPRules;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class FAForcedPiece extends ForcedPiece {
    protected int xp = 0, maxXP = 0;
    private Point initialPosition;
    private boolean checkHappened = false;

    public FAForcedPiece(Side side) {
        super(side);
        baseType = PieceBaseType.FA_FORCED_PIECE;
    }

    public FAForcedPiece(Side side, int xp, Point initialPosition) {
        super(side);
        baseType = PieceBaseType.FA_FORCED_PIECE;
        this.xp = xp;
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
        boolean xpChanged = false;
        board.placePiece(new EmptyCell(), position.x, position.y);
        if (board.getPiece(row, col) instanceof FAForcedPiece) {
            switch (board.getType(row, col)) {
                case PAWN:
                    xp += XPRules.PAWN_CAPTURE_XP;
                    break;
                case KNIGHT:
                    xp += XPRules.KNIGHT_CAPTURE_XP;
                    break;
                case BISHOP:
                    xp += XPRules.BISHOP_CAPTURE_XP;
                    break;
                case ROOK:
                    xp += XPRules.ROOK_CAPTURE_XP;
                    break;
                case QUEEN:
                    xp += XPRules.QUEEN_CAPTURE_XP;
                    break;
                case AMAZON:
                    xp += XPRules.AMAZON_CAPTURE_XP;
                    break;
            }
            xpChanged = true;
            ((FAForcedPiece) board.getPiece(row, col)).respawn(board);
        }
        board.placePiece(this, row, col);
        if (board.isInCheck(side.getOpponent())) {
            xp += XPRules.CHECK_XP;
            checkHappened = true;
            xpChanged = true;
        } else {
            checkHappened = false;
        }
        if (!xpChanged) {
            xp += XPRules.MOVE_XP;
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
            int currentLevel = XPRules.LEVELS.indexOf(getType());
            if (currentLevel > 0) {
                switch (XPRules.LEVELS.get(currentLevel - 1)) {
                    case PAWN:
                        board.placePiece(new FAForcedPawn(side, 0, initialPosition), initialPosition.x, initialPosition.y);
                        break;
                    case KNIGHT:
                        board.placePiece(new FAForcedKnight(side, 0, initialPosition), initialPosition.x, initialPosition.y);
                        break;
                    case BISHOP:
                        board.placePiece(new FAForcedBishop(side, 0, initialPosition), initialPosition.x, initialPosition.y);
                        break;
                    case ROOK:
                        board.placePiece(new FAForcedRook(side, 0, initialPosition), initialPosition.x, initialPosition.y);
                        break;
                    case QUEEN:
                        board.placePiece(new FAForcedQueen(side, 0, initialPosition), initialPosition.x, initialPosition.y);
                        break;
                }
            }
        }
    }

    private void levelUp(@NotNull Board board) {
        int currentLevel = XPRules.LEVELS.indexOf(getType());
        if (currentLevel < XPRules.LEVELS.size() - 1) {
            int diff = xp - maxXP;
            switch (XPRules.LEVELS.get(currentLevel + 1)) {
                case KNIGHT:
                    board.placePiece(new FAForcedKnight(side, diff, initialPosition), position.x, position.y);
                    break;
                case BISHOP:
                    board.placePiece(new FAForcedBishop(side, diff, initialPosition), position.x, position.y);
                    break;
                case ROOK:
                    board.placePiece(new FAForcedRook(side, diff, initialPosition), position.x, position.y);
                    break;
                case QUEEN:
                    board.placePiece(new FAForcedQueen(side, diff, initialPosition), position.x, position.y);
                    break;
                case AMAZON:
                    board.placePiece(new FAForcedAmazon(side, diff, initialPosition), position.x, position.y);
                    break;
            }
            FAForcedPiece newPiece = ((FAForcedPiece) board.getPiece(position.x, position.y));
            if (board.isInCheck(side.getOpponent()) && !checkHappened) {
                newPiece.xp += XPRules.CHECK_XP;
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

    @Contract(pure = true)
    protected boolean doesNotLevelUp() {
        return false;
    }

    @Contract(pure = true)
    protected boolean doesNotLevelDown() {
        return false;
    }
}
