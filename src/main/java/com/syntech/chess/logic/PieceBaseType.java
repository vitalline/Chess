package com.syntech.chess.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum PieceBaseType {
    PIECE("Piece", ""),
    FORCED_PIECE("ForcedPiece", "Forced"),
    MODEST_FORCED_PIECE("ModestForcedPiece", "Modest Forced"),
    PROMOTABLE_PIECE("PromotablePiece", ""),
    PROMOTABLE_FORCED_PIECE("PromotableForcedPiece", "Forced"),
    PROMOTABLE_MODEST_FORCED_PIECE("PromotableModestForcedPiece", "Modest Forced"),
    SHOOTING_PIECE("ShootingPiece", ""),
    FA_FORCED_PIECE("FAForcedPiece", "Forced"),
    CLONING_FORCED_PIECE("CloningForcedPiece", "Cloning Forced"),
    NEUTRAL_PIECE("NeutralPiece", "");

    private String name;
    private String properName;

    @Contract(pure = true)
    PieceBaseType(String name) {
        this.name = name;
        this.properName = name;
    }

    @Contract(pure = true)
    PieceBaseType(String name, String properName) {
        this.name = name;
        this.properName = properName;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    @Contract(pure = true)
    public String getProperName() {
        return properName;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return "PieceBaseType{" +
                "name='" + name + '\'' +
                '}';
    }
}
