package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class DoublePawnType extends SpecialFirstMoveType {

    public DoublePawnType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeMovement(position, board, hasNotMoved(), moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addPawnLikeCapturing(position, board, moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
