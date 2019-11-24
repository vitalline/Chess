package com.syntech.chess.logic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum PieceBaseType {
    NONE("Cell"),
    PIECE("Piece"),
    FORCED_PIECE("ForcedPiece"),
    PROMOTABLE_PIECE("PromotablePiece"),
    PROMOTABLE_FORCED_PIECE("PromotableForcedPiece"),
    FA_FORCED_PIECE("FAForcedPiece");

    private String name;

    @Contract(pure = true)
    PieceBaseType(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
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
