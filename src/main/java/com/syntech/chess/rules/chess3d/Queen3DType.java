package com.syntech.chess.rules.chess3d;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementRules3D;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class Queen3DType extends MovementType {

    public Queen3DType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) {
            MovementRules3D.addOrthogonalMovement(moves, position, (Board3D) board);
            MovementRules3D.addDiagonalMovement(moves, position, (Board3D) board);
            MovementRules3D.addTriagonalMovement(moves, position, (Board3D) board);
        }
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) {
            MovementRules3D.addOrthogonalCapturing(moves, position, (Board3D) board);
            MovementRules3D.addDiagonalCapturing(moves, position, (Board3D) board);
            MovementRules3D.addTriagonalCapturing(moves, position, (Board3D) board);
        }
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 9;
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }
}