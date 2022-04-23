package com.syntech.chess.rules;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class MovePriorities {
    private static final int NOT_A_MOVE = -1;
    public static final int NORMAL_MOVE = 0;
    public static final int FORCED_CAPTURE = 1;
    private static final int FORCED_PAWN_CAPTURE = 7;
    private static final int FORCED_KNIGHT_CAPTURE = 6;
    private static final int FORCED_BISHOP_CAPTURE = 5;
    private static final int FORCED_ROOK_CAPTURE = 4;
    private static final int FORCED_CARDINAL_CAPTURE = 3;
    private static final int FORCED_QUEEN_CAPTURE = 2;
    private static final int FORCED_AMAZON_CAPTURE = 1;
    private static final int FORCED_SNIPER_CAPTURE = 9;
    public static final int SNIPER_SHOT = 10;
    private static final int CHECK = 100;

    @NotNull
    public static ArrayList<Move> topPriorityMoves(@NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        int maxPriority = getTopPriority(moves);
        for (Move move : moves) {
            if (move.getPriority() == maxPriority) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public static int getTopPriority(@NotNull ArrayList<Move> moves) {
        int maxPriority = MovePriorities.NOT_A_MOVE;
        for (Move move : moves) {
            if (maxPriority < move.getPriority()) {
                maxPriority = move.getPriority();
            }
        }
        return maxPriority;
    }

    @Contract(pure = true)
    public static int getCapturePriority(@NotNull PieceType type) {
        return switch (type) {
            case PAWN -> FORCED_PAWN_CAPTURE;
            case KNIGHT -> FORCED_KNIGHT_CAPTURE;
            case BISHOP -> FORCED_BISHOP_CAPTURE;
            case ROOK -> FORCED_ROOK_CAPTURE;
            case CARDINAL -> FORCED_CARDINAL_CAPTURE;
            case QUEEN -> FORCED_QUEEN_CAPTURE;
            case AMAZON -> FORCED_AMAZON_CAPTURE;
            case SNIPER -> FORCED_SNIPER_CAPTURE;
            case KING -> CHECK;
            default -> NORMAL_MOVE;
        };
    }
}
