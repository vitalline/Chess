package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.RookType;

import java.awt.*;

public class ForcedRook extends ForcedPiece {

    public ForcedRook(Side side, Point position) {
        super(side, position);
        movementType = new RookType();
    }

    public ForcedRook(Side side) {
        super(side);
        movementType = new RookType();
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

}
