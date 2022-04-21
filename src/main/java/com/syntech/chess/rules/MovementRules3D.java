package com.syntech.chess.rules;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board3D;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class MovementRules3D {

    private static void addDirectionalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, PieceType type, int x, int y, int z, int sx, int sy, int sz, int maxDistance) {
        int d = 1;
        while (board.isFree(x + sx * d, board.getInternalCol(y + sy * d, z + sz * d)) && d <= maxDistance) {
            moves.add(new Move(type, pos, x + sx * d, board.getInternalCol(y + sy * d, z + sz * d)));
            d++;
        }
    }

    private static void addDirectionalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, PieceType type, Side side, int x, int y, int z, int sx, int sy, int sz, int maxDistance) {
        int d = 1;
        while (board.isFree(x + sx * d, board.getInternalCol(y + sy * d, z + sz * d))) {
            d++;
        }
        if (board.getSide(x + sx * d, board.getInternalCol(y + sy * d, z + sz * d)) == side.getOpponent() && d <= maxDistance) {
            moves.add(new Move(type, pos, x + sx * d, board.getInternalCol(y + sy * d, z + sz * d)));
        }
    }

    public static void addOrthogonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 3; n++) {
            for (int i : new int[]{-1, 1}) {
                int sx = n > 0 ? n > 1 ? i : 0 : 0;
                int sy = n > 0 ? n > 1 ? 0 : i : 0;
                int sz = n > 0 ? n > 1 ? 0 : 0 : i;
                addDirectionalMovement(moves, pos, board, type, x, y, z, sx, sy, sz, maxDistance);
            }
        }
    }

    public static void addOrthogonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 3; n++) {
            for (int i : new int[]{-1, 1}) {
                int sx = n > 0 ? n > 1 ? i : 0 : 0;
                int sy = n > 0 ? n > 1 ? 0 : i : 0;
                int sz = n > 0 ? n > 1 ? 0 : 0 : i;
                addDirectionalCapturing(moves, pos, board, type, side, x, y, z, sx, sy, sz, maxDistance);
            }
        }
    }

    public static void addDiagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 3; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    int sx = n > 0 ? n > 1 ? i : j : 0;
                    int sy = n > 0 ? n > 1 ? 0 : i : j;
                    int sz = n > 0 ? n > 1 ? j : 0 : i;
                    addDirectionalMovement(moves, pos, board, type, x, y, z, sx, sy, sz, maxDistance);
                }
            }
        }
    }

    public static void addDiagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 3; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    int sx = n > 0 ? n > 1 ? i : j : 0;
                    int sy = n > 0 ? n > 1 ? 0 : i : j;
                    int sz = n > 0 ? n > 1 ? j : 0 : i;
                    addDirectionalCapturing(moves, pos, board, type, side, x, y, z, sx, sy, sz, maxDistance);
                }
            }
        }
    }

    public static void addTriagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int i : new int[]{-1, 1}) {
            for (int j : new int[]{-1, 1}) {
                for (int k : new int[]{-1, 1}) {
                    addDirectionalMovement(moves, pos, board, type, x, y, z, i, j, k, maxDistance);
                }
            }
        }
    }

    public static void addTriagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int maxDistance) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int i : new int[]{-1, 1}) {
            for (int j : new int[]{-1, 1}) {
                for (int k : new int[]{-1, 1}) {
                    addDirectionalCapturing(moves, pos, board, type, side, x, y, z, i, j, k, maxDistance);
                }
            }
        }
    }

    public static void addLeapingMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int a, int b, int c) {
        PieceType type = board.getType(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 6; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    for (int k : new int[]{-1, 1}) {
                        int dx = i * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? a : b : c : a : c : b);
                        int dy = j * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? c : a : b : b : a : c);
                        int dz = k * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? b : c : a : c : b : a);
                        if (dx == 0 && i == 1) continue;
                        if (dy == 0 && j == 1) continue;
                        if (dz == 0 && k == 1) continue;
                        if (board.isFree(x + dx, board.getInternalCol(y + dy, z + dz))) {
                            moves.add(new Move(type, pos, x + dx, board.getInternalCol(y + dy, z + dz)));
                        }
                    }
                }
            }
        }
    }

    public static void addLeapingCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board, int a, int b, int c) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        for (int n = 0; n < 6; n++) {
            for (int i : new int[]{-1, 1}) {
                for (int j : new int[]{-1, 1}) {
                    for (int k : new int[]{-1, 1}) {
                        int dx = i * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? a : b : c : a : c : b);
                        int dy = j * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? c : a : b : b : a : c);
                        int dz = k * (n > 0 ? n > 1 ? n > 2 ? n > 3 ? n > 4 ? b : c : a : c : b : a);
                        if (dx == 0 && i == 1) continue;
                        if (dy == 0 && j == 1) continue;
                        if (dz == 0 && k == 1) continue;
                        if (board.getSide(x + dx, board.getInternalCol(y + dy, z + dz)) == side.getOpponent()) {
                            moves.add(new Move(type, pos, x + dx, board.getInternalCol(y + dy, z + dz)));
                        }
                    }
                }
            }
        }
    }

    public static void addPawnLikeMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        int moveDirection = MovementRules.getPawnMoveDirection(side);
        if (!board.isFree(x, board.getInternalCol(y, z + moveDirection))) {
            return;
        }
        moves.add(new Move(type, pos, x, board.getInternalCol(y, z + moveDirection)));
    }

    public static void addPawnLikeCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int x = pos.x, y = board.getCol(pos.y), z = board.getBoard(pos.y);
        int moveDirection = MovementRules.getPawnMoveDirection(side);
        for (int n = 0; n < 2; n++) {
            for (int i : new int[]{-1, 1}) {
                int sx = n > 0 ? i : 0;
                int sy = n > 0 ? 0 : i;
                if (board.getSide(x + sx, board.getInternalCol(y + sy, z + moveDirection)) == side.getOpponent()) {
                    moves.add(new Move(type, pos, x + sx, board.getInternalCol(y + sy, z + moveDirection)));
                }
            }
        }
    }

    public static void addOrthogonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addOrthogonalMovement(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addOrthogonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addOrthogonalCapturing(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addDiagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addDiagonalMovement(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addDiagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addDiagonalCapturing(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addTriagonalMovement(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addTriagonalMovement(moves, pos, board, Integer.MAX_VALUE);
    }

    public static void addTriagonalCapturing(ArrayList<Move> moves, @NotNull Point pos, @NotNull Board3D board) {
        addTriagonalCapturing(moves, pos, board, Integer.MAX_VALUE);
    }

}
