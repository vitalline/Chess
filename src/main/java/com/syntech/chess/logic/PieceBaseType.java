package com.syntech.chess.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum PieceBaseType {
    PIECE("Piece", ""),
    FORCED_PIECE("ForcedPiece", "Forced"),
    MODEST_FORCED_PIECE("ModestForcedPiece", "Modest Forced"),
    SHOOTING_PIECE("ShootingPiece", ""),
    LEVELLING_FORCED_PIECE("LevellingForcedPiece", "Forced"),
    LEVELLING_UP_FORCED_PIECE("LevellingUpForcedPiece", "Forced"),
    LEVELLING_DOWN_FORCED_PIECE("LevellingDownForcedPiece", "Forced"),
    CLONING_FORCED_PIECE("CloningForcedPiece", "Cloning Forced"),
    NEUTRAL_PIECE("NeutralPiece", "");

    private final String name;
    private final String properName;

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
