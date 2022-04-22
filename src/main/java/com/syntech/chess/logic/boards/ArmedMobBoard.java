package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.QueenType;
import org.jetbrains.annotations.NotNull;

import static com.syntech.chess.logic.PieceFactory.mob;

public class ArmedMobBoard extends NeutralAIBoard {

    public ArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public ArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    @Override
    protected void doAIAction() {
        Piece lastMovedPiece = getSelectedPiece();
        if (!(lastMovedPiece instanceof LevellingForcedPiece)) return;
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
        for (Move detect : lastMovedPiece.getAvailableCapturesWithoutSpecialRules(this, new QueenType(lastMovedPiece.getSide()))) {
            Piece piece = getPiece(detect.getEndRow(), detect.getEndCol());
            if (piece.getSide() == Side.NEUTRAL) {
                switch (lastMovedPiece.getType()) {
                    case PAWN:   ((LevellingForcedPiece) lastMovedPiece).damage(this, 20); break;
                    case KING:   ((LevellingForcedPiece) lastMovedPiece).damage(this, 17); break;
                    case BISHOP: ((LevellingForcedPiece) lastMovedPiece).damage(this, 14); break;
                    case ROOK:   ((LevellingForcedPiece) lastMovedPiece).damage(this, 11); break;
                    case QUEEN:  ((LevellingForcedPiece) lastMovedPiece).damage(this,  8); break;
                    case AMAZON: ((LevellingForcedPiece) lastMovedPiece).damage(this,  5); break;
                    case KNIGHT: break;
                }
            }
        }
    }
}
