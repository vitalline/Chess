package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class DoublePawnType extends SpecialFirstMoveType {

    protected Side side;

    @Contract(pure = true)
    public DoublePawnType(Side side) {
        this.side = side;
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeMovement(position, board, MovementRules.getPawnMoveDirection(this.side), hasNotMoved(), moves);
        return moves;
    }

    @Override
    public ArrayList<Point> getControlledCells(@NotNull Point position, @NotNull Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addPawnLikeControlledCells(position, board, MovementRules.getPawnMoveDirection(this.side), moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableThreatsOn(@NotNull Point position, @NotNull Board board, Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeThreatening(position, board, side, MovementRules.getPawnMoveDirection(this.side), moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.DOUBLE_PAWN;
    }
}
