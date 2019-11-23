package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;

import java.awt.*;
import java.util.ArrayList;

public interface MovementType {
    default ArrayList<Point> getControlledCells(Point position, Board board) {
        ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(position, board);
        moves.addAll(getAvailableThreatsOn(position, board, Side.WHITE));
        moves.addAll(getAvailableThreatsOn(position, board, Side.BLACK));
        return moves;
    }

    ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board);

    ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side);
}
