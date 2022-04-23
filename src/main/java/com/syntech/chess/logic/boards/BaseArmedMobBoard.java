package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.chess.QueenType;
import org.jetbrains.annotations.NotNull;

public abstract class BaseArmedMobBoard extends NeutralAIBoard {

    public BaseArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public BaseArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    protected abstract double getDamage(LevellingForcedPiece piece);

    @Override
    protected void doAIAction() {
        Piece piece = getSelectedPiece();
        if (!(piece instanceof LevellingForcedPiece castPiece)) return;
        for (Move detect : new QueenType(castPiece.getSide()).getAvailableCapturesWithoutSpecialRules(castPiece.getPosition(), this)) {
            Piece attacker = getPiece(detect.getEndRow(), detect.getEndCol());
            if (attacker.getSide() == Side.NEUTRAL) {
                castPiece.damage(this, getDamage(castPiece));
            }
        }
    }
}
