package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class MovementRules {
    public static void addDiagonalMovement(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        int d = 1;
        while (board.isFree(pos.x + d, pos.y + d)) {
            moves.add(new Move(type, pos, pos.x + d, pos.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x + d, pos.y - d)) {
            moves.add(new Move(type, pos, pos.x + d, pos.y - d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y + d)) {
            moves.add(new Move(type, pos, pos.x - d, pos.y + d));
            d++;
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y - d)) {
            moves.add(new Move(type, pos, pos.x - d, pos.y - d));
            d++;
        }
    }

    public static void addDiagonalCapturing(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int d = 1;
        while (board.isFree(pos.x + d, pos.y + d)) {
            d++;
        }
        if (board.getSide(pos.x + d, pos.y + d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + d, pos.y + d));
        }
        d = 1;
        while (board.isFree(pos.x + d, pos.y - d)) {
            d++;
        }
        if (board.getSide(pos.x + d, pos.y - d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + d, pos.y - d));
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y + d)) {
            d++;
        }
        if (board.getSide(pos.x - d, pos.y + d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - d, pos.y + d));
        }
        d = 1;
        while (board.isFree(pos.x - d, pos.y - d)) {
            d++;
        }
        if (board.getSide(pos.x - d, pos.y - d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - d, pos.y - d));
        }
    }

    public static void addOrthogonalMovement(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        int d = 0;
        while (board.isFree(pos.x + ++d, pos.y)) {
            moves.add(new Move(type, pos, pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x + --d, pos.y)) {
            moves.add(new Move(type, pos, pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + ++d)) {
            moves.add(new Move(type, pos, pos.x, pos.y + d));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + --d)) {
            moves.add(new Move(type, pos, pos.x, pos.y + d));
        }
    }

    public static void addOrthogonalCapturing(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int d = 0;
        while (board.isFree(pos.x + ++d, pos.y)) {
        }
        if (board.getSide(pos.x + d, pos.y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x + --d, pos.y)) {
        }
        if (board.getSide(pos.x + d, pos.y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + d, pos.y));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + ++d)) {
        }
        if (board.getSide(pos.x, pos.y + d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x, pos.y + d));
        }
        d = 0;
        while (board.isFree(pos.x, pos.y + --d)) {
        }
        if (board.getSide(pos.x, pos.y + d) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x, pos.y + d));
        }
    }

    public static void addLeapingMovement(@NotNull Point pos, @NotNull Board board, int x, int y, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        if (board.isFree(pos.x + x, pos.y + y)) {
            moves.add(new Move(type, pos, pos.x + x, pos.y + y));
        }
        if (board.isFree(pos.x + y, pos.y + x)) {
            moves.add(new Move(type, pos, pos.x + y, pos.y + x));
        }
        if (board.isFree(pos.x - x, pos.y + y)) {
            moves.add(new Move(type, pos, pos.x - x, pos.y + y));
        }
        if (board.isFree(pos.x - y, pos.y + x)) {
            moves.add(new Move(type, pos, pos.x - y, pos.y + x));
        }
        if (board.isFree(pos.x + x, pos.y - y)) {
            moves.add(new Move(type, pos, pos.x + x, pos.y - y));
        }
        if (board.isFree(pos.x + y, pos.y - x)) {
            moves.add(new Move(type, pos, pos.x + y, pos.y - x));
        }
        if (board.isFree(pos.x - x, pos.y - y)) {
            moves.add(new Move(type, pos, pos.x - x, pos.y - y));
        }
        if (board.isFree(pos.x - y, pos.y - x)) {
            moves.add(new Move(type, pos, pos.x - y, pos.y - x));
        }
    }

    public static void addLeapingCapturing(@NotNull Point pos, @NotNull Board board, int x, int y, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        if (board.getSide(pos.x + x, pos.y + y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + x, pos.y + y));
        }
        if (board.getSide(pos.x + y, pos.y + x) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + y, pos.y + x));
        }
        if (board.getSide(pos.x - x, pos.y + y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - x, pos.y + y));
        }
        if (board.getSide(pos.x - y, pos.y + x) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - y, pos.y + x));
        }
        if (board.getSide(pos.x + x, pos.y - y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + x, pos.y - y));
        }
        if (board.getSide(pos.x + y, pos.y - x) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + y, pos.y - x));
        }
        if (board.getSide(pos.x - x, pos.y - y) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - x, pos.y - y));
        }
        if (board.getSide(pos.x - y, pos.y - x) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x - y, pos.y - x));
        }
    }

    public static int getPawnMoveDirection(Side side) {
        return side == Side.WHITE ? 1 : -1;
    }

    public static void addPawnLikeMovement(@NotNull Point pos, @NotNull Board board, boolean hasNotMoved, ArrayList<Move> moves) {
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

    public static void addPawnLikeCapturing(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        int moveDirection = getPawnMoveDirection(side);
        if (board.getSide(pos.x + moveDirection, pos.y - 1) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + moveDirection, pos.y - 1));
        }
        if (board.getSide(pos.x + moveDirection, pos.y + 1) == side.getOpponent()) {
            moves.add(new Move(type, pos, pos.x + moveDirection, pos.y + 1));
        }
    }

    public static void addKingLikeMovement(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.isFree(pos.x + i, pos.y + j)) {
                    moves.add(new Move(type, pos, pos.x + i, pos.y + j));
                }
            }
        }
    }

    public static void addKingLikeCapturing(@NotNull Point pos, @NotNull Board board, ArrayList<Move> moves) {
        PieceType type = board.getType(pos.x, pos.y);
        Side side = board.getSide(pos.x, pos.y);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (board.getSide(pos.x + i, pos.y + j) == side.getOpponent()) {
                    moves.add(new Move(type, pos, pos.x + i, pos.y + j));
                }
            }
        }
    }

}
