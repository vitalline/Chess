package com.syntech.chess.rules.chess3d;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementRules3D;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Knight3DType extends MovementType {

    public Knight3DType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addLeapingMovement(moves, position, (Board3D) board, 0, 1, 2);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addLeapingCapturing(moves, position, (Board3D) board, 0, 1, 2);
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
