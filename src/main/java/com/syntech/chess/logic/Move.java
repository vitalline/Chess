package com.syntech.chess.logic;

import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Move {
    private PieceType piece;
    private boolean addRow = false;
    private boolean addCol = false;
    private Point startPosition;
    private Point endPosition;
    private boolean isCapture = false;
    private PieceType promotion = PieceType.NONE;
    private boolean isCheck = false;
    private boolean isGameEnd = false;
    private int priority = 0;
    private int power = 0;

    public Move(PieceType piece, int startRow, int startCol, int endRow, int endCol) {
        this(piece, new Point(startRow, startCol), endRow, endCol);
    }

    public Move(PieceType piece, Point startPosition, int endRow, int endCol) {
        this(piece, startPosition, new Point(endRow, endCol));
    }

    public Move(PieceType piece, Point startPosition, Point endPosition) {
        this.piece = piece;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Move(Move move) {
        this.piece = move.piece;
        this.addRow = move.addRow;
        this.addCol = move.addCol;
        this.startPosition = move.startPosition;
        this.endPosition = move.endPosition;
        this.isCapture = move.isCapture;
        this.promotion = move.promotion;
        this.isCheck = move.isCheck;
        this.isGameEnd = move.isGameEnd;
        this.priority = move.priority;
        this.power = move.power;
    }

    // TODO: finish PGN-to-move conversion
    /*
    public Move(String pgn, Board board) {
        if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_knight"))) {
            piece = PieceType.KNIGHT;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_pegasus"))) {
            piece = PieceType.PEGASUS;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_bishop"))) {
            piece = PieceType.BISHOP;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_sniper"))) {
            piece = PieceType.SNIPER;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_rook"))) {
            piece = PieceType.ROOK;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_queen"))) {
            piece = PieceType.QUEEN;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_king"))) {
            piece = PieceType.KNIGHT;
        } else if (pgn.substring(0, 1).equals(Translation.EN_US.get("log_amazon"))) {
            piece = PieceType.AMAZON;
        } else {
            piece = PieceType.PAWN;
        }
        pgn = pgn.substring(1);
    }
     */

    public boolean hasDifferentMoveData(Move newMove) {
        return !startPosition.equals(newMove.getStartPosition())
                || !endPosition.equals(newMove.getEndPosition());
    }

    @NotNull
    public static String getRow(int row) {
        return String.valueOf(row + 1);
    }

    @NotNull
    public static String getColumn(int col) {
        return String.valueOf((char) (col + 'a'));
    }

    @NotNull
    public static String getCoordinates(@NotNull Point position) {
        return getColumn(position.y) + getRow(position.x);
    }

    public Point getEndPosition() {
        return endPosition;
    }

    public static boolean contains(@NotNull ArrayList<Move> moves, int endRow, int endCol) {
        return containsWithPowerAtLeast(moves, endRow, endCol, 0);
    }

    public static boolean containsWithPowerAtLeast(@NotNull ArrayList<Move> moves, int endRow, int endCol, int power) {
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol && move.getPower() >= power) {
                return true;
            }
        }
        return false;
    }

    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }

    private static int amount(@NotNull ArrayList<Move> moves, PieceType piece, int endRow, int endCol) {
        return amount(moves, piece, endRow, endCol, 0, 0, false, false);
    }

    private static int amountRow(@NotNull ArrayList<Move> moves, PieceType piece, int endRow, int endCol, int startRow) {
        return amount(moves, piece, endRow, endCol, startRow, 0, true, false);
    }

    public int getPriority() {
        return priority;
    }

    public int getPower() {
        return power;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPower(int power) {
        this.power = power;
    }

    private static int amountCol(@NotNull ArrayList<Move> moves, PieceType piece, int endRow, int endCol, int startCol) {
        return amount(moves, piece, endRow, endCol, 0, startCol, false, true);
    }

    private static int amount(@NotNull ArrayList<Move> moves, PieceType piece, int endRow, int endCol, int startRow, int startCol, boolean checkRow, boolean checkColumn) {
        int amount = 0;
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol
                    && (move.getStartRow() == startRow || !checkRow)
                    && (move.getStartCol() == startCol || !checkColumn)
                    && move.piece == piece) {
                ++amount;
            }
        }
        return amount;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public int getStartRow() {
        return startPosition.x;
    }

    public int getStartCol() {
        return startPosition.y;
    }

    public int getEndRow() {
        return endPosition.x;
    }

    public void setEndRow(int row) {
        endPosition.x = row;
    }

    public int getEndCol() {
        return endPosition.y;
    }

    public void setEndCol(int col) {
        endPosition.y = col;
    }

    public PieceType getPromotion() {
        return promotion;
    }

    public void setPromotion(PieceType promotion) {
        this.promotion = promotion;
    }

    public void setCaptureFlag() {
        this.isCapture = true;
    }

    public void setCheckFlag() {
        this.isCheck = true;
    }

    public void setGameEndFlag() {
        this.isGameEnd = true;
    }

    public void setData(Board board) {
        ArrayList<Move> moves;
        if (!board.isFree(getEndRow(), getEndCol())) {
            isCapture = true;
        }
        if (isCapture) {
            moves = board.getAllAvailableCaptures(board.getSide(getStartRow(), getStartCol()));
        } else {
            moves = board.getAllAvailableMoves(board.getSide(getStartRow(), getStartCol()));
        }
        if (amount(moves, piece, getEndRow(), getEndCol()) > 1) {
            addCol = true;
            if (amountCol(moves, piece, getEndRow(), getEndCol(), getStartCol()) > 1) {
                addCol = false;
                addRow = true;
            }
            if (amountRow(moves, piece, getEndRow(), getEndCol(), getStartRow()) > 1) {
                addCol = true;
            }
        }
    }

    public static void setPriority(ArrayList<Move> moves, int priority) {
        for (Move move : moves) {
            move.setPriority(priority);
        }
    }

    public static void setPower(ArrayList<Move> moves, int power) {
        for (Move move : moves) {
            move.setPower(power);
        }
    }

    private String toInternalNotation(Translation translation) {
        if (isCastling()) {
            if (getEndCol() > getStartCol()) {
                return "O-O";
            } else {
                return "O-O-O";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(piece.getShortNameTag(translation));
        if (addCol || (sb.toString().equals("") && isCapture)) {
            sb.append(getColumn(getStartCol()));
        }
        if (addRow) {
            sb.append(getRow(getStartRow()));
        }
        if (isCapture) {
            sb.append("×");
        }
        sb.append(getCoordinates(endPosition));
        if (promotion != PieceType.NONE) {
            sb.append("=");
            sb.append(promotion.getShortNameTag(translation));
        }
        if (isCheck) {
            if (isGameEnd) {
                sb.append("#");
            } else {
                sb.append("+");
            }
        }
        return sb.toString();
    }

    private boolean isCastling() {
        return piece == PieceType.KING && Math.abs(getStartCol() - getEndCol()) == 2 && getStartRow() == getEndRow();
    }

    public String toNotation(Translation translation) {
        return toInternalNotation(translation).replace("=", "");
    }

    public String toPGN(Translation translation) {
        return toInternalNotation(translation).replace("×", "x");
    }
}
