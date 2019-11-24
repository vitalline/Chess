package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.KnightType;

public class Knight extends Piece {

    public Knight(Side side) {
        super(side);
        movementType = new KnightType();
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
