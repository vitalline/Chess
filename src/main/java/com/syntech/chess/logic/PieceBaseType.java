package com.syntech.chess.logic;

import org.jetbrains.annotations.NotNull;

public enum PieceBaseType {
    PIECE(""),
    FORCED_PIECE("Forced"),
    MODEST_FORCED_PIECE("Modest Forced"),
    SHOOTING_PIECE(""),
    LEVELLING_FORCED_PIECE("Forced"),
    CLONING_FORCED_PIECE("Cloning Forced"),
    NEUTRAL_PIECE("");

    private final String properName;

    PieceBaseType(String properName) {
        this.properName = properName;
    }

    public String getProperName() {
        return properName;
    }

    @NotNull
    @Override
    public String toString() {
        return "PieceBaseType{" +
                "properName='" + properName + '\'' +
                '}';
    }
}
