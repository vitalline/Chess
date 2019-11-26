package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ModestForcedPiece extends Piece {

    public ModestForcedPiece(Side side, MovementType movementType) {
        super(side, movementType);
        baseType = PieceBaseType.MODEST_FORCED_PIECE;
    }

    public ArrayList<Move> getAvailableCaptures(@NotNull Board board) {
        ArrayList<Move> moves = super.getAvailableCaptures(board);
        for (Move move : moves) {
            move.setPriority(move.getCapturePriority(board));
        }
        return moves;
    }
}
