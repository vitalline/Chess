package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class MagaType implements MovementType {

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addOrthogonalMovement(position, board, moves);
        MovementRules.addDiagonalMovement(position, board, moves);
        MovementRules.addLeapingMovement(position, board, 1, 2, moves);
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addOrthogonalThreatening(position, board, side, moves);
        MovementRules.addDiagonalThreatening(position, board, side, moves);
        MovementRules.addLeapingThreatening(position, board, 1, 2, side, moves);
        return moves;
    }
}