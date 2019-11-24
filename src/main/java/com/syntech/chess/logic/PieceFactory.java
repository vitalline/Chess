package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.EmptyCell;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.chess.*;
import com.syntech.chess.logic.pieces.fa_forced.*;
import com.syntech.chess.logic.pieces.forced.*;
import org.jetbrains.annotations.NotNull;

public class PieceFactory {
    protected static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, Piece... pieces) {
        return piece(baseType, type, side, 0, pieces);
    }

    protected static Piece cell() {
        return piece(PieceBaseType.NONE, PieceType.NONE, Side.NONE, 0);
    }

    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int promotionRow, Piece... pieces) {
        switch (baseType) {
            case NONE:
                switch (type) {
                    case NONE:
                        return new EmptyCell();
                }
            case PIECE:
                switch (type) {
                    case PAWN:
                        return new Pawn(side);
                    case DOUBLE_PAWN:
                        return new DoublePawn(side);
                    case KNIGHT:
                        return new Knight(side);
                    case BISHOP:
                        return new Bishop(side);
                    case ROOK:
                        return new Rook(side);
                    case QUEEN:
                        return new Queen(side);
                    case KING:
                        return new King(side);
                    default:
                        return new EmptyCell();
                }
            case FORCED_PIECE:
                switch (type) {
                    case PAWN:
                        return new ForcedPawn(side);
                    case DOUBLE_PAWN:
                        return new ForcedDoublePawn(side);
                    case KNIGHT:
                        return new ForcedKnight(side);
                    case BISHOP:
                        return new ForcedBishop(side);
                    case ROOK:
                        return new ForcedRook(side);
                    case QUEEN:
                        return new ForcedQueen(side);
                    case KING:
                        return new ForcedKing(side);
                    default:
                        return new EmptyCell();
                }
            case PROMOTABLE_PIECE:
                switch (type) {
                    case PAWN:
                        return new PromotablePawn(side, promotionRow, pieces);
                    case DOUBLE_PAWN:
                        return new PromotableDoublePawn(side, promotionRow, pieces);
                    default:
                        return new EmptyCell();
                }
            case PROMOTABLE_FORCED_PIECE:
                switch (type) {
                    case PAWN:
                        return new PromotableForcedPawn(side, promotionRow, pieces);
                    case DOUBLE_PAWN:
                        return new PromotableForcedDoublePawn(side, promotionRow, pieces);
                    default:
                        return new EmptyCell();
                }
            case FA_FORCED_PIECE:
                switch (type) {
                    case PAWN:
                        return new FAForcedPawn(side);
                    case KNIGHT:
                        return new FAForcedKnight(side);
                    case BISHOP:
                        return new FAForcedBishop(side);
                    case ROOK:
                        return new FAForcedRook(side);
                    case QUEEN:
                        return new FAForcedQueen(side);
                    case KING:
                        return new FAForcedKing(side);
                    case AMAZON:
                        return new FAForcedAmazon(side);
                    default:
                        return new EmptyCell();
                }
            default:
                return new EmptyCell();
        }
    }
}
