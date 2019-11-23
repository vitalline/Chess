package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class BishopType implements MovementType {

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addDiagonalMovement(position, board, moves);
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addDiagonalThreating(position, board, side, moves);
        return moves;
    }
}
