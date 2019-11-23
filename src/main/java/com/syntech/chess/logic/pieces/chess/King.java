package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.KingType;

import java.awt.*;

public class King extends Piece {

    public King(Side side, Point position) {
        super(side, position);
        movementType = new KingType(side);
    }

    public King(Side side) {
        super(side);
        movementType = new KingType(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
