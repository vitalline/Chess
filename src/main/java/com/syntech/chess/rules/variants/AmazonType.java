package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class AmazonType extends MovementType {

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addOrthogonalMovement(position, board, moves);
        MovementRules.addDiagonalMovement(position, board, moves);
        MovementRules.addLeapingMovement(position, board, 1, 2, moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addOrthogonalThreatening(position, board, side, moves);
        MovementRules.addDiagonalThreatening(position, board, side, moves);
        MovementRules.addLeapingThreatening(position, board, 1, 2, side, moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.AMAZON;
    }
}