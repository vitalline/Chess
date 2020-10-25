package com.syntech.chess.graphic;

import com.syntech.chess.logic.Side;
import imgui.ImColor;
import imgui.ImVec4;

/**
 * The enumerator of all possible colors used by the app.
 */

public enum Color {
    NONE(0.5f, 0.5f, 0.5f),
    BORDER(0.27f, 0.66f, 0.2f),
    BACKGROUND(0.47f, 0.86f, 0.4f),
    BUTTON(0.07f, 0.27f, 0.07f, 0.17f, 0.37f, 0.17f, 0.27f, 0.47f, 0.27f),
    WHITE(0.8f, 0.8f, 0.8f, 0.85f, 0.85f, 0.85f, 0.825f, 0.825f, 0.825f),
    BLACK(0.35f, 0.35f, 0.35f, 0.4f, 0.4f, 0.4f, 0.375f, 0.375f, 0.375f),
    MOVABLE_WHITE(0.875f, 0.875f, 0.725f, 0.925f, 0.925f, 0.775f, 0.9f, 0.9f, 0.8f),
    MOVABLE_BLACK(0.425f, 0.425f, 0.275f, 0.475f, 0.475f, 0.325f, 0.45f, 0.45f, 0.35f),
    SELECTED_WHITE(0.9f, 0.9f, 0.7f, 0.95f, 0.95f, 0.75f, 0.925f, 0.925f, 0.725f),
    SELECTED_BLACK(0.45f, 0.45f, 0.25f, 0.5f, 0.5f, 0.3f, 0.475f, 0.475f, 0.275f),
    MOVE_WHITE(0.7f, 0.9f, 0.7f, 0.75f, 0.95f, 0.75f, 0.725f, 0.925f, 0.725f),
    MOVE_BLACK(0.25f, 0.45f, 0.25f, 0.3f, 0.5f, 0.3f, 0.275f, 0.475f, 0.275f),
    CAPTURE_WHITE(0.9f, 0.7f, 0.7f, 0.95f, 0.75f, 0.75f, 0.925f, 0.725f, 0.725f),
    CAPTURE_BLACK(0.45f, 0.25f, 0.25f, 0.5f, 0.3f, 0.3f, 0.475f, 0.275f, 0.275f);

    private final int color, hoveredColor, activeColor;

    public static final ImVec4 backgroundColor = new ImVec4(0.47f, 0.86f, 0.4f, 1.0f);

    Color(float r, float g, float b) {
        color = ImColor.floatToColor(r, g, b);
        hoveredColor = ImColor.floatToColor(r, g, b);
        activeColor = ImColor.floatToColor(r, g, b);
    }

    Color(float r, float g, float b, float hr, float hg, float hb, float ar, float ag, float ab) {
        color = ImColor.floatToColor(r, g, b);
        hoveredColor = ImColor.floatToColor(hr, hg, hb);
        activeColor = ImColor.floatToColor(ar, ag, ab);
    }

    /**
     * Converts a color to the side it belongs to.
     */
    public Side toSide() {
        switch (this) {
            case WHITE:
            case MOVABLE_WHITE:
            case SELECTED_WHITE:
            case MOVE_WHITE:
            case CAPTURE_WHITE:
                return Side.WHITE;
            case BLACK:
            case MOVABLE_BLACK:
            case SELECTED_BLACK:
            case MOVE_BLACK:
            case CAPTURE_BLACK:
                return Side.BLACK;
            default:
                return Side.NEUTRAL;
        }
    }

    /**
     * Returns the "default" button color.
     */
    public int getColor() {
        return color;
    }

    /**
     * Returns the "hovered" button color.
     */
    public int getHoveredColor() {
        return hoveredColor;
    }

    /**
     * Returns the "active" button color.
     */
    public int getActiveColor() {
        return activeColor;
    }
}
