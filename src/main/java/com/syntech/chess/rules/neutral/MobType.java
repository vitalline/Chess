package com.syntech.chess.rules.neutral;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.MovementType;

import java.awt.*;
import java.util.ArrayList;

public class MobType extends MovementType {

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addMobMovement(moves, position, board);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        return new ArrayList<>();
    }

    @Override
    public int getEvaluationCost() {
        return 0;
    }

    @Override
    public PieceType getType() {
        return PieceType.MOB;
    }
}
