package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class ClimbingPawnType extends MovementType {

    public ClimbingPawnType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        int dir = MovementRules.getPawnMoveDirection(side);
        MovementRules.addPawnLikeMovement(moves, position, board,(side == Side.WHITE) == ((int) position.getX() < board.getHeight() / 2 - dir * 2));
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeCapturing(moves, position, board);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 1;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
