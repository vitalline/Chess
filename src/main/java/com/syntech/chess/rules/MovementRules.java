package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class MovementRules {
    public static void addDiagonalMovement(@NotNull Point position, @NotNull Board board, ArrayList<Move> moves) {
        int d = 1;
        while (board.isFree(position.x + d, position.y + d)) {
            moves.add(new Move(position.x + d, position.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(position.x + d, position.y - d)) {
            moves.add(new Move(position.x + d, position.y - d));
            d++;
        }
        d = 1;
        while (board.isFree(position.x - d, position.y + d)) {
            moves.add(new Move(position.x - d, position.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(position.x - d, position.y - d)) {
            moves.add(new Move(position.x - d, position.y - d));
            d++;
        }
    }

    public static void addDiagonalThreatening(@NotNull Point position, @NotNull Board board, Side side, ArrayList<Move> moves) {
        int d = 1;
        while (board.isFree(position.x + d, position.y + d)) {
            d++;
        }
        if (board.getSide(position.x + d, position.y + d) == side) {
            moves.add(new Move(position.x + d, position.y + d));
        }
        d = 1;
        while (board.isFree(position.x + d, position.y - d)) {
            d++;
        }
        if (board.getSide(position.x + d, position.y - d) == side) {
            moves.add(new Move(position.x + d, position.y - d));
        }
        d = 1;
        while (board.isFree(position.x - d, position.y + d)) {
            d++;
        }
        if (board.getSide(position.x - d, position.y + d) == side) {
            moves.add(new Move(position.x - d, position.y + d));
        }
        d = 1;
        while (board.isFree(position.x - d, position.y - d)) {
            d++;
        }
        if (board.getSide(position.x - d, position.y - d) == side) {
            moves.add(new Move(position.x - d, position.y - d));
        }
    }

    public static void addOrthogonalMovement(@NotNull Point position, @NotNull Board board, ArrayList<Move> moves) {
        int d = 0;
        while (board.isFree(position.x + ++d, position.y)) {
            moves.add(new Move(position.x + d, position.y));
        }
        d = 0;
        while (board.isFree(position.x + --d, position.y)) {
            moves.add(new Move(position.x + d, position.y));
        }
        d = 0;
        while (board.isFree(position.x, position.y + ++d)) {
            moves.add(new Move(position.x, position.y + d));
        }
        d = 0;
        while (board.isFree(position.x, position.y + --d)) {
            moves.add(new Move(position.x, position.y + d));
        }
    }

    public static void addOrthogonalThreatening(@NotNull Point position, @NotNull Board board, Side side, ArrayList<Move> moves) {
        int d = 0;
        while (board.isFree(position.x + ++d, position.y)) {
        }
        if (board.getSide(position.x + d, position.y) == side) {
            moves.add(new Move(position.x + d, position.y));
        }
        d = 0;
        while (board.isFree(position.x + --d, position.y)) {
        }
        if (board.getSide(position.x + d, position.y) == side) {
            moves.add(new Move(position.x + d, position.y));
        }
        d = 0;
        while (board.isFree(position.x, position.y + ++d)) {
        }
        if (board.getSide(position.x, position.y + d) == side) {
            moves.add(new Move(position.x, position.y + d));
        }
        d = 0;
        while (board.isFree(position.x, position.y + --d)) {
        }
        if (board.getSide(position.x, position.y + d) == side) {
            moves.add(new Move(position.x, position.y + d));
        }
    }

    public static void addLeapingMovement(@NotNull Point position, @NotNull Board board, int x, int y, ArrayList<Move> moves) {
        if (board.isFree(position.x + x, position.y + y)) {
            moves.add(new Move(position.x + x, position.y + y));
        }
        if (board.isFree(position.x + y, position.y + x)) {
            moves.add(new Move(position.x + y, position.y + x));
        }
        if (board.isFree(position.x - x, position.y + y)) {
            moves.add(new Move(position.x - x, position.y + y));
        }
        if (board.isFree(position.x - y, position.y + x)) {
            moves.add(new Move(position.x - y, position.y + x));
        }
        if (board.isFree(position.x + x, position.y - y)) {
            moves.add(new Move(position.x + x, position.y - y));
        }
        if (board.isFree(position.x + y, position.y - x)) {
            moves.add(new Move(position.x + y, position.y - x));
        }
        if (board.isFree(position.x - x, position.y - y)) {
            moves.add(new Move(position.x - x, position.y - y));
        }
        if (board.isFree(position.x - y, position.y - x)) {
            moves.add(new Move(position.x - y, position.y - x));
        }
    }

    public static void addLeapingThreatening(@NotNull Point position, @NotNull Board board, int x, int y, Side side, ArrayList<Move> moves) {
        if (board.getSide(position.x + x, position.y + y) == side) {
            moves.add(new Move(position.x + x, position.y + y));
        }
        if (board.getSide(position.x + y, position.y + x) == side) {
            moves.add(new Move(position.x + y, position.y + x));
        }
        if (board.getSide(position.x - x, position.y + y) == side) {
            moves.add(new Move(position.x - x, position.y + y));
        }
        if (board.getSide(position.x - y, position.y + x) == side) {
            moves.add(new Move(position.x - y, position.y + x));
        }
        if (board.getSide(position.x + x, position.y - y) == side) {
            moves.add(new Move(position.x + x, position.y - y));
        }
        if (board.getSide(position.x + y, position.y - x) == side) {
            moves.add(new Move(position.x + y, position.y - x));
        }
        if (board.getSide(position.x - x, position.y - y) == side) {
            moves.add(new Move(position.x - x, position.y - y));
        }
        if (board.getSide(position.x - y, position.y - x) == side) {
            moves.add(new Move(position.x - y, position.y - x));
        }
    }

    @Contract(pure = true)
    public static int getPawnMoveDirection(Side side) {
        return side == Side.WHITE ? 1 : -1;
    }

    public static void addPawnLikeMovement(@NotNull Point position, @NotNull Board board, int moveDirection, boolean hasNotMoved, ArrayList<Move> moves) {
        if (!board.isFree(position.x + moveDirection, position.y)) {
            return;
        }
        moves.add(new Move(position.x + moveDirection, position.y));
        if (hasNotMoved && board.isFree(position.x + moveDirection * 2, position.y)) {
            moves.add(new Move(position.x + moveDirection * 2, position.y));
        }
    }

    public static void addPawnLikeThreatening(@NotNull Point position, @NotNull Board board, Side side, int moveDirection, ArrayList<Move> moves) {
        if (board.getSide(position.x + moveDirection, position.y - 1) == side) {
            moves.add(new Move(position.x + moveDirection, position.y - 1));
        }
        if (board.getSide(position.x + moveDirection, position.y + 1) == side) {
            moves.add(new Move(position.x + moveDirection, position.y + 1));
        }
    }

    public static void addPawnLikeControlledCells(@NotNull Point position, @NotNull Board board, int moveDirection, ArrayList<Point> moves) {
        if (board.isOnBoard(position.x + moveDirection, position.y - 1)) {
            moves.add(new Point(position.x + moveDirection, position.y - 1));
        }
        if (board.isOnBoard(position.x + moveDirection, position.y + 1)) {
            moves.add(new Point(position.x + moveDirection, position.y + 1));
        }
    }

    public static void addKingLikeMovement(@NotNull Point position, @NotNull Board board, Side side, ArrayList<Move> moves) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.isFree(position.x + i, position.y + j)
                        && !board.getAllControlledCells(side.getOpponent()).contains(
                        new Point(position.x + i, position.y + j))) {
                    moves.add(new Move(position.x + i, position.y + j));
                }
            }
        }
    }

    public static void addKingLikeThreatening(@NotNull Point position, @NotNull Board board, Side side, Side kingSide, ArrayList<Move> moves) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.getSide(position.x + i, position.y + j) == side && kingSide == side) {
                    moves.add(new Move(position.x + i, position.y + j));
                } else if (board.getSide(position.x + i, position.y + j) == side
                        && !board.getAllControlledCells(side).contains(
                        new Point(position.x + i, position.y + j))) {
                    moves.add(new Move(position.x + i, position.y + j));
                }
            }
        }
    }

    public static void addKingLikeControlledCells(@NotNull Point position, @NotNull Board board, ArrayList<Point> moves) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.isOnBoard(position.x + i, position.y + j)) {
                    moves.add(new Point(position.x + i, position.y + j));
                }
            }
        }
    }
}
