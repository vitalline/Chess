package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public final class ForcedXPRules {

    private static final int[] LEVEL_XP = {4, 9, 16, 25, 36};
    private static final int[] RESISTANCE_LEVEL_XP = {2, 3, 5, 8, 13, 21, 34};
    private static final int[] POWER_LEVEL_XP = {6, 15, 28, 45, 66, 91};

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

    public static final ArrayList<PieceType> RESISTED_PIECES = new ArrayList<>(Arrays.asList(
            PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.KING, PieceType.AMAZON
    ));

    public static int getNextLevelXP(@NotNull PieceType type) {
        int currentLevel = LEVELS.indexOf(type);
        try {
            return LEVEL_XP[currentLevel];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return 0;
        }
    }

    public static PieceType getNextLevel(@NotNull PieceType type) {
        int currentLevel = LEVELS.indexOf(type);
        try {
            return LEVELS.get(currentLevel + 1);
        } catch (IndexOutOfBoundsException ignored) {
            return PieceType.NONE;
        }
    }

    public static PieceType getPreviousLevel(@NotNull PieceType type) {
        int currentLevel = LEVELS.indexOf(type);
        try {
            return LEVELS.get(currentLevel - 1);
        } catch (IndexOutOfBoundsException ignored) {
            return PieceType.NONE;
        }
    }

    public static int getResistanceLevelXP(int level) {
        try {
            return RESISTANCE_LEVEL_XP[level];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return 0;
        }
    }

    @NotNull
    private static ArrayList<PieceType> getResistedPieces(int level) {
        try {
            return new ArrayList<>(RESISTED_PIECES.subList(0, level));
        } catch (IndexOutOfBoundsException ignored) {
            return level < 0 ? new ArrayList<>() : RESISTED_PIECES;
        }
    }

    @NotNull
    public static String getResistedPieceList(int level) {
        ArrayList<PieceType> resistedPieces = getResistedPieces(level);
        if (resistedPieces.isEmpty()) {
            resistedPieces.add(PieceType.NONE);
        }
        StringBuilder sb = new StringBuilder();
        for (PieceType s : resistedPieces) {
            sb.append("\n\t");
            sb.append(s.getProperName());
        }
        return sb.toString();
    }

    public static int getMaxResistanceLevel() {
        return RESISTANCE_LEVEL_XP.length;
    }

    public static int getPowerLevelXP(int level) {
        try {
            return POWER_LEVEL_XP[level];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return 0;
        }
    }

    public static String getPowerLabel(int level) {
        PieceType type;
        try {
            type = RESISTED_PIECES.get(level - 1);
        } catch (IndexOutOfBoundsException ignored) {
            type = level <= 0 ? PieceType.NONE : PieceType.AMAZON;
        }
        StringBuilder label = new StringBuilder(type.getName());
        for (int i = level; i > RESISTED_PIECES.size(); i--) {
            label.append('+');
        }
        return label.toString();
    }

    public static int getMaxPowerLevel() {
        return POWER_LEVEL_XP.length;
    }

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

    public static int getCheckXPWorth() {
        return ForcedXPRules.CHECK_XP;
    }

    public static int getMoveXPWorth() {
        return ForcedXPRules.MOVE_XP;
    }
}
