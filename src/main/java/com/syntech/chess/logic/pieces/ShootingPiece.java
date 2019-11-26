package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShootingPiece extends Piece {

    public ShootingPiece(Side side, MovementType movementType) {
        super(side, movementType);
        baseType = PieceBaseType.SHOOTING_PIECE;
    }

    public ArrayList<Move> getAvailableMoves(@NotNull Board board) {
        return new ArrayList<>();
    }

    public void move(@NotNull Board board, int row, int col) {
        board.placePiece(PieceFactory.cell(), row, col);
        if (movementType instanceof SpecialFirstMoveType) {
            ((SpecialFirstMoveType) movementType).move();
        }
    }
}
