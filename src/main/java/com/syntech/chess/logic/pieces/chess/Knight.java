package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.KnightType;

import java.awt.*;

public class Knight extends Piece {

    public Knight(Side side, Point position) {
        super(side, position);
        movementType = new KnightType();
    }

    public Knight(Side side) {
        super(side);
        movementType = new KnightType();
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
