package com.syntech.chess.rules.chess3d;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementRules3D;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Pawn3DType extends MovementType {

    public Pawn3DType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addPawnLikeMovement(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addPawnLikeCapturing(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 1;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
