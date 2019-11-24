package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.KingType;

public class King extends Piece {

    public King(Side side) {
        super(side);
        movementType = new KingType(side);
    }
}
