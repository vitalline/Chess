package com.syntech.chess.logic;

import com.syntech.chess.graphic.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Side {
    NEUTRAL("neutral", ""),
    WHITE("white", "White"),
    BLACK("black", "Black");

    private String name;
    private String properName;

    @Contract(pure = true)
    Side(String name) {
        this.name = name;
        this.properName = name;
    }

    @Contract(pure = true)
    Side(String name, String properName) {
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
        return "Side{" +
                "name='" + name + '\'' +
                '}';
    }

    @Contract(pure = true)
    public Color toColor() {
        switch (this) {
            case WHITE:
                return Color.WHITE;
            case BLACK:
                return Color.BLACK;
            default:
                return Color.NONE;
        }
    }

    @Contract(pure = true)
    public Side getOpponent() {
        switch (this) {
            case WHITE:
                return Side.BLACK;
            case BLACK:
                return Side.WHITE;
            default:
                return Side.NEUTRAL;
        }
    }
}
