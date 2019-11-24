package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.RookType;

public class Rook extends Piece {

    public Rook(Side side) {
        super(side);
        movementType = new RookType();
    }
}
