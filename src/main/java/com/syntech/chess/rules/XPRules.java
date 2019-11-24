package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceType;

import java.util.ArrayList;
import java.util.Arrays;

public final class XPRules {
    public static final int PAWNLEVELUP = 4;
    public static final int KNIGHTLEVELUP = 9;
    public static final int BISHOPLEVELUP = 16;
    public static final int ROOKLEVELUP = 25;
    public static final int QUEENLEVELUP = 36;

    public static final int MOVEXP = 1;
    public static final int CHECKXP = 2;
    public static final int PAWNCAPTUREXP = 3;
    public static final int KNIGHTCAPTUREXP = 5;
    public static final int BISHOPCAPTUREXP = 7;
    public static final int ROOKCAPTUREXP = 12;
    public static final int QUEENCAPTUREXP = 20;
    public static final int MAGACAPTUREXP = 30;

    public static final ArrayList<PieceType> LEVELS = new ArrayList<>(Arrays.asList(
            PieceType.NONE, PieceType.PAWN, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN, PieceType.MAGA
    ));
}
