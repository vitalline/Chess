package com.syntech.chess.rules;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class MovementRules {

    private static void addDirectionalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, PieceType type, int sx, int sy, int maxDistance) {
        int d = 1;
        while (board.isFree(pos.x + sx * d, pos.y + sy * d) && d <= maxDistance) {
            moves.add(new Move(type, pos, pos.x + sx * d, pos.y + sy * d));
            d++;
        }
    }


    private static void addDirectionalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, PieceType type, Side side, int sx, int sy, int maxDistance) {
        int d = 1;
        while (board.isFree(pos.x + sx * d, pos.y + sy * d)) {
            d++;
        }
        if (board.isCapturable(pos.x + sx * d, pos.y + sy * d, side) && d <= maxDistance) {
            moves.add(new Move(type, pos, pos.x + sx * d, pos.y + sy * d));
        }
    }

    public static void addOrthogonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        for (int n = 0; n < 2; n++) {
            for (int i : new int[]{-1, 1}) {
                int sx = n > 0 ? i : 0;
                int sy = n > 0 ? 0 : i;
                addDirectionalMovement(moves, pos, board, type, sx, sy, maxDistance);
            }
        }
    }

    public static void addOrthogonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        for (int n = 0; n < 2; n++) {
            for (int i : new int[]{-1, 1}) {
                int sx = n > 0 ? i : 0;
                int sy = n > 0 ? 0 : i;
                addDirectionalCapturing(moves, pos, board, type, side, sx, sy, maxDistance);
            }
        }
    }

    public static void addDiagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        for (int i : new int[]{-1, 1}) {
            for (int j : new int[]{-1, 1}) {
                addDirectionalMovement(moves, pos, board, type, i, j, maxDistance);
            }
        }
    }

    public static void addDiagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        for (int i : new int[]{-1, 1}) {
            for (int j : new int[]{-1, 1}) {
                addDirectionalCapturing(moves, pos, board, type, side, i, j, maxDistance);
            }
        }
    }

    public static void addLeapingMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int a, int b) {
        PieceType type = board.getType(pos.x, pos.y);
        for (int n = 0; n < 2; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    int dx = i * (n > 0 ? a : b);
                    int dy = j * (n > 0 ? b : a);
                    if (dx == 0 && i == 1) continue;
                    if (dy == 0 && j == 1) continue;
                    if (board.isFree(pos.x + dx, pos.y + dy)) {
                        moves.add(new Move(type, pos, pos.x + dx, pos.y + dy));
                    }
                }
            }
        }
    }

    public static void addLeapingCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, int a, int b) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        for (int n = 0; n < 2; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    int dx = i * (n > 0 ? a : b);
                    int dy = j * (n > 0 ? b : a);
                    if (dx == 0 && i == 1) continue;
                    if (dy == 0 && j == 1) continue;
                    if (board.isCapturable(pos.x + dx, pos.y + dy, side)) {
                        moves.add(new Move(type, pos, pos.x + dx, pos.y + dy));
                    }
                }
            }
        }
    }

    public static int getPawnMoveDirection(Side side) {
        return side == Side.WHITE ? 1 : -1;
    }

    public static void addPawnLikeMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board, boolean hasNotMoved) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int moveDirection = getPawnMoveDirection(side);
        if (!board.isFree(pos.x + moveDirection, pos.y)) {
            return;
        }
        moves.add(new Move(type, pos, pos.x + moveDirection, pos.y));
        if (hasNotMoved && board.isFree(pos.x + moveDirection * 2, pos.y)) {
            moves.add(new Move(type, pos, pos.x + moveDirection * 2, pos.y));
        }
    }

    public static void addPawnLikeCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int moveDirection = getPawnMoveDirection(side);
        if (board.isCapturable(pos.x + moveDirection, pos.y - 1, side)) {
            moves.add(new Move(type, pos, pos.x + moveDirection, pos.y - 1));
        }
        if (board.isCapturable(pos.x + moveDirection, pos.y + 1, side)) {
            moves.add(new Move(type, pos, pos.x + moveDirection, pos.y + 1));
        }
    }

    public static void addDiagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        addDiagonalMovement(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addDiagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        addDiagonalCapturing(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addOrthogonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        addOrthogonalMovement(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addOrthogonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        addOrthogonalCapturing(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addMobMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board board) {
        PieceType type = board.getType(pos.x, pos.y);
        addDirectionalMovement(moves, pos, board, type, getPawnMoveDirection(board.getTurnSide()), 0, 1);
        addDirectionalMovement(moves, pos, board, type, 0, pos.y < (board.getWidth() / 2) ? -1 : 1, 1);
        addDirectionalMovement(moves, pos, board, type, 0, pos.y < (board.getWidth() / 2) ? 1 : -1, 1);
        addDirectionalMovement(moves, pos, board, type, -getPawnMoveDirection(board.getTurnSide()), 0, 1);
    }

}
