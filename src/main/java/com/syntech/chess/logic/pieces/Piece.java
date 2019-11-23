package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    protected final Side side;
    protected Point position;
    protected MovementType movementType;

    @Contract(pure = true)
    public Piece(Side side, Point position) {
        this.side = side;
        this.position = position;
    }

    @Contract(pure = true)
    public Piece(Side side) {
        this.side = side;
        this.position = new Point(0, 0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece clone = (Piece) super.clone();
        clone.position = new Point(position);
        return clone;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public Side getSide() {
        return side;
    }

    public PieceType getType() {
        return PieceType.NONE;
    }

    public String getName() {
        return getSide().getName() + getType().getName();
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

    @NotNull
    protected ArrayList<Point> excludeMovesThatLeaveKingInCheck(Board board, @NotNull ArrayList<Point> moves) {
        ArrayList<Point> filteredMoves = new ArrayList<>();
        for (Point move : moves) {
            if (!board.getNextTurn(position.x, position.y, move.x, move.y).isInCheck(side)) {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    public void move(@NotNull Board board, int row, int col) {
        board.placePiece(new EmptyCell(), position.x, position.y);
        board.placePiece(this, row, col);
        board.deselectPiece();
    }

}
