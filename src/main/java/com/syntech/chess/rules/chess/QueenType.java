package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class QueenType extends MovementType {

    public QueenType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addOrthogonalMovement(position, board, moves);
        MovementRules.addDiagonalMovement(position, board, moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addOrthogonalCapturing(position, board, moves);
        MovementRules.addDiagonalCapturing(position, board, moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }
}