package com.syntech.chess.graphic;

import com.syntech.chess.logic.Side;
import org.ice1000.jimgui.JImVec4;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Color {
    NONE,
    BORDER,
    BACKGROUND,
    BUTTON,
    WHITE,
    BLACK,
    SELECTED_WHITE,
    SELECTED_BLACK,
    MOVE_WHITE,
    MOVE_BLACK,
    CAPTURE_WHITE,
    CAPTURE_BLACK;

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
            case BORDER:
                return new JImVec4(0.27f, 0.66f, 0.2f, 1.0f);
            case BACKGROUND:
                return new JImVec4(0.47f, 0.86f, 0.4f, 1.0f);
            case BUTTON:
                return new JImVec4(0.07f, 0.27f, 0.07f, 1.0f);
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
            case BUTTON:
                return new JImVec4(0.17f, 0.37f, 0.17f, 1.0f);
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
            case BUTTON:
                return new JImVec4(0.27f, 0.47f, 0.27f, 1.0f);
            default:
                return new JImVec4(0.5f, 0.5f, 0.5f, 1.0f);
        }
    }
}
