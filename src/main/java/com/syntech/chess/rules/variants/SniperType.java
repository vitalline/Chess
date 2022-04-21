package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.chess.BishopType;

import java.awt.*;
import java.util.ArrayList;

public class SniperType extends BishopType {

    public SniperType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = super.getAvailableCapturesWithoutSpecialRules(position, board);
        Move.setPriority(moves, MovePriorities.SNIPER_SHOT);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.SNIPER;
    }
}
