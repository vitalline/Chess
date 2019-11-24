package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.fa_forced.*;
import com.syntech.chess.rules.XPRules;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FAForcedPiece extends ForcedPiece {
    protected int xp, maxXP;
    private Point initialPosition;

    public FAForcedPiece(Side side) {
        super(side);
    }

    public FAForcedPiece(Side side, int xp, Point initialPosition) {
        super(side);
        this.xp = xp;
        this.initialPosition = initialPosition;
    }

    @Override
    public String getName() {
        return String.format("%s%s%d", getSide().getName(), getType().getName(), xp);
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
                    xp += XPRules.PAWNCAPTUREXP;
                    break;
                case KNIGHT:
                    xp += XPRules.KNIGHTCAPTUREXP;
                    break;
                case BISHOP:
                    xp += XPRules.BISHOPCAPTUREXP;
                    break;
                case ROOK:
                    xp += XPRules.ROOKCAPTUREXP;
                    break;
                case QUEEN:
                    xp += XPRules.QUEENCAPTUREXP;
                    break;
                case MAGA:
                    xp += XPRules.MAGACAPTUREXP;
                    break;
            }
            xpChanged = true;
            ((FAForcedPiece) board.getPiece(row, col)).respawn(board);
        }
        board.placePiece(this, row, col);
        if (board.isInCheck(side.getOpponent())) {
            xp += XPRules.CHECKXP;
            xpChanged = true;
        }
        if (!xpChanged) {
            xp += XPRules.MOVEXP;
        }
        board.deselectPiece();
        if (xp >= maxXP) {
            levelUp(board);
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

    protected void levelUp(@NotNull Board board) {
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
                case MAGA:
                    board.placePiece(new FAForcedMaga(side, diff, initialPosition), position.x, position.y);
                    break;
            }
            if (((FAForcedPiece) board.getPiece(position.x, position.y)).xp >= ((FAForcedPiece) board.getPiece(position.x, position.y)).maxXP) {
                ((FAForcedPiece) board.getPiece(position.x, position.y)).levelUp(board);
            }
        }
    }
}
