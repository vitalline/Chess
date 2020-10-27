package com.syntech.chess.ai;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

public class AI extends Thread {
    private static final int WIN_SCORE = Integer.MAX_VALUE;
    private final int depth;
    private Board board;
    private Move bestMove;
    private Move[] currentMoves;
    private ArrayList<Hashtable<Move, Integer>> deepMoveScores;
    private ArrayList<Hashtable<Move, Integer>> deepMoveCounts;
    private int currentDepth = 0;
    private static final int MAX_DISPLAYED_DEPTH = 3;
    private boolean shouldRun = false;

    public AI(int depth, @NotNull Board board) {
        this.depth = depth;
        try {
            this.board = (Board) board.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        currentMoves = new Move[depth];
        deepMoveScores = new ArrayList<>();
        deepMoveCounts = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            deepMoveScores.add(new Hashtable<>());
            deepMoveCounts.add(new Hashtable<>());
        }
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
        int score = (pieceScore - opponentPieceScore) * 10 + moveScore - opponentMoveScore;
        return (side == Side.WHITE) ? score : -score;
    }

    public synchronized String thoughts(Translation translation) {
        if (currentDepth == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_DISPLAYED_DEPTH; i++) {
            sb.append(currentMoves[i].toNotation(translation));
            if (i == currentDepth - 1) break;
            sb.append(' ');
            if (i == MAX_DISPLAYED_DEPTH - 1 && i < currentDepth - 1) sb.append('…');
        }
        return sb.toString();
    }

    public synchronized Move bestMove() {
        return bestMove;
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
        if (moves.size() == 0) {
            if (board.isInCheck(side)) {
                return (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
            }
            return 0;
        }
        //Make a random move if stopped abruptly and no actually good moves were found (yet).
        synchronized (this) {
            Collections.shuffle(moves);
            if (currentDepth == 0) {
                if (moves.size() > 0) {
                    this.bestMove = moves.get(0);
                    this.bestMove.setData(board);
                }
                if (moves.size() == 1) {
                    shouldRun = false;
                }
            }
        }
        int bestScore = (side == Side.WHITE) ? -WIN_SCORE : WIN_SCORE;
        if (maxDepth > 1 && currentDepth == 0) {
            Board copy = board;
            try {
                copy = (Board) board.clone();
                copy.recreateMoveLog();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            run(copy, maxDepth - 1, 0, alpha, beta);
        }
        Hashtable<Move, Integer> finalMoveScores = deepMoveScores.get(currentDepth);
        Hashtable<Move, Integer> finalMoveCounts = deepMoveCounts.get(currentDepth);
        moves.sort(Comparator.comparingDouble(move ->
                ((double) finalMoveScores.getOrDefault(move, 0))
                        / finalMoveCounts.getOrDefault(move, 1)
                        * ((side == Side.WHITE) ? -1 : 1)));
        if (currentDepth == 0) {
            for (int i = 1 - maxDepth % 2; i < depth; i += 2) {
                deepMoveScores.set(i, new Hashtable<>());
                deepMoveCounts.set(i, new Hashtable<>());
            }
        }
        Move bestMove = this.bestMove;
        for (Move move : moves) {
            Board copy = board;
            try {
                copy = (Board) board.clone();
                copy.recreateMoveLog();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            copy.updateMove(move);
            copy.simulatedRedo();
            synchronized (this) {
                currentMoves[currentDepth] = move;
            }
            int score;
            if (maxDepth > currentDepth + 1) {
                score = run(copy, maxDepth, currentDepth + 1, alpha, beta);
            } else {
                score = evaluateBoard(copy);
            }
            Hashtable<Move, Integer> moveScores = deepMoveScores.get(currentDepth);
            Hashtable<Move, Integer> moveCounts = deepMoveCounts.get(currentDepth);
            Integer oldScore = moveScores.getOrDefault(move, 0);
            Integer oldCount = moveCounts.getOrDefault(move, 0);
            moveScores.put(move, score + oldScore);
            moveCounts.put(move, ++oldCount);
            if ((side == Side.WHITE) ? bestScore < score : bestScore > score) {
                bestScore = score;
                bestMove = move;
            }
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
            synchronized (this) {
                if (shouldRun) {
                    this.currentDepth = currentDepth;
                    if (currentDepth == 0) {
                        this.bestMove = bestMove;
                        this.bestMove.setData(board);
                        if (Math.abs(bestScore) == WIN_SCORE) {
                            shouldRun = false;
                        }
                    }
                }
            }
        }
        return bestScore;
    }
}
