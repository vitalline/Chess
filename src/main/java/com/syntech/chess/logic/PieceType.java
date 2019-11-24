package com.syntech.chess.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum PieceType {
    NONE("Cell"),
    PAWN("Pawn"),
    DOUBLE_PAWN("Pawn"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    ROOK("Rook"),
    QUEEN("Queen"),
    KING("King"),
    AMAZON("Amazon");

    private String name;

    @Contract(pure = true)
    PieceType(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    @Contract(pure = true)
    public String getProperName() {
        return name;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return "PieceType{" +
                "name='" + name + '\'' +
                '}';
    }
}
