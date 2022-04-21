package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.PromotionInfo;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class ModestForcedPiece extends Piece {

    public ModestForcedPiece(Side side, MovementType movementType) {
        this(side, movementType, null);
    }

    public ModestForcedPiece(Side side, MovementType movementType, PromotionInfo promotionInfo) {
        super(side, movementType, promotionInfo);
        baseType = PieceBaseType.MODEST_FORCED_PIECE;
    }

    @Override
    public ArrayList<Move> getAvailableCaptures(@NotNull Board board) {
        ArrayList<Move> moves = super.getAvailableCaptures(board);
        for (Move move : moves) {
            move.setPriority(getCapturePriority(board, move.getEndPosition()));
        }
        return moves;
    }

    private int getCapturePriority(@NotNull Board board, @NotNull Point position) {
        return MovePriorities.getCapturePriority(board.getType(position.x, position.y));
    }
}
