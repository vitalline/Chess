package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public final class ForcedXPRules {
    private static final int PAWN_MAX_XP = 4;
    private static final int KNIGHT_MAX_XP = 9;
    private static final int BISHOP_MAX_XP = 16;
    private static final int ROOK_MAX_XP = 25;
    private static final int QUEEN_MAX_XP = 36;
    private static final int AMAZON_MAX_XP = 0;

    private static final int MOVE_XP = 1;
    private static final int CHECK_XP = 2;
    private static final int PAWN_CAPTURE_XP = 3;
    private static final int KNIGHT_CAPTURE_XP = 5;
    private static final int BISHOP_CAPTURE_XP = 7;
    private static final int ROOK_CAPTURE_XP = 12;
    private static final int QUEEN_CAPTURE_XP = 20;
    private static final int AMAZON_CAPTURE_XP = 30;

    public static final ArrayList<PieceType> LEVELS = new ArrayList<>(Arrays.asList(
            PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.AMAZON
    ));

    @Contract(pure = true)
    public static int getMaxXP(@NotNull PieceType type) {
        switch (type) {
            case PAWN:
                return ForcedXPRules.PAWN_MAX_XP;
            case KNIGHT:
                return ForcedXPRules.KNIGHT_MAX_XP;
            case BISHOP:
                return ForcedXPRules.BISHOP_MAX_XP;
            case ROOK:
                return ForcedXPRules.ROOK_MAX_XP;
            case QUEEN:
                return ForcedXPRules.QUEEN_MAX_XP;
            case AMAZON:
                return ForcedXPRules.AMAZON_MAX_XP;
            default:
                return 0;
        }
    }

    @Contract(pure = true)
    public static int getPieceXPWorth(@NotNull PieceType type) {
        switch (type) {
            case PAWN:
                return ForcedXPRules.PAWN_CAPTURE_XP;
            case KNIGHT:
                return ForcedXPRules.KNIGHT_CAPTURE_XP;
            case BISHOP:
                return ForcedXPRules.BISHOP_CAPTURE_XP;
            case ROOK:
                return ForcedXPRules.ROOK_CAPTURE_XP;
            case QUEEN:
                return ForcedXPRules.QUEEN_CAPTURE_XP;
            case AMAZON:
                return ForcedXPRules.AMAZON_CAPTURE_XP;
            default:
                return 0;
        }
    }

    @Contract(pure = true)
    public static int getCheckXPWorth() {
        return ForcedXPRules.CHECK_XP;
    }

    @Contract(pure = true)
    public static int getMoveXPWorth() {
        return ForcedXPRules.MOVE_XP;
    }
}
