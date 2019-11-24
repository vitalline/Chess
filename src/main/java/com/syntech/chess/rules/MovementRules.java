package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
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
}
