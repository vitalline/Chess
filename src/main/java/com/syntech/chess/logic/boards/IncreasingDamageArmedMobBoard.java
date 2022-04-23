package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

public class IncreasingDamageArmedMobBoard extends BaseArmedMobBoard {

    public IncreasingDamageArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public IncreasingDamageArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    private double getBaseDamage(@NotNull Piece piece) {
        return switch (piece.getType()) {
            case PAWN -> 16;
            case KING -> 14;
            case KNIGHT -> 12;
            case BISHOP -> 10;
            case ROOK -> 8;
            case QUEEN -> 6;
            case AMAZON -> 4;
            default -> 0;
        };
    }

    private double getResistanceMultiplier(int resistanceLevel) {
        return switch (resistanceLevel) {
            case 1 -> 0.9D;
            case 2 -> 0.8D;
            case 3 -> 0.7D;
            case 4 -> 0.6D;
            case 5 -> 0.4D;
            case 6 -> 0.2D;
            case 7 -> 0.0D;
            default -> 1D;
        };
    }

    @Override
    protected double getDamage(LevellingForcedPiece piece) {
        return getBaseDamage(piece) * (1 + 0.1D * (turn >> 1)) * getResistanceMultiplier(piece.getResistanceLevel());
    }
}
