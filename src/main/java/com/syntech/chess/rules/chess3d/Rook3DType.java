package com.syntech.chess.rules.chess3d;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.logic.boards.Board3D;
import com.syntech.chess.rules.MovementRules3D;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class Rook3DType extends MovementType {

    public Rook3DType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addOrthogonalMovement(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        if (board instanceof Board3D) MovementRules3D.addOrthogonalCapturing(moves, position, (Board3D) board);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 5;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
}