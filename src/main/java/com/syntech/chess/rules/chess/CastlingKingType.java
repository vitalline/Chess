package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.SpecialFirstMoveType;

import java.awt.*;
import java.util.ArrayList;

public class CastlingKingType extends SpecialFirstMoveType {

    public CastlingKingType(Side side) {
        super(side);
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addKingLikeMovement(position, board, moves);
        moves = board.excludeMovesThatLeaveKingInCheck(side, moves);
        if (hasNotMoved()
                && Move.contains(moves, position.x, position.y + 1)
                && !board.isInCheck(side)
                && board.isFree(position.x, position.y + 2)
                && board.getPiece(position.x, position.y + 3).getMovementType() instanceof CastlingRookType
                && ((SpecialFirstMoveType) board.getPiece(position.x, position.y + 3).getMovementType()).hasNotMoved()
        ) {
            moves.add(new Move(getType(), position, position.x, position.y + 2));
        }
        if (hasNotMoved()
                && Move.contains(moves, position.x, position.y - 1)
                && !board.isInCheck(side)
                && board.isFree(position.x, position.y - 2)
                && board.isFree(position.x, position.y - 3)
                && board.getPiece(position.x, position.y - 4).getMovementType() instanceof CastlingRookType
                && ((SpecialFirstMoveType) board.getPiece(position.x, position.y - 4).getMovementType()).hasNotMoved()
        ) {
            moves.add(new Move(getType(), position, position.x, position.y - 2));
        }
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addKingLikeCapturing(position, board, moves);
        return moves;
    }

    @Override
    public int getEvaluationCost() {
        return 0;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
