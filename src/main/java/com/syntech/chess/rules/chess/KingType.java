package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.util.ArrayList;

public class KingType extends MovementType {

    private Side side;

    @Contract(pure = true)
    public KingType(Side side) {
        this.side = side;
    }

    @Override
    public ArrayList<Point> getControlledCells(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.isOnBoard(position.x + i, position.y + j)) {
                    moves.add(new Point(position.x + i, position.y + j));
                }
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.isFree(position.x + i, position.y + j)
                        && !board.getAllControlledCells(side.getOpponent()).contains(
                        new Point(position.x + i, position.y + j))) {
                    moves.add(new Point(position.x + i, position.y + j));
                }
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Point> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.getSide(position.x + i, position.y + j) == side && this.side == side) {
                    moves.add(new Point(position.x + i, position.y + j));
                } else if (board.getSide(position.x + i, position.y + j) == side
                        && !board.getAllControlledCells(side).contains(
                        new Point(position.x + i, position.y + j))) {
                    moves.add(new Point(position.x + i, position.y + j));
                }
            }
        }
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
