package com.syntech.chess.ai;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AI {
    private static final int WIN_SCORE = 1000;

    private static int evaluateBoard(@NotNull Board board) {
        Side side = board.getTurnSide();
        int pieceScore = 0, moveScore = 0;
        ArrayList<Piece> pieces = board.getAllPieces(side);
        for (Piece piece : pieces) {
            pieceScore += piece.getEvaluationCost();
        }

        ArrayList<Move> moves = board.getAllAvailableMoves(side);
        moves.addAll(board.getAllAvailableCaptures(side));
        for (Move ignored : moves) {
            moveScore += 1;
        }
        if (moveScore == 0) {
            if (board.isInCheck(side)) {
                return (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
            }
            return 0;
        }
        int score = pieceScore * 2 + moveScore;
        return (side == Side.WHITE) ? score : -score;
    }

    public static int getBestScoreAndSaveBestMove(@NotNull Board board, int depth) throws CloneNotSupportedException {
        return getBestScoreAndSaveBestMove(board, depth, -WIN_SCORE, WIN_SCORE);
    }

    public static int getBestScoreAndSaveBestMove(@NotNull Board board, int depth,
                                                  int alpha, int beta) throws CloneNotSupportedException {
        Side side = board.getTurnSide();
        ArrayList<Move> moves = board.getAllAvailableMoves(side);
        moves.addAll(board.getAllAvailableCaptures(side));
        int bestMoveScore = (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
        Move bestMove = null;
        for (Move move : moves) {
            Board copy = (Board) board.clone();
            copy.updateMove(move);
            copy.redo();
            int newMoveScore;
            if (depth > 1) {
                newMoveScore = getBestScoreAndSaveBestMove(copy, depth - 1, alpha, beta);
            } else {
                newMoveScore = evaluateBoard(copy);
            }
            if ((side == Side.WHITE) ? bestMoveScore < newMoveScore : bestMoveScore > newMoveScore) {
                bestMoveScore = newMoveScore;
                bestMove = move;
            }
            if (side == Side.WHITE) {
                alpha = Math.max(alpha, bestMoveScore);
            } else {
                beta = Math.min(beta, bestMoveScore);
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (bestMove != null) {
            bestMove.setData(board);
            board.updateMove(bestMove);
        }
        return bestMoveScore;
    }
}
