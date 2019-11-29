package com.syntech.chess.logic;

import com.syntech.chess.graphic.Color;
import org.jetbrains.annotations.NotNull;

public enum Side {
    NEUTRAL("neutral", ""),
    WHITE("white", "White"),
    BLACK("black", "Black");

    private final String name;
    private final String properName;

    Side(String name) {
        this.name = name;
        this.properName = name;
    }

    Side(String name, String properName) {
        this.name = name;
        this.properName = properName;
    }

    public String getName() {
        return name;
    }

    public String getProperName() {
        return properName;
    }

    @NotNull
    @Override
    public String toString() {
        return "Side{" +
                "name='" + name + '\'' +
                '}';
    }

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
