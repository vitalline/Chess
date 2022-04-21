package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

public abstract class AIBoard extends Board {
    public AIBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public AIBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    protected abstract void doAIAction();

    protected void advanceTurn() {
        doAIAction();
        super.advanceTurn();
    }
}
