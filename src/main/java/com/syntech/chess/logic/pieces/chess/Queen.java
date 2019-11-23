package com.syntech.chess.logic.pieces.chess;

import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.QueenType;

import java.awt.*;

public class Queen extends Piece {

    public Queen(Side side, Point position) {
        super(side, position);
        movementType = new QueenType();
    }

    public Queen(Side side) {
        super(side);
        movementType = new QueenType();
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

}
