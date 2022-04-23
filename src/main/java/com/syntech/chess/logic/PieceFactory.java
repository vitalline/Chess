package com.syntech.chess.logic;

import com.syntech.chess.logic.pieces.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PieceFactory {

    @NotNull
    public static Piece none() {
        return piece(PieceBaseType.PIECE, PieceType.NONE, Side.NONE);
    }

    @NotNull
    public static Piece cell() {
        return piece(PieceBaseType.PIECE, PieceType.EMPTY, Side.NONE);
    }

    @NotNull
    public static Piece wall() {
        return piece(PieceBaseType.PIECE, PieceType.WALL, Side.NONE);
    }

    @NotNull
    public static Piece mob(boolean armed) {
        return piece(PieceBaseType.LEVELLING_FORCED_PIECE, armed ? PieceType.ARMED_MOB : PieceType.MOB, Side.NEUTRAL, LevellingData.UP);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side) {
        return piece(baseType, type, side, LevellingData.NONE);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData) {
        return piece(baseType, type, side, levellingData, 0);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side,
                              LevellingData levellingData, Point initialPosition) {
        return piece(baseType, type, side, levellingData, 0, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side,
                              LevellingData levellingData, int xp) {
        return piece(baseType, type, side, levellingData, xp, null);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData,
                              int xp, Point initialPosition) {
        return piece(baseType, type, side, levellingData, xp, 0, 0, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData,
                              int xp, int resistanceXP, int resistanceLevel, Point initialPosition) {
        return piece(baseType, type, side, levellingData, xp, resistanceXP, resistanceLevel, 0, 0, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData,
                              int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel) {
        return piece(baseType, type, side, levellingData, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, null);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData,
                              int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel, Point initialPosition) {
        return piece(baseType, type, side, null, levellingData, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, 100, 100, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, LevellingData levellingData,
                              int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel, double hp, Point initialPosition) {
        return piece(baseType, type, side, null, levellingData, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, hp, hp, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side, PromotionInfo promotionInfo) {
        return piece(baseType, type, side, promotionInfo, LevellingData.NONE);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side,
                              PromotionInfo promotionInfo, LevellingData levellingData) {
        return piece(baseType, type, side, promotionInfo, levellingData, null);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, PieceType type, Side side,
                              PromotionInfo promotionInfo, LevellingData levellingData, Point initialPosition) {
        return piece(baseType, type, side, promotionInfo, levellingData, 0, 0, 0, 0, 0, 100, 100, initialPosition);
    }

    @NotNull
    public static Piece piece(@NotNull PieceBaseType baseType, @NotNull PieceType type, Side side, PromotionInfo promotionInfo,
                              LevellingData levellingData, int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel,
                              double hp, double maxHP, Point initialPosition) {
        return switch (baseType) {
            case PIECE -> new Piece(side, type.getMovementType(side), promotionInfo);
            case FORCED_PIECE -> new ForcedPiece(side, type.getMovementType(side), promotionInfo);
            case MODEST_FORCED_PIECE -> new ModestForcedPiece(side, type.getMovementType(side), promotionInfo);
            case SHOOTING_PIECE -> new ShootingPiece(side, type.getMovementType(side));
            case INVINCIBLE_FORCED_PIECE -> new InvincibleForcedPiece(side, type.getMovementType(side));
            case LEVELLING_FORCED_PIECE -> new LevellingForcedPiece(side, type.getMovementType(side), levellingData, promotionInfo, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, hp, maxHP, initialPosition);
            case CLONING_FORCED_PIECE -> new CloningForcedPiece(side, type.getMovementType(side));
        };
    }
}
