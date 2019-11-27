package com.syntech.chess.logic;

import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.chess.*;
import com.syntech.chess.rules.neutral.ImmovableType;
import com.syntech.chess.rules.variants.AmazonType;
import com.syntech.chess.rules.variants.SniperType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum PieceType {
    EMPTY("Cell", "Empty Cell"),
    WALL("Wall", "Wall"),
    PAWN("Pawn"),
    DOUBLE_PAWN("Pawn"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    SNIPER("Bishop", "Sniper"),
    ROOK("Rook"),
    QUEEN("Queen"),
    KING("King"),
    AMAZON("Amazon");

    private final String name;
    private final String properName;

    @Contract(pure = true)
    PieceType(String name) {
        this.name = name;
        this.properName = name;
    }

    @Contract(pure = true)
    PieceType(String name, String properName) {
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

    @Nullable
    public MovementType getMovementType(Side side) {
        switch (this) {
            case EMPTY:
            case WALL:
                return new ImmovableType(this);
            case PAWN:
                return new PawnType(side);
            case DOUBLE_PAWN:
                return new DoublePawnType(side);
            case KNIGHT:
                return new KnightType();
            case BISHOP:
                return new BishopType();
            case SNIPER:
                return new SniperType();
            case ROOK:
                return new RookType();
            case QUEEN:
                return new QueenType();
            case KING:
                return new KingType(side);
            case AMAZON:
                return new AmazonType();
            default:
                return null;
        }
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
