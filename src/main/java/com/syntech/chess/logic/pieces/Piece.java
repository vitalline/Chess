package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovePriorities;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Piece implements Cloneable {
    protected final Side side;
    protected Point position;
    protected MovementType movementType;
    private PromotionInfo promotionInfo;
    PieceBaseType baseType;

    @Contract(pure = true)
    public Piece(Side side, MovementType movementType) {
        this(side, movementType, null);
    }

    @Contract(pure = true)
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

    public PieceBaseType getBaseType() {
        return baseType;
    }

    public String getName() {
        return getSide().getName() + getType().getName();
    }

    public String getLabel() {
        String baseType = getBaseType().getProperName();
        String side = getSide().getProperName();
        String type = getType().getProperName();
        String label = "";
        if (!baseType.equals("")) {
            label += baseType + ' ';
        }
        if (!side.equals("")) {
            label += side + ' ';
        }
        label += type;
        return label;
    }

    public ArrayList<Point> getControlledCells(Board board) {
        return movementType.getControlledCells(position, board);
    }

    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(Board board) {
        return movementType.getAvailableMovesWithoutSpecialRules(position, board);
    }

    public ArrayList<Move> getAvailableThreatsOn(Board board, Side side) {
        return movementType.getAvailableThreatsOn(position, board, side);
    }

    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(Board board) {
        return getAvailableThreatsOn(board, side.getOpponent());
    }

    public ArrayList<Move> getAvailableMoves(Board board) {
        ArrayList<Move> moves = getAvailableMovesWithoutSpecialRules(board);
        moves = excludeMovesThatLeaveKingInCheck(board, moves);
        return MovePriorities.topPriorityMoves(moves);
    }

    public ArrayList<Move> getAvailableCaptures(Board board) {
        ArrayList<Move> moves = getAvailableCapturesWithoutSpecialRules(board);
        moves = excludeMovesThatLeaveKingInCheck(board, moves);
        return MovePriorities.topPriorityMoves(moves);
    }

    @NotNull
    private ArrayList<Move> excludeMovesThatLeaveKingInCheck(Board board, @NotNull ArrayList<Move> moves) {
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            if (!board.getNextTurn(position.x, position.y, move.getRow(), move.getCol()).isInCheck(side)) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
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

    public PieceType[] getPromotionTypes() {
        return promotionInfo != null ? promotionInfo.getPromotionTypes() : new PieceType[0];
    }

    public void promoteTo(@NotNull PieceType type) {
        this.movementType = type.getMovementType(side);
        this.promotionInfo = null;
    }
}
