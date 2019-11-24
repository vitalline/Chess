package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.DoublePawnType;

public class ForcedDoublePawn extends Piece {

    public ForcedDoublePawn(Side side) {
        super(side);
        movementType = new DoublePawnType(side);
    }
}
