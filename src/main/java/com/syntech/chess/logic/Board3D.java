package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.Piece;
import imgui.ImGui;
import org.jetbrains.annotations.NotNull;

public class Board3D extends Board {
    private int boardWidth;

    public Board3D(@NotNull Piece[][] board, int boardWidth, boolean initialize, boolean update) {
        super(board, initialize, update);
        this.boardWidth = boardWidth;
    }

    public Board3D(@NotNull Piece[][] board, int boardWidth, int turn) {
        super(board, turn);
        this.boardWidth = boardWidth;
    }

    protected boolean displayBoard(float size) {

        boolean inputReceived = false;

        displayLabelRow(size, 0);
        for (int row = getHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < getWidth(); col++) {
                if (getBoard(col) > getBoard(col - 1)) {
                    displayLabel(Move.getRow(row), size / 2, size, getBoard(col));
                    ImGui.sameLine();
                }
                if (displayCell(size, row, col)) {
                    inputReceived = true;
                }
                ImGui.sameLine();
            }
            displayLabel(Move.getRow(row), size / 2, size, getBoardAmount());
        }
        displayLabelRow(size, 1);

        return inputReceived;
    }

    protected void displayLabelRow(float size, int id) {
        for (int col = 0; col < getWidth(); col++) {
            if (getBoard(col) > getBoard(col - 1)) {
                displayLabel("", size / 2, size / 2, id * (getBoardAmount() + 1) + getBoard(col));
                ImGui.sameLine();
            }
            displayLabel("" + Move.getColumn(col), size, size / 2, id);
            ImGui.sameLine();
        }
        displayLabel("", size / 2, size / 2, id * (getBoardAmount() + 1) + getBoardAmount());
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
        return board * getBoardWidth() + col;
    }
}
