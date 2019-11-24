package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.QueenType;

public class Queen extends Piece {

    public Queen(Side side) {
        super(side);
        movementType = new QueenType();
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

}
