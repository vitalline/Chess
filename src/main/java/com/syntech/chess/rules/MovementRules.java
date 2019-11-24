package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class MovementRules {
    public static void addDiagonalMovement(@NotNull Point pos, @NotNull Board board, ArrayList<Point> moves) {
        int d = 1;
        while (board.isFree(pos.x + d, pos.y + d)) {
            moves.add(new Point(pos.x + d, pos.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x + d, pos.y - d)) {
            moves.add(new Point(pos.x + d, pos.y - d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y + d)) {
            moves.add(new Point(pos.x - d, pos.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y - d)) {
            moves.add(new Point(pos.x - d, pos.y - d));
            d++;
        }
    }

    public static void addDiagonalThreatening(@NotNull Point pos, @NotNull Board board, Side side, ArrayList<Point> moves) {
        int d = 1;
        while (board.isFree(pos.x + d, pos.y + d)) {
            d++;
        }
        if (board.getSide(pos.x + d, pos.y + d) == side) {
            moves.add(new Point(pos.x + d, pos.y + d));
        }
        d = 1;
        while (board.isFree(pos.x + d, pos.y - d)) {
            d++;
        }
        if (board.getSide(pos.x + d, pos.y - d) == side) {
            moves.add(new Point(pos.x + d, pos.y - d));
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y + d)) {
            d++;
        }
        if (board.getSide(pos.x - d, pos.y + d) == side) {
            moves.add(new Point(pos.x - d, pos.y + d));
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y - d)) {
            d++;
        }
        if (board.getSide(pos.x - d, pos.y - d) == side) {
            moves.add(new Point(pos.x - d, pos.y - d));
        }
    }

    public static void addOrthogonalMovement(@NotNull Point pos, @NotNull Board board, ArrayList<Point> moves) {
        int d = 0;
        while (board.isFree(pos.x + ++d, pos.y)) {
            moves.add(new Point(pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x + --d, pos.y)) {
            moves.add(new Point(pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + ++d)) {
            moves.add(new Point(pos.x, pos.y + d));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + --d)) {
            moves.add(new Point(pos.x, pos.y + d));
        }
    }

    public static void addOrthogonalThreatening(@NotNull Point pos, @NotNull Board board, Side side, ArrayList<Point> moves) {
        int d = 0;
        while (board.isFree(pos.x + ++d, pos.y)) {
        }
        if (board.getSide(pos.x + d, pos.y) == side) {
            moves.add(new Point(pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x + --d, pos.y)) {
        }
        if (board.getSide(pos.x + d, pos.y) == side) {
            moves.add(new Point(pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + ++d)) {
        }
        if (board.getSide(pos.x, pos.y + d) == side) {
            moves.add(new Point(pos.x, pos.y + d));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + --d)) {
        }
        if (board.getSide(pos.x, pos.y + d) == side) {
            moves.add(new Point(pos.x, pos.y + d));
        }
    }

    public static void addLeapingMovement(@NotNull Point pos, @NotNull Board board, int x, int y, ArrayList<Point> moves) {
        if (board.isFree(pos.x + x, pos.y + y)) {
            moves.add(new Point(pos.x + x, pos.y + y));
        }
        if (board.isFree(pos.x + y, pos.y + x)) {
            moves.add(new Point(pos.x + y, pos.y + x));
        }
        if (board.isFree(pos.x - x, pos.y + y)) {
            moves.add(new Point(pos.x - x, pos.y + y));
        }
        if (board.isFree(pos.x - y, pos.y + x)) {
            moves.add(new Point(pos.x - y, pos.y + x));
        }
        if (board.isFree(pos.x + x, pos.y - y)) {
            moves.add(new Point(pos.x + x, pos.y - y));
        }
        if (board.isFree(pos.x + y, pos.y - x)) {
            moves.add(new Point(pos.x + y, pos.y - x));
        }
        if (board.isFree(pos.x - x, pos.y - y)) {
            moves.add(new Point(pos.x - x, pos.y - y));
        }
        if (board.isFree(pos.x - y, pos.y - x)) {
            moves.add(new Point(pos.x - y, pos.y - x));
        }
    }

    public static void addLeapingThreatening(@NotNull Point pos, @NotNull Board board, int x, int y, Side side, ArrayList<Point> moves) {
        if (board.getSide(pos.x + x, pos.y + y) == side) {
            moves.add(new Point(pos.x + x, pos.y + y));
        }
        if (board.getSide(pos.x + y, pos.y + x) == side) {
            moves.add(new Point(pos.x + y, pos.y + x));
        }
        if (board.getSide(pos.x - x, pos.y + y) == side) {
            moves.add(new Point(pos.x - x, pos.y + y));
        }
        if (board.getSide(pos.x - y, pos.y + x) == side) {
            moves.add(new Point(pos.x - y, pos.y + x));
        }
        if (board.getSide(pos.x + x, pos.y - y) == side) {
            moves.add(new Point(pos.x + x, pos.y - y));
        }
        if (board.getSide(pos.x + y, pos.y - x) == side) {
            moves.add(new Point(pos.x + y, pos.y - x));
        }
        if (board.getSide(pos.x - x, pos.y - y) == side) {
            moves.add(new Point(pos.x - x, pos.y - y));
        }
        if (board.getSide(pos.x - y, pos.y - x) == side) {
            moves.add(new Point(pos.x - y, pos.y - x));
        }
    }

    @Contract(pure = true)
    public static int getPawnMoveDirection(Side side) {
        return side == Side.WHITE ? 1 : -1;
    }

    public static void addPawnLikeMovement(@NotNull Point pos, @NotNull Board board, int moveDirection, boolean hasNotMoved, ArrayList<Point> moves) {
        if (!board.isFree(pos.x + moveDirection, pos.y)) {
            return;
        }
        moves.add(new Point(pos.x + moveDirection, pos.y));
        if (hasNotMoved && board.isFree(pos.x + moveDirection * 2, pos.y)) {
            moves.add(new Point(pos.x + moveDirection * 2, pos.y));
        }
    }

    public static void addPawnLikeThreatening(@NotNull Point pos, @NotNull Board board, Side side, int moveDirection, ArrayList<Point> moves) {
        if (board.getSide(pos.x + moveDirection, pos.y - 1) == side) {
            moves.add(new Point(pos.x + moveDirection, pos.y - 1));
        }
        if (board.getSide(pos.x + moveDirection, pos.y + 1) == side) {
            moves.add(new Point(pos.x + moveDirection, pos.y + 1));
        }
    }

    public static void addPawnLikeControlledCells(@NotNull Point pos, @NotNull Board board, int moveDirection, ArrayList<Point> moves) {
        if (board.isOnBoard(pos.x + moveDirection, pos.y - 1)) {
            moves.add(new Point(pos.x + moveDirection, pos.y - 1));
        }
        if (board.isOnBoard(pos.x + moveDirection, pos.y + 1)) {
            moves.add(new Point(pos.x + moveDirection, pos.y + 1));
        }
    }
}
