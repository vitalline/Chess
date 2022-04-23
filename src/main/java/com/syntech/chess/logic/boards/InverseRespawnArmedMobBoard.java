package com.syntech.chess.logic.boards;

import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.pieces.LevellingForcedPiece;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import static com.syntech.chess.rules.StartingPositions.mmoRPGForcedChessWithMobs;

public class InverseRespawnArmedMobBoard extends BaseArmedMobBoard {

    private static final Piece[][] baseBoard = mmoRPGForcedChessWithMobs(LevellingData.UP_HP, true);

    public InverseRespawnArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Boolean initialize, Boolean update) {
        super(board, priority, initialize, update);
    }

    public InverseRespawnArmedMobBoard(@NotNull Piece[][] board, Boolean priority, Integer turn) {
        super(board, priority, turn);
    }

    protected double getPawnDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 20;
            case 7 -> 23;
            case 6 -> 27;
            case 5 -> 32;
            case 4 -> 40;
            case 3 -> 53;
            case 2 -> 80;
            case 1 -> 100;
            default -> 0;
        };
    }

    protected double getKingDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 17;
            case 7 -> 19;
            case 6 -> 23;
            case 5 -> 27;
            case 4 -> 34;
            case 3 -> 45;
            case 2 -> 68;
            case 1 -> 100;
            default -> 0;
        };
    }

    protected double getBishopDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 14;
            case 7 -> 16;
            case 6 -> 19;
            case 5 -> 22;
            case 4 -> 28;
            case 3 -> 37;
            case 2 -> 56;
            case 1 -> 100;
            default -> 0;
        };
    }

    protected double getRookDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 11;
            case 7 -> 13;
            case 6 -> 15;
            case 5 -> 18;
            case 4 -> 22;
            case 3 -> 29;
            case 2 -> 44;
            case 1 -> 88;
            default -> 0;
        };
    }

    protected double getQueenDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 8;
            case 7 -> 9;
            case 6 -> 11;
            case 5 -> 13;
            case 4 -> 16;
            case 3 -> 21;
            case 2 -> 32;
            case 1 -> 64;
            default -> 0;
        };
    }

    protected double getAmazonDamage() {
        return switch (neutralPieces.size()) {
            case 8 -> 5;
            case 7 -> 6;
            case 6 -> 7;
            case 5 -> 8;
            case 4 -> 10;
            case 3 -> 13;
            case 2 -> 20;
            case 1 -> 40;
            default -> 0;
        };
    }

    protected double getDamage(@NotNull LevellingForcedPiece piece) {
        return switch (piece.getType()) {
            case PAWN -> getPawnDamage();
            case KING -> getKingDamage();
            case BISHOP -> getBishopDamage();
            case ROOK -> getRookDamage();
            case QUEEN -> getQueenDamage();
            case AMAZON -> getAmazonDamage();
            default -> 0;
        };
    }

    @Override
    protected void doAIAction() {
        super.doAIAction();
        updatePieces();
        try {
            if (whitePieces.size() == 1 && whitePieces.get(0).equals(whiteKing)) {
                for (int row = 0; row <= 1; row++) {
                    for (int col = 0; col <= getWidth(); col++) {
                        if (isFree(row, col) && baseBoard[row][col].getType() != PieceType.KING) {
                            placePiece((Piece) baseBoard[row][col].clone(), row, col);
                        }
                    }
                }
            }
            if (blackPieces.size() == 1 && blackPieces.get(0).equals(blackKing)) {
                for (int row = getHeight() - 1; row >= getHeight() - 2; row--) {
                    for (int col = 0; col <= getWidth(); col++) {
                        if (isFree(row, col) && baseBoard[row][col].getType() != PieceType.KING) {
                            placePiece((Piece) baseBoard[row][col].clone(), row, col);
                        }
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
