package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class RookType extends MovementType {

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addOrthogonalMovement(position, board, moves);
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addOrthogonalThreatening(position, board, side, moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
}