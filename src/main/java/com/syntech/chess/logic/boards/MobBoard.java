package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

public class MobBoard extends AIBoard {
    private ArrayList<Point> neutralPieces;
    private Piece lastMovedPiece;

    public MobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public MobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    public void updatePieces() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        neutralPieces = new ArrayList<>();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (!isFree(row, col)) {
                    if (getSide(row, col) == Side.WHITE) whitePieces.add(new Point(row, col));
                    if (getSide(row, col) == Side.BLACK) blackPieces.add(new Point(row, col));
                    if (getSide(row, col) == Side.NEUTRAL) neutralPieces.add(new Point(row, col));
                }
            }
        }
        allAvailableWhiteCapturesWSR = null;
        allAvailableBlackCapturesWSR = null;
        allAvailableWhiteMoves = null;
        allAvailableBlackMoves = null;
    }

    private @Nullable Move getNeutralMove(@NotNull Point p) {
        for (Move neutralMove : getPiece(p.x, p.y).getAvailableMovesWithoutSpecialRules(this)) {
            boolean isSafe = true;
            for (Move move : lastMovedPiece.getAvailableCapturesWithoutSpecialRules(this)) {
                if (neutralMove.getEndPosition().equals(move.getEndPosition())) {
                    isSafe = false;
                    break;
                }
            }
            for (Move move : lastMovedPiece.getAvailableMovesWithoutSpecialRules(this)) {
                if (neutralMove.getEndPosition().equals(move.getEndPosition())) {
                    isSafe = false;
                    break;
                }
            }
            if (isSafe) {
                return neutralMove;
            }
        }
        return null;
    }

    @Override
    protected void doAIAction() {
        if (neutralPieces.size() == 0) return;
        lastMovedPiece = getSelectedPiece();
        ArrayList<Point> attackedPieces = new ArrayList<>();
        Point lastMovedPiecePosition = lastMovedPiece.getPosition();
        Point lastMovedPieceInitialPosition = ((LevellingForcedPiece) lastMovedPiece).getInitialPosition();
        Move finalMove = null;
        for (Move move : lastMovedPiece.getAvailableCapturesWithoutSpecialRules(this)) {
            if (getSide(move.getEndRow(), move.getEndCol()) == Side.NEUTRAL) {
                attackedPieces.add(new Point(move.getEndRow(), move.getEndCol()));
            }
        }
        if (attackedPieces.size() == 1) {
            Point attackedPiece = attackedPieces.get(0);
            finalMove = getNeutralMove(attackedPiece);
            if (finalMove == null) {
                ArrayList<Move> moves = getPiece(attackedPiece.x, attackedPiece.y).getAvailableMovesWithoutSpecialRules(this);
                if (moves.size() > 0) finalMove = moves.get(0);
            }
        }
        if (attackedPieces.size() != 1 || finalMove == null){
            ArrayList<Move> savingMoves = new ArrayList<>();
            Hashtable<Move, Double> savingMoveScores = new Hashtable<>();
            ArrayList<Move> allMoves = new ArrayList<>();
            Hashtable<Move, Double> allMoveScores = new Hashtable<>();
            for (Point p: neutralPieces) {
                ArrayList<Move> moves = getPiece(p.x, p.y).getAvailableMovesWithoutSpecialRules(this);
                if (moves.size() == 0) continue;
                Move move = moves.get(0);
                double score = (
                        (((attackedPieces.contains(p) ? 1 : 0) * getWidth()
                            + (p.y > getWidth() / 2 ? (getWidth() - p.y) * 2 - 1 : p.y * 2)) * getWidth() * getHeight()
                            - p.distance(lastMovedPiecePosition)) * getWidth() * getHeight()
                            - p.distance(lastMovedPieceInitialPosition)
                );
                Move savingMove = getNeutralMove(p);
                if (savingMove != null) {
                    savingMoves.add(savingMove);
                    savingMoveScores.put(savingMove, score);
                    allMoves.add(savingMove);
                    allMoveScores.put(savingMove, score);
                } else {
                    allMoves.add(move);
                    allMoveScores.put(move, score);
                }
            }
            if (savingMoves.size() > 0) {
                savingMoves.sort(Comparator.comparingDouble(move -> -savingMoveScores.getOrDefault(move, 0D)));
                finalMove = savingMoves.get(0);
            } else if (allMoves.size() > 0)  {
                allMoves.sort(Comparator.comparingDouble(move -> -allMoveScores.getOrDefault(move, 0D)));
                finalMove = allMoves.get(0);
            }
        }
        if (finalMove != null) {
            getPiece(finalMove.getStartRow(), finalMove.getStartCol()).move(this, finalMove.getEndRow(), finalMove.getEndCol());
        }
    }
}
