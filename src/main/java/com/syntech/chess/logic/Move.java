package com.syntech.chess.logic;

import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

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

    @Contract(pure = true)
    public Move(@NotNull Move move) {
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
    }

    public boolean hasPromotion(@NotNull PieceType promotion) {
        return this.promotion.getTextureID().equals(promotion.getTextureID());
    }

    public boolean hasDifferentMoveData(@NotNull Move newMove) {
        return !startPosition.equals(newMove.getStartPosition())
                || !endPosition.equals(newMove.getEndPosition())
                || addRow != newMove.addRow
                || addCol != newMove.addCol
                || (newMove.promotion != PieceType.NONE
                && !hasPromotion(newMove.promotion));
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

    private static int getRow(@NotNull String row) {
        return row.charAt(0) - '1';
    }

    private static int getColumn(@NotNull String col) {
        return col.charAt(0) - 'a';
    }

    @NotNull
    private static Point getPosition(@NotNull String coordinates) {
        return new Point(getRow(coordinates.substring(1, 2)), getColumn(coordinates.substring(0, 1)));
    }

    private static PieceType getType(@NotNull String notation) {
        if (notation.equals(Translation.EN_US.get("log.knight"))) {
            return PieceType.KNIGHT;
        } else if (notation.equals(Translation.EN_US.get("log.pegasus"))) {
            return PieceType.PEGASUS;
        } else if (notation.equals(Translation.EN_US.get("log.bishop"))) {
            return PieceType.BISHOP;
        } else if (notation.equals(Translation.EN_US.get("log.sniper"))) {
            return PieceType.SNIPER;
        } else if (notation.equals(Translation.EN_US.get("log.rook"))) {
            return PieceType.ROOK;
        } else if (notation.equals(Translation.EN_US.get("log.cardinal"))) {
            return PieceType.CARDINAL;
        } else if (notation.equals(Translation.EN_US.get("log.queen"))) {
            return PieceType.QUEEN;
        } else if (notation.equals(Translation.EN_US.get("log.king"))) {
            return PieceType.KING;
        } else if (notation.equals(Translation.EN_US.get("log.amazon"))) {
            return PieceType.AMAZON;
        }
        return PieceType.NONE;
    }

    public static boolean contains(@NotNull ArrayList<Move> moves, int endRow, int endCol) {
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol) {
                return true;
            }
        }
        return false;
    }

    private int amount(@NotNull ArrayList<Move> moves, int endRow, int endCol, int startRow, int startCol) {
        int amount = 1;
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol
                    && (move.getStartRow() != startRow || move.getStartCol() != startCol)
                    && move.piece == this.piece) {
                ++amount;
            }
        }
        return amount;
    }

    private int amountRow(@NotNull ArrayList<Move> moves, int endRow, int endCol, int startRow, int startCol) {
        int amount = 1;
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol
                    && move.getStartRow() == startRow
                    && move.getStartCol() != startCol
                    && move.piece == this.piece) {
                ++amount;
            }
        }
        return amount;
    }

    private int amountCol(@NotNull ArrayList<Move> moves, int endRow, int endCol, int startRow, int startCol) {
        int amount = 1;
        for (Move move : moves) {
            if (move.getEndRow() == endRow && move.getEndCol() == endCol
                    && move.getStartRow() != startRow
                    && move.getStartCol() == startCol
                    && move.piece == this.piece) {
                ++amount;
            }
        }
        return amount;
    }

    public PieceType getPiece() {
        return piece;
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

    public Point getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public void setData(@NotNull Board board) {
        ArrayList<Move> moves;
        if (!board.isFree(getEndRow(), getEndCol())) {
            isCapture = true;
        }
        moves = board.getAllAvailableMoves(board.getSide(getStartRow(), getStartCol()));
        if (amount(moves, getEndRow(), getEndCol(), getStartRow(), getStartCol()) > 1) {
            addCol = true;
            if (amountCol(moves, getEndRow(), getEndCol(), getStartRow(), getStartCol()) > 1) {
                addCol = false;
                addRow = true;
                if (amountRow(moves, getEndRow(), getEndCol(), getStartRow(), getStartCol()) > 1) {
                    addCol = true;
                }
            }
        }
    }

    public static void setPriority(@NotNull ArrayList<Move> moves, int priority) {
        for (Move move : moves) {
            move.setPriority(priority);
        }
    }

    @NotNull
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

    public String toPGN() {
        return toInternalNotation(Translation.EN_US).replace("×", "x");
    }

    @Nullable
    public static Move fromPGN(@NotNull String pgn, Board board) {
        if (pgn.equals("O-O")) {
            ArrayList<Move> moves = board.getAllAvailableMoves(board.getTurnSide());
            for (Move move : moves) {
                if (move.getPiece() == PieceType.KING
                        && move.getStartRow() == move.getEndRow()
                        && move.getStartCol() == move.getEndCol() - 2) {
                    return move;
                }
            }
        }

        if (pgn.equals("O-O-O")) {
            ArrayList<Move> moves = board.getAllAvailableMoves(board.getTurnSide());
            for (Move move : moves) {
                if (move.getPiece() == PieceType.KING
                        && move.getStartRow() == move.getEndRow()
                        && move.getStartCol() == move.getEndCol() + 2) {
                    return move;
                }
            }
        }

        try {
            PieceType piece = PieceType.PAWN;
            PieceType promotion = PieceType.NONE;
            Point startPosition = new Point(-1, -1);
            Point endPosition;
            boolean addRow = false, addCol = false;

            if (getType(pgn.substring(0, 1)) != PieceType.NONE) {
                piece = getType(pgn.substring(0, 1));
                pgn = pgn.substring(1);
            }

            if (pgn.endsWith("#") || pgn.endsWith("+")) {
                pgn = pgn.substring(0, pgn.length() - 1);
            }

            if (getType(pgn.substring(pgn.length() - 1)) != PieceType.NONE) {
                promotion = getType(pgn.substring(pgn.length() - 1));
                pgn = pgn.substring(0, pgn.length() - 2);
            }

            if (pgn.length() > 2) {
                int col = getColumn(pgn);
                if (col >= 0 && col < board.getWidth()) {
                    startPosition.y = col;
                    addCol = true;
                    pgn = pgn.substring(1);
                }

                int row = getRow(pgn);
                if (row >= 0 && row < board.getHeight()) {
                    startPosition.x = row;
                    addRow = true;
                    pgn = pgn.substring(1);
                }

                if (pgn.startsWith("x")) {
                    pgn = pgn.substring(1);
                }
            }

            endPosition = getPosition(pgn);

            ArrayList<Move> moves;
            moves = board.getAllAvailableMoves(board.getTurnSide());

            for (Move move : moves) {
                if (move.getPiece() == piece
                        && (!addRow || move.getStartRow() == startPosition.x)
                        && (!addCol || move.getStartCol() == startPosition.y)
                        && move.getEndPosition().equals(endPosition)
                        && move.hasPromotion(promotion)) {
                    return move;
                }
            }

        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return addRow == move.addRow &&
                addCol == move.addCol &&
                isCapture == move.isCapture &&
                isCheck == move.isCheck &&
                isGameEnd == move.isGameEnd &&
                priority == move.priority &&
                piece == move.piece &&
                startPosition.equals(move.startPosition) &&
                endPosition.equals(move.endPosition) &&
                promotion == move.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, addRow, addCol, startPosition, endPosition, isCapture, promotion, isCheck, isGameEnd, priority);
    }
}
