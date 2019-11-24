package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.RookType;

public class ForcedRook extends ForcedPiece {

    public ForcedRook(Side side) {
        super(side);
        movementType = new RookType();
    }
}
