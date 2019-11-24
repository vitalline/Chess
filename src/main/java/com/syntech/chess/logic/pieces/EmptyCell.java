package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;

import java.awt.*;
import java.util.ArrayList;

public class EmptyCell extends Piece {

    public EmptyCell() {
        super(Side.NONE);
    }

    @Override
    public PieceType getType() {
        return PieceType.NONE;
    }

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Board board) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(Board board, Side side) {
        return new ArrayList<>();
    }
}
