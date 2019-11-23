package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.RookType;

import java.awt.*;

public class Rook extends Piece {

    public Rook(Side side, Point position) {
        super(side, position);
        movementType = new RookType();
    }

    public Rook(Side side) {
        super(side);
        movementType = new RookType();
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

}
