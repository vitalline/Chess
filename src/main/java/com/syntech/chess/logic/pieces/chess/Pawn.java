package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.PawnType;

import java.awt.*;

public class Pawn extends Piece {

    public Pawn(Side side, Point position) {
        super(side, position);
        movementType = new PawnType(side);
    }

    public Pawn(Side side) {
        super(side);
        movementType = new PawnType(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
