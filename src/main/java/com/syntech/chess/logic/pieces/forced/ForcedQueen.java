package com.syntech.chess.logic.pieces.forced;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.ForcedPiece;
import com.syntech.chess.rules.chess.QueenType;

import java.awt.*;

public class ForcedQueen extends ForcedPiece {

    public ForcedQueen(Side side, Point position) {
        super(side, position);
        movementType = new QueenType();
    }

    public ForcedQueen(Side side) {
        super(side);
        movementType = new QueenType();
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

}
