package com.syntech.chess.logic;

import com.syntech.chess.rules.MovePriorities;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Move {
    private final Point endPosition;
    private int priority, power;

    public Move(int row, int col) {
        this(row, col, 0);
    }

    public Move(int row, int col, int priority) {
        this(row, col, priority, 0);
    }

    public Move(int row, int col, int priority, int power) {
        this.endPosition = new Point(row, col);
        this.priority = priority;
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return priority == move.priority && power == move.power &&
                Objects.equals(endPosition, move.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endPosition, power, priority);
    }

    public Point getEndPosition() {
        return endPosition;
    }

    public int getRow() {
        return endPosition.x;
    }

    public int getCol() {
        return endPosition.y;
    }

    public int getPriority() {
        return priority;
    }

    public int getPower() {
        return power;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @NotNull
    public static ArrayList<Point> toListOfPoints(@NotNull ArrayList<Move> moves) {
        ArrayList<Point> points = new ArrayList<>();
        for (Move m : moves) {
            points.add(m.endPosition);
        }
        return points;
    }

    public static boolean contains(@NotNull ArrayList<Move> moves, int row, int col) {
        for (Move move : moves) {
            if (move.getRow() == row && move.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWithPowerAtLeast(int power, @NotNull ArrayList<Move> moves, int row, int col) {
        for (Move move : moves) {
            if (move.getRow() == row && move.getCol() == col && move.getPower() >= power) {
                return true;
            }
        }
        return false;
    }

    public static void setPriority(ArrayList<Move> moves, int priority) {
        for (Move move : moves) {
            move.setPriority(priority);
        }
    }

    public static void setPower(ArrayList<Move> moves, int power) {
        for (Move move : moves) {
            move.setPower(power);
        }
    }

    public int getCapturePriority(Board board) {
        return MovePriorities.getCapturePriority(board.getType(endPosition.x, endPosition.y));
    }
}
