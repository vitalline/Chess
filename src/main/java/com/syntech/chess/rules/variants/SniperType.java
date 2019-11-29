package com.syntech.chess.rules.variants;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.chess.BishopType;

import java.awt.*;
import java.util.ArrayList;

public class SniperType extends BishopType {

    @Override
    public ArrayList<Move> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Move> moves = super.getAvailableThreatsOn(position, board, side);
        Move.setPriority(moves, MovePriorities.SNIPER_SHOT);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.SNIPER;
    }
}
