package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class KnightType extends MovementType {

    public KnightType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addLeapingMovement(moves, position, board, 1, 2);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addLeapingCapturing(moves, position, board, 1, 2);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 3;
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
