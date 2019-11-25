package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.util.ArrayList;

public abstract class MovementType implements Cloneable {

    @Contract(pure = true)
    public MovementType() {
    }

    @Contract(pure = true)
    public MovementType(MovementType movementType) {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public PieceType getType() {
        return PieceType.EMPTY;
    }

    public ArrayList<Point> getControlledCells(Point position, Board board) {
        ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(position, board);
        moves.addAll(getAvailableThreatsOn(position, board, Side.WHITE));
        moves.addAll(getAvailableThreatsOn(position, board, Side.BLACK));
        return moves;
    }

    public abstract ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board);

    public abstract ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side);
}
