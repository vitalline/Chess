package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class ForcedPiece extends Piece {

    public ForcedPiece(Side side, MovementType movementType) {
        super(side, movementType);
        baseType = PieceBaseType.FORCED_PIECE;
    }

    public ArrayList<Point> getAvailableMoves(@NotNull Board board) {
        if (board.getAllAvailableCaptures(side).isEmpty()) {
            ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(board);
            return excludeMovesThatLeaveKingInCheck(board, moves);
        }
        return new ArrayList<>();
    }

}
