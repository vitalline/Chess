package com.syntech.chess.logic;

import com.syntech.chess.graphic.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Side {
    NONE("empty"),
    WHITE("white"),
    BLACK("black");

    private String name;

    @Contract(pure = true)
    Side(String name) {
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
        return "Side{" +
                "name='" + name + '\'' +
                '}';
    }

    @Contract(pure = true)
    public Color toColor() {
        switch (this) {
            case NONE:
                return Color.NONE;
            case WHITE:
                return Color.WHITE;
            case BLACK:
                return Color.BLACK;
        }
        return Color.NONE;
    }

    @Contract(pure = true)
    public Side getOpponent() {
        switch (this) {
            case NONE:
                return Side.NONE;
            case WHITE:
                return Side.BLACK;
            case BLACK:
                return Side.WHITE;
        }
        return Side.NONE;
    }
}
