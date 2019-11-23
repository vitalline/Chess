package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Side;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public abstract class ForcedPiece extends Piece {

    public ForcedPiece(Side side, Point position) {
        super(side, position);
    }

    public ForcedPiece(Side side) {
        super(side);
    }

    public ArrayList<Point> getAvailableMoves(@NotNull Board board) {
        if (board.getAllAvailableCaptures(side).isEmpty()) {
            ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(board);
            return excludeMovesThatLeaveKingInCheck(board, moves);
        }
        return new ArrayList<>();
    }

}
