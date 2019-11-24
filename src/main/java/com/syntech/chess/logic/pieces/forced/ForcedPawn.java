package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.PawnType;

public class ForcedPawn extends ForcedPiece {

    public ForcedPawn(Side side) {
        super(side);
        movementType = new PawnType(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
