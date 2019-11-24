package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.DoublePawnType;

public class DoublePawn extends Piece {

    public DoublePawn(Side side) {
        super(side);
        movementType = new DoublePawnType(side);
    }
}
