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

    public static void addDiagonalThreating(@NotNull Point pos, @NotNull Board board, Side side, ArrayList<Point> moves) {
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

    public static void addOrthogonalThreating(@NotNull Point pos, @NotNull Board board, Side side, ArrayList<Point> moves) {
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
}
