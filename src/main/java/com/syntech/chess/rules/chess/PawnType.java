package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class PawnType extends MovementType {

    protected Side side;

    public PawnType(Side side) {
        this.side = side;
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeMovement(position, board, MovementRules.getPawnMoveDirection(this.side), false, moves);
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
        return PieceType.PAWN;
    }
}
