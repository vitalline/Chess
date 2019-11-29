package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PieceFactory {

    @Contract(" -> new")
    @NotNull
    public static Piece cell() {
        return piece(PieceBaseType.NEUTRAL_PIECE, PieceType.EMPTY, Side.NEUTRAL);
    }

    @Contract(" -> new")
    @NotNull
    public static Piece wall() {
        return piece(PieceBaseType.NEUTRAL_PIECE, PieceType.WALL, Side.NEUTRAL);
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
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int promotionRow, PieceType... types) {
        return piece(baseType, type, side, 0, null, promotionRow, types);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, int xp, Point initialPosition, int promotionRow, PieceType... types) {
        return piece(baseType, type, side, new PromotionInfo(promotionRow, types), xp, initialPosition);
    }

    @Contract("_, _, _, _, _, _ -> new")
    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, PromotionInfo promotionInfo, int xp, Point initialPosition) {
        switch (baseType) {
            case NEUTRAL_PIECE:
                return new NeutralPiece(side, type.getMovementType(side));
            case PIECE:
                return new Piece(side, type.getMovementType(side), promotionInfo);
            case FORCED_PIECE:
                return new ForcedPiece(side, type.getMovementType(side), promotionInfo);
            case MODEST_FORCED_PIECE:
                return new ModestForcedPiece(side, type.getMovementType(side), promotionInfo);
            case SHOOTING_PIECE:
                return new ShootingPiece(side, type.getMovementType(side));
            case LEVELLING_FORCED_PIECE:
                return new LevellingForcedPiece(side, type.getMovementType(side), xp, initialPosition);
            case LEVELLING_UP_FORCED_PIECE:
                return new LevellingUpForcedPiece(side, type.getMovementType(side), xp);
            case LEVELLING_DOWN_FORCED_PIECE:
                return new LevellingDownForcedPiece(side, type.getMovementType(side), promotionInfo, initialPosition);
            case CLONING_FORCED_PIECE:
                return new CloningForcedPiece(side, type.getMovementType(side));
            default:
                return cell();
        }
    }
}
