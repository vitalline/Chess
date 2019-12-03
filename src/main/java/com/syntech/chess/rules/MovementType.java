package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;

import java.awt.*;
import java.util.ArrayList;

public abstract class MovementType implements Cloneable {

    protected Side side;

    public MovementType() {
        this(Side.NEUTRAL);
    }

    public MovementType(Side side) {
        this.side = side;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public PieceType getType() {
        return PieceType.EMPTY;
    }

    public abstract ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board);

    public abstract ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board);
}
