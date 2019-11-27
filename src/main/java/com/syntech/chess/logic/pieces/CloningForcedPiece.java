package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceFactory;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.NotNull;

public class CloningForcedPiece extends ForcedPiece {
    public CloningForcedPiece(Side side, MovementType movementType) {
        super(side, movementType, null);
    }

    public void move(@NotNull Board board, int row, int col) {
        try {
            if (!board.isFree(row, col)) {
                board.placePiece(PieceFactory.cell(), position);
            } else {
                board.placePiece((Piece) this.clone(), position);
            }
            board.placePiece(this, row, col);
        } catch (CloneNotSupportedException ignored) {
            // do nothing. a piece that was supposed to be moving,
            // but didn't, would be easier to catch than those
            // rather nasty cloning bugs (i hope).
        }
        if (movementType instanceof SpecialFirstMoveType) {
            ((SpecialFirstMoveType) movementType).move();
        }
    }
}
