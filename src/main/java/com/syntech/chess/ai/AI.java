package com.syntech.chess.ai;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class AI extends Thread {
    private static final int WIN_SCORE = Integer.MAX_VALUE;
    private final int depth;
    private Board board;
    private Move[] bestMoves;
    private int[] bestScores;
    private Move[] currentMoves;
    private int[] currentScores;
    private int currentDepth = -1;
    private boolean shouldRun = false;

    public AI(int depth, @NotNull Board board) throws CloneNotSupportedException {
        this.depth = depth;
        this.board = (Board) board.clone();
        bestMoves = new Move[depth];
        bestScores = new int[depth];
        currentMoves = new Move[depth];
        currentScores = new int[depth];
    }

    private static int evaluateBoard(@NotNull Board board) {
        Side side = board.getTurnSide();
        int pieceScore = 0, opponentPieceScore = 0;
        int moveScore = 0, opponentMoveScore = 0;
        ArrayList<Piece> pieces = board.getAllPieces(side);
        for (Piece piece : pieces) {
            pieceScore += piece.getEvaluationCost();
        }
        pieces = board.getAllPieces(side.getOpponent());
        for (Piece piece : pieces) {
            opponentPieceScore += piece.getEvaluationCost();
        }

        ArrayList<Move> moves = board.getAllAvailableMoves(side);
        for (Move ignored : moves) {
            moveScore += 1;
        }
        if (moveScore == 0) {
            if (board.isInCheck(side)) {
                return (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
            }
            return 0;
        }
        moves = board.getAllAvailableMoves(side.getOpponent());
        for (Move ignored : moves) {
            opponentMoveScore += 1;
        }
        int score = pieceScore * 8 - opponentPieceScore * 4 + moveScore * 2 - opponentMoveScore;
        return (side == Side.WHITE) ? score : -score;
    }

    public synchronized String thoughts(Translation translation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentDepth + 1; i++) {
            sb.append(bestMoves[i].toNotation(translation));
            if (i != currentDepth) sb.append(' ');
        }
        return sb.toString();
    }

    public synchronized Move bestMove() {
        return bestMoves[0];
    }

    @Override
    public void run() {
        shouldRun = true;
        run(board, depth, 0, -WIN_SCORE, WIN_SCORE);
        shouldRun = false;
    }

    public synchronized void stopEarly() {
        shouldRun = false;
    }

    private int run(@NotNull Board board, int maxDepth, int currentDepth, int alpha, int beta) {
        Side side = board.getTurnSide();
        if (!shouldRun) return (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
        ArrayList<Move> moves = board.getAllAvailableMoves(side);
        Collections.shuffle(moves);
        int bestScore = (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
        Move bestMove = null;
        for (Move move : moves) {
            Board copy = null;
            try {
                copy = (Board) board.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            assert copy != null; //probably not the smartest option here tbh
            copy.updateMove(move);
            copy.redo();
            currentMoves[currentDepth] = move;
            int score;
            if (maxDepth > currentDepth + 1) {
                score = run(copy, maxDepth, currentDepth + 1, alpha, beta);
            } else {
                score = evaluateBoard(copy);
            }
            if ((side == Side.WHITE) ? bestScore < score : bestScore > score) {
                bestScore = score;
                bestMove = move;
            }
            currentScores[currentDepth] = score;
            if (side == Side.WHITE) {
                alpha = Math.max(alpha, bestScore);
            } else {
                beta = Math.min(beta, bestScore);
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (bestMove != null) {
            bestMove.setData(board);
            if ((side == Side.WHITE) ? bestScores[currentDepth] <= bestScore : bestScores[currentDepth] >= bestScore) {
                synchronized (this) {
                    if (shouldRun) {
                        System.arraycopy(currentScores, 0, bestScores, 0, currentDepth + 1);
                        System.arraycopy(currentMoves, 0, bestMoves, 0, currentDepth + 1);
                        this.currentDepth = currentDepth;
                    }
                }
            }
        }
        return bestScore;
    }
}
