package com.syntech.chess.graphic;

import com.syntech.chess.logic.Side;
import org.ice1000.jimgui.JImVec4;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Color {
    NONE("empty"),
    WHITE("white"),
    BLACK("black"),
    SELECTED_WHITE("selectedWhite"),
    SELECTED_BLACK("selectedBlack"),
    MOVE_WHITE("moveWhite"),
    MOVE_BLACK("moveBlack"),
    CAPTURE_WHITE("moveWhite"),
    CAPTURE_BLACK("moveBlack");

    private String name;

    @Contract(pure = true)
    Color(String name) {
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
        return "Color{" +
                "name='" + name + '\'' +
                '}';
    }

    @Contract(pure = true)
    public Side toSide() {
        switch (this) {
            case WHITE:
            case SELECTED_WHITE:
            case MOVE_WHITE:
            case CAPTURE_WHITE:
                return Side.WHITE;
            case BLACK:
            case SELECTED_BLACK:
            case MOVE_BLACK:
            case CAPTURE_BLACK:
                return Side.BLACK;
            default:
                return Side.NEUTRAL;
        }
    }

    @NotNull
    @Contract(" -> new")
    public JImVec4 getColor() {
        switch (this) {
            case WHITE:
                return new JImVec4(0.8f, 0.8f, 0.8f, 1.0f);
            case BLACK:
                return new JImVec4(0.2f, 0.2f, 0.2f, 1.0f);
            case SELECTED_WHITE:
                return new JImVec4(0.9f, 0.9f, 0.7f, 1.0f);
            case SELECTED_BLACK:
                return new JImVec4(0.3f, 0.3f, 0.1f, 1.0f);
            case MOVE_WHITE:
                return new JImVec4(0.7f, 0.9f, 0.7f, 1.0f);
            case MOVE_BLACK:
                return new JImVec4(0.1f, 0.3f, 0.1f, 1.0f);
            case CAPTURE_WHITE:
                return new JImVec4(0.9f, 0.7f, 0.7f, 1.0f);
            case CAPTURE_BLACK:
                return new JImVec4(0.3f, 0.1f, 0.1f, 1.0f);
            default:
                return new JImVec4(0.5f, 0.5f, 0.5f, 1.0f);
        }
    }

    @NotNull
    @Contract(" -> new")
    public JImVec4 getHoveredColor() {
        switch (this) {
            case WHITE:
                return new JImVec4(0.85f, 0.85f, 0.85f, 1.0f);
            case BLACK:
                return new JImVec4(0.25f, 0.25f, 0.25f, 1.0f);
            case SELECTED_WHITE:
                return new JImVec4(0.95f, 0.95f, 0.75f, 1.0f);
            case SELECTED_BLACK:
                return new JImVec4(0.35f, 0.35f, 0.15f, 1.0f);
            case MOVE_WHITE:
                return new JImVec4(0.75f, 0.95f, 0.75f, 1.0f);
            case MOVE_BLACK:
                return new JImVec4(0.15f, 0.35f, 0.15f, 1.0f);
            case CAPTURE_WHITE:
                return new JImVec4(0.95f, 0.75f, 0.75f, 1.0f);
            case CAPTURE_BLACK:
                return new JImVec4(0.35f, 0.15f, 0.15f, 1.0f);
            default:
                return new JImVec4(0.5f, 0.5f, 0.5f, 1.0f);
        }
    }

    @NotNull
    @Contract(" -> new")
    public JImVec4 getActiveColor() {
        switch (this) {
            case WHITE:
                return new JImVec4(0.825f, 0.825f, 0.825f, 1.0f);
            case BLACK:
                return new JImVec4(0.225f, 0.225f, 0.225f, 1.0f);
            case SELECTED_WHITE:
                return new JImVec4(0.925f, 0.925f, 0.725f, 1.0f);
            case SELECTED_BLACK:
                return new JImVec4(0.325f, 0.325f, 0.125f, 1.0f);
            case MOVE_WHITE:
                return new JImVec4(0.725f, 0.925f, 0.725f, 1.0f);
            case MOVE_BLACK:
                return new JImVec4(0.125f, 0.325f, 0.125f, 1.0f);
            case CAPTURE_WHITE:
                return new JImVec4(0.925f, 0.725f, 0.725f, 1.0f);
            case CAPTURE_BLACK:
                return new JImVec4(0.325f, 0.125f, 0.125f, 1.0f);
            default:
                return new JImVec4(0.5f, 0.5f, 0.5f, 1.0f);
        }
    }
}
