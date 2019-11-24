package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.KingType;

public class ForcedKing extends ForcedPiece {

    public ForcedKing(Side side) {
        super(side);
        movementType = new KingType(side);
    }
}
