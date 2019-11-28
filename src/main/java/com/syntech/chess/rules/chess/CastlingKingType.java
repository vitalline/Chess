package com.syntech.chess.rules.chess;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementRules;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.util.ArrayList;

public class CastlingKingType extends SpecialFirstMoveType {

    private Side side;

    @Contract(pure = true)
    public CastlingKingType(Side side) {
        this.side = side;
    }

    @Override
    public ArrayList<Point> getControlledCells(Point position, Board board) {
        ArrayList<Point> moves = new ArrayList<>();
        MovementRules.addKingLikeControlledCells(position, board, moves);
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Point position, Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addKingLikeMovement(position, board, side, moves);
        if (hasNotMoved()
                && moves.contains(new Move(position.x, position.y + 1))
                && board.isFree(position.x, position.y + 1)
                && !board.getAllControlledCells(side.getOpponent()).contains(new Point(position.x, position.y + 1))
                && !board.isInCheck(side)
                && board.getPiece(position.x, position.y + 3).getMovementType() instanceof CastlingRookType
                && ((SpecialFirstMoveType) board.getPiece(position.x, position.y + 3).getMovementType()).hasNotMoved()
        ) {
            moves.add(new Move(position.x, position.y + 2));
        }
        if (hasNotMoved()
                && moves.contains(new Move(position.x, position.y - 1))
                && board.isFree(position.x, position.y - 1)
                && !board.getAllControlledCells(side.getOpponent()).contains(new Point(position.x, position.y - 1))
                && !board.isInCheck(side)
                && board.getPiece(position.x, position.y - 4).getMovementType() instanceof CastlingRookType
                && ((SpecialFirstMoveType) board.getPiece(position.x, position.y - 4).getMovementType()).hasNotMoved()
        ) {
            moves.add(new Move(position.x, position.y - 2));
        }
        return moves;
    }

    @Override
    public ArrayList<Move> getAvailableThreatsOn(Point position, Board board, Side side) {
        ArrayList<Move> moves = new ArrayList<>();
        MovementRules.addKingLikeThreatening(position, board, side, this.side, moves);
        return moves;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
