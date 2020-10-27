package com.syntech.chess.rules.chess3d;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementRules3D;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class Bishop3DType extends MovementType {

    public Bishop3DType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addDiagonalMovement(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addDiagonalCapturing(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 3;
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
