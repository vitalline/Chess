package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.KnightType;

import java.awt.*;

public class ForcedKnight extends ForcedPiece {

    public ForcedKnight(Side side, Point position) {
        super(side, position);
        movementType = new KnightType();
    }

    public ForcedKnight(Side side) {
        super(side);
        movementType = new KnightType();
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
}
