package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceType;

import java.util.ArrayList;
import java.util.Arrays;

public final class XPRules {
    public static final int PAWN_LEVEL_UP = 4;
    public static final int KNIGHT_LEVEL_UP = 9;
    public static final int BISHOP_LEVEL_UP = 16;
    public static final int ROOK_LEVEL_UP = 25;
    public static final int QUEEN_LEVEL_UP = 36;

    public static final int MOVE_XP = 1;
    public static final int CHECK_XP = 2;
    public static final int PAWN_CAPTURE_XP = 3;
    public static final int KNIGHT_CAPTURE_XP = 5;
    public static final int BISHOP_CAPTURE_XP = 7;
    public static final int ROOK_CAPTURE_XP = 12;
    public static final int QUEEN_CAPTURE_XP = 20;
    public static final int AMAZON_CAPTURE_XP = 30;

    public static final ArrayList<PieceType> LEVELS = new ArrayList<>(Arrays.asList(
            PieceType.NONE, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.AMAZON
    ));
}
