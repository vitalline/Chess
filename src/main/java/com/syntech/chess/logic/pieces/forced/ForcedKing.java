package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.KingType;

import java.awt.*;

public class ForcedKing extends ForcedPiece {

    public ForcedKing(Side side, Point position) {
        super(side, position);
        movementType = new KingType(side);
    }

    public ForcedKing(Side side) {
        super(side);
        movementType = new KingType(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
