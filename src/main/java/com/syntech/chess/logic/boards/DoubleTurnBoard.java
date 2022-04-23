package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovePriorities;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class DoubleTurnBoard extends NeutralAIBoard {

    public DoubleTurnBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public DoubleTurnBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    @NotNull
    protected ArrayList<Move> getAvailableMoves(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableMoves = getPiece(row, col).getAvailableMoves(this);
            if (turn % 2 == 0) availableMoves = excludeMovesThatLeaveKingInCheck(turnIndicator.getOpponent(), availableMoves);
            if (priority) availableMoves = topPriorityMoves(availableMoves, row, col);
            return availableMoves;
        }
        return new ArrayList<>();
    }

    @NotNull
    protected ArrayList<Move> getAvailableCaptures(int row, int col) {
        if (getSide(row, col) == getTurnSide()) {
            ArrayList<Move> availableCaptures = getPiece(row, col).getAvailableCaptures(this);
            if (turn % 2 == 0) availableCaptures = excludeMovesThatLeaveKingInCheck(turnIndicator.getOpponent(), availableCaptures);
            if (priority) availableCaptures = topPriorityMoves(availableCaptures, row, col);
            return availableCaptures;
        }
        return new ArrayList<>();
    }

    public ArrayList<Move> getAllAvailableMoves(Side side) {
        if (side == Side.WHITE && allAvailableWhiteMoves != null) return allAvailableWhiteMoves;
        if (side == Side.BLACK && allAvailableBlackMoves != null) return allAvailableBlackMoves;
        ArrayList<Move> moves = new ArrayList<>();
        for (Point p : getPositions(side)) {
            moves.addAll(getPiece(p.x, p.y).getAvailableMoves(this));
            moves.addAll(getPiece(p.x, p.y).getAvailableCaptures(this));
        }
        if (turn % 2 == 0) moves = excludeMovesThatLeaveKingInCheck(turnIndicator.getOpponent(), moves);
        if (priority) moves = MovePriorities.topPriorityMoves(moves);
        if (side == Side.WHITE) allAvailableWhiteMoves = moves;
        if (side == Side.BLACK) allAvailableBlackMoves = moves;
        return moves;
    }

    @Override
    protected void doAIAction() {
        if (turn % 2 == 0) {
            turnIndicator = turnIndicator.getOpponent();
        }
    }
}
