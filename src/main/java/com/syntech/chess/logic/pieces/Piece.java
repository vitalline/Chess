package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.*;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Piece implements Cloneable {
    protected final Side side;
    protected Point position;
    private MovementType movementType;
    PieceBaseType baseType;

    @Contract(pure = true)
    public Piece(Side side, MovementType movementType) {
        this.side = side;
        this.position = new Point(0, 0);
        this.movementType = movementType;
        this.baseType = PieceBaseType.PIECE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece clone = (Piece) super.clone();
        clone.position = new Point(position);
        clone.movementType = (MovementType) movementType.clone();
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

    public ArrayList<Point> getAvailableMovesWithoutSpecialRules(Board board) {
        return movementType.getAvailableMovesWithoutSpecialRules(position, board);
    }

    public ArrayList<Point> getAvailableThreatsOn(Board board, Side side) {
        return movementType.getAvailableThreatsOn(position, board, side);
    }

    public ArrayList<Point> getAvailableCapturesWithoutSpecialRules(Board board) {
        return getAvailableThreatsOn(board, side.getOpponent());
    }

    public ArrayList<Point> getAvailableMoves(Board board) {
        ArrayList<Point> moves = getAvailableMovesWithoutSpecialRules(board);
        return excludeMovesThatLeaveKingInCheck(board, moves);
    }

    public ArrayList<Point> getAvailableCaptures(Board board) {
        ArrayList<Point> moves = getAvailableCapturesWithoutSpecialRules(board);
        return excludeMovesThatLeaveKingInCheck(board, moves);
    }

    @NotNull ArrayList<Point> excludeMovesThatLeaveKingInCheck(Board board, @NotNull ArrayList<Point> moves) {
        ArrayList<Point> filteredMoves = new ArrayList<>();
        for (Point move : moves) {
            if (!board.getNextTurn(position.x, position.y, move.x, move.y).isInCheck(side)) {
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

    public boolean canBePromoted(Board board) {
        return false;
    }

}
