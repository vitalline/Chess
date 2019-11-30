package com.syntech.chess.logic;

import com.syntech.chess.graphic.Color;
import com.syntech.chess.text.Translation;

public enum Side {
    NEUTRAL("neutral", ""),
    WHITE("white", "side_white"),
    BLACK("black", "side_black");

    private final String textureID;
    private final String translationString;

    Side(String textureID) {
        this.textureID = textureID;
        this.translationString = textureID;
    }

    Side(String textureID, String translationString) {
        this.textureID = textureID;
        this.translationString = translationString;
    }

    public String getTextureID() {
        return textureID;
    }

    public String getProperName(Translation translation) {
        return translation.get(translationString);
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
