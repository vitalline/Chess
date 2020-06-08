package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Piece implements Cloneable {
    protected final Side side;
    protected Point position;
    protected MovementType movementType;
    private PromotionInfo promotionInfo;
    protected PieceBaseType baseType;
    private ArrayList<Move> availableMovesWithoutSpecialRules = null;
    private ArrayList<Move> availableCapturesWithoutSpecialRules = null;
    private ArrayList<Move> availableMoves = null;
    private ArrayList<Move> availableCaptures = null;

    public Piece(Side side, MovementType movementType) {
        this(side, movementType, null);
    }

    public Piece(Side side, MovementType movementType, PromotionInfo promotionInfo) {
        this.side = side;
        this.position = new Point(0, 0);
        this.movementType = movementType;
        this.promotionInfo = promotionInfo;
        this.baseType = PieceBaseType.PIECE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece clone = (Piece) super.clone();
        clone.position = new Point(position);
        clone.movementType = (MovementType) movementType.clone();
        if (promotionInfo != null) {
            clone.promotionInfo = (PromotionInfo) promotionInfo.clone();
        }
        return clone;
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }

    public Side getSide() {
        return side;
    }

    public PieceType getType() {
        return movementType.getType();
    }

    private PieceBaseType getBaseType() {
        return baseType;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public String getTextureID() {
        return getSide().getTextureID() + getType().getTextureID();
    }

    public String getLabel(@NotNull Translation translation) {
        return translation.get("label.name",
                getBaseType().getTranslationString(),
                getSide().getTranslationString(),
                getType().getTranslationString()
        ).trim().replaceAll(" {2}", " ");
    }

    public int getEvaluationCost() {
        return getMovementType().getEvaluationCost();
    }

    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Board board) {
        if (availableMovesWithoutSpecialRules == null) {
            availableMovesWithoutSpecialRules = movementType.getAvailableMovesWithoutSpecialRules(position, board);
        }
        return availableMovesWithoutSpecialRules;
    }

    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Board board) {
        if (availableCapturesWithoutSpecialRules == null) {
            availableCapturesWithoutSpecialRules = movementType.getAvailableCapturesWithoutSpecialRules(position, board);
            for (Move move : availableCapturesWithoutSpecialRules) {
                move.setCaptureFlag();
            }
        }
        return availableCapturesWithoutSpecialRules;
    }

    public ArrayList<Move> getAvailableMoves(Board board) {
        if (availableMoves == null) {
            availableMoves = getAvailableMovesWithoutSpecialRules(board);
            availableMoves = addPromotions(availableMoves, board);
            availableMoves = board.excludeMovesThatLeaveKingInCheck(side, availableMoves);
            availableMoves = MovePriorities.topPriorityMoves(availableMoves);
        }
        return availableMoves;
    }

    public ArrayList<Move> getAvailableCaptures(Board board) {
        if (availableCaptures == null) {
            availableCaptures = getAvailableCapturesWithoutSpecialRules(board);
            availableCaptures = addPromotions(availableCaptures, board);
            availableCaptures = board.excludeMovesThatLeaveKingInCheck(side, availableCaptures);
            availableCaptures = MovePriorities.topPriorityMoves(availableCaptures);
        }
        return availableCaptures;
    }

    public void resetMoveCache() {
        availableMovesWithoutSpecialRules = null;
        availableCapturesWithoutSpecialRules = null;
        availableMoves = null;
        availableCaptures = null;
    }

    public void move(@NotNull Board board, int row, int col) {
        board.placePiece(PieceFactory.cell(), position);
        board.placePiece(this, row, col);
        if (movementType instanceof SpecialFirstMoveType) {
            ((SpecialFirstMoveType) movementType).move();
        }
    }

    public boolean canBePromoted() {
        return promotionInfo != null && promotionInfo.canBePromoted(position.x);
    }

    private boolean canBePromotedAfterMove(Move move, Board board) {
        return board.getMoveResultWithoutPromotion(move).hasPromotion();
    }

    private ArrayList<Move> addPromotions(ArrayList<Move> moves, Board board) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (canBePromotedAfterMove(move, board)) {
                for (PieceType type : getPromotionTypes()) {
                    Move newMove = new Move(move);
                    newMove.setPromotion(type);
                    filteredMoves.add(newMove);
                }
            } else {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public PieceType[] getPromotionTypes() {
        return promotionInfo != null ? promotionInfo.getPromotionTypes() : new PieceType[0];
    }

    public void promoteTo(@NotNull PieceType type) {
        this.movementType = type.getMovementType(side);
        this.promotionInfo = null;
    }
}
