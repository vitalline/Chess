package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.*;
import com.syntech.chess.rules.ForcedXPRules;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PieceFactory {

    @Contract(" -> new")
    @NotNull
    public static Piece cell() {
        return piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, Side.NEUTRAL);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side) {
        return piece(baseType, type, side, 0, null, 0);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int xp, Point initialPosition) {
        return piece(baseType, type, side, xp, initialPosition, 0);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int promotionRow, Piece... pieces) {
        return piece(baseType, type, side, 0, null, promotionRow, pieces);
    }

    @Contract("_, _, _, _, _, _, _ -> new")
    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int xp, Point initialPosition, int promotionRow, Piece... pieces) {
        switch (baseType) {
            case NEUTRAL_PIECE:
                return new NeutralPiece(side, type.getMovementType(side));
            case PIECE:
                return new Piece(side, type.getMovementType(side));
            case FORCED_PIECE:
                return new ForcedPiece(side, type.getMovementType(side));
            case PROMOTABLE_PIECE:
                return new PromotablePiece(side, type.getMovementType(side), promotionRow, pieces);
            case PROMOTABLE_FORCED_PIECE:
                return new PromotableForcedPiece(side, type.getMovementType(side), promotionRow, pieces);
            case FA_FORCED_PIECE:
                return new FAForcedPiece(side, type.getMovementType(side), xp, ForcedXPRules.getMaxXP(type), initialPosition);
            default:
                return cell();
        }
    }
}
