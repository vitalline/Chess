package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PawnType implements MovementType {

    private Side side;

    @Contract(pure = true)
    public PawnType(Side side) {
        this.side = side;
    }

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        if (!board.isFree(position.x + getMoveDirection(), position.y)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Collections.singletonList(new Point(position.x + getMoveDirection(), position.y)));
    }


    @Override
    public ArrayList<Point> getControlledCells(@NotNull Point position, @NotNull Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        if (board.isOnBoard(position.x + getMoveDirection(), position.y - 1)) {
            moves.add(new Point(position.x + getMoveDirection(), position.y - 1));
        }
        if (board.isOnBoard(position.x + getMoveDirection(), position.y + 1)) {
            moves.add(new Point(position.x + getMoveDirection(), position.y + 1));
        }
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(@NotNull Point position, @NotNull Board board, Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        if (board.getSide(position.x + getMoveDirection(), position.y - 1) == side) {
            moves.add(new Point(position.x + getMoveDirection(), position.y - 1));
        }
        if (board.getSide(position.x + getMoveDirection(), position.y + 1) == side) {
            moves.add(new Point(position.x + getMoveDirection(), position.y + 1));
        }
        return moves;
    }

    @Contract(pure = true)
    private int getMoveDirection() {
        return side == Side.WHITE ? 1 : -1;
    }
}
