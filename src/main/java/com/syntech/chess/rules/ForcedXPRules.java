package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public final class ForcedXPRules {
    private static final int PAWN_LEVEL_UP_XP = 4;
    private static final int KNIGHT_LEVEL_UP_XP = 9;
    private static final int BISHOP_LEVEL_UP_XP = 16;
    private static final int ROOK_LEVEL_UP_XP = 25;
    private static final int QUEEN_LEVEL_UP_XP = 36;

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
    public static int getNextLevelXP(@NotNull PieceType type) {
        int levelXP = 0;
        switch (type) {
            case QUEEN:
                levelXP += ForcedXPRules.QUEEN_LEVEL_UP_XP;
            case ROOK:
                levelXP += ForcedXPRules.ROOK_LEVEL_UP_XP;
            case BISHOP:
                levelXP += ForcedXPRules.BISHOP_LEVEL_UP_XP;
            case KNIGHT:
                levelXP += ForcedXPRules.KNIGHT_LEVEL_UP_XP;
            case PAWN:
                levelXP += ForcedXPRules.PAWN_LEVEL_UP_XP;
        }
        return levelXP;
    }

    @Contract(pure = true)
    public static int getPreviousLevelXP(@NotNull PieceType type) {
        int currentLevel = LEVELS.indexOf(type);
        if (currentLevel > 0) {
            return getNextLevelXP(LEVELS.get(currentLevel - 1));
        }
        return 0;
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
