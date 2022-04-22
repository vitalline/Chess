package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public abstract class NeutralAIBoard extends AIBoard {
    protected ArrayList<Point> neutralPieces;

    public NeutralAIBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public NeutralAIBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    public void updatePieces() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        neutralPieces = new ArrayList<>();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (!isFree(row, col)) {
                    addPiece(row, col);
                    if (getSide(row, col) == Side.NEUTRAL) neutralPieces.add(new Point(row, col));
                }
            }
        }
        allAvailableWhiteMoves = null;
        allAvailableBlackMoves = null;
    }

}
