package com.syntech.chess.logic;

import com.syntech.chess.rules.MovePriorities;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Move {
    private final Point endPosition;
    private int priority;

    @Contract(pure = true)
    public Move(int row, int col) {
        this.endPosition = new Point(row, col);
        this.priority = MovePriorities.NORMAL_MOVE;
    }

    @Contract(pure = true)
    public Move(int row, int col, int priority) {
        this.endPosition = new Point(row, col);
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return priority == move.priority &&
                Objects.equals(endPosition, move.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endPosition, priority);
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

    public void setPriority(int priority) {
        this.priority = priority;
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

    public static void setPriority(ArrayList<Move> moves, int priority) {
        for (Move move : moves) {
            move.setPriority(priority);
        }
    }

    public int getCapturePriority(Board board) {
        return MovePriorities.getCapturePriority(board.getType(endPosition.x, endPosition.y));
    }
}
