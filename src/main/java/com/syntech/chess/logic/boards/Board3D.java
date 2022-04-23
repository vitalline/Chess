package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.chess3d.Knight3DType;
import com.syntech.chess.rules.chess3d.Queen3DType;
import imgui.ImGui;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board3D extends Board {
    private final int boardWidth;

    public Board3D(@NotNull Piece[][] board, Boolean priority, Integer boardWidth, Boolean initialize, @NotNull Boolean update) {
        super(board, priority, initialize, false);
        this.boardWidth = boardWidth;
        if (update) {
            updateMovablePieces();
        }
    }

    public Board3D(@NotNull Piece[][] board, Boolean priority, Integer boardWidth, Integer turn) {
        super(board, priority, turn);
        this.boardWidth = boardWidth;
    }

    @Override
    protected boolean displayBoard(float size) {

        boolean inputReceived = false;

        displayLabelRow(size, 0);
        for (int row = getHeight() - 1; row >= 0; row--) {
            displayLabel(Move.getRow(row), size / 2, size, 0);
            for (int col = 0; col < getWidth(); col++) {
                ImGui.sameLine();
                if (displayCell(size, row, col)) {
                    inputReceived = true;
                }
                if (getBoard(col + 1) > getBoard(col)) {
                    ImGui.sameLine();
                    displayLabel(Move.getRow(row), size / 2, size, getBoard(col));
                }
            }
        }
        displayLabelRow(size, 1);

        return inputReceived;
    }

    @Override
    protected void displayLabelRow(float size, int id) {
        displayLabel("", size / 2, size / 2, id * (getBoardAmount() + 1));
        for (int col = 0; col < getWidth(); col++) {
            ImGui.sameLine();
            displayLabel("" + Move.getColumn(col), size, size / 2, id);
            if (getBoard(col + 1) > getBoard(col)) {
                ImGui.sameLine();
                displayLabel("", size / 2, size / 2, id * (getBoardAmount() + 1) + getBoard(col));
            }
        }
    }

    @Override
    public int getPromotionCoordinate(@NotNull Point position) {
        return getBoard(position.y);
    }

    @Override
    public Board getMoveResult(Move move) {
        Board3D moveResult = new Board3D(board, priority, boardWidth, turn);
        if (move != null) {
            moveResult.move(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol(), false);
            moveResult.promoteIfNeeded(move);
        }
        return moveResult;
    }

    @Override
    protected boolean cellColorIsWhite(int row, int col) {
        return (getWidth() - col + row + getBoardAmount() - getBoard(col)) % 2 == 0;
    }

    public int getBoardAmount() {
        return getWidth() / getBoardWidth();
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getCol(int internalCol) {
        return internalCol % getBoardWidth();
    }

    public int getBoard(int internalCol) {
        return internalCol / getBoardWidth();
    }

    public int getInternalCol(int col, int board) {
        if (board >= 0 && board < getBoardAmount() && col >= 0 && col < getBoardWidth())
            return board * getBoardWidth() + col;
        return -1;
    }

    protected ArrayList<MovementType> checkMoves(Side side) {
        return new ArrayList<>(Arrays.asList(new Queen3DType(side), new Knight3DType(side)));
    }
}
