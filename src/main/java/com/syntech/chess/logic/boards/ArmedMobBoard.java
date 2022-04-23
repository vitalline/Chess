package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import static com.syntech.chess.logic.PieceFactory.mob;

public class ArmedMobBoard extends BaseArmedMobBoard {

    public ArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public ArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    protected double getDamage(@NotNull LevellingForcedPiece piece) {
        return switch (piece.getType()) {
            case PAWN -> 20;
            case KING -> 17;
            case BISHOP -> 14;
            case ROOK -> 11;
            case QUEEN -> 8;
            case AMAZON -> 5;
            default -> 0;
        };
    }

    @Override
    protected void doAIAction() {
        if (neutralPieces.size() == 0) {
            for (int row = getHeight() / 2 - 1; row <= getHeight() / 2; row++) {
                for (int col = 0; col <= getWidth(); col++) {
                    if (isFree(row, col)) {
                        placePiece(mob(true), row, col);
                    }
                }
            }
            return;
        }
        super.doAIAction();
    }
}
