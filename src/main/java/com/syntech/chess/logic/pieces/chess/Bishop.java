package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.BishopType;

public class Bishop extends Piece {

    public Bishop(Side side) {
        super(side);
        movementType = new BishopType();
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
