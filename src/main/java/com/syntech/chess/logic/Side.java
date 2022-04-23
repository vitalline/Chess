package com.syntech.chess.logic;

import com.syntech.chess.graphic.Color;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

public enum Side {
    NONE("", ""),
    NEUTRAL("neutral", ""),
    WHITE("white", "side.white"),
    BLACK("black", "side.black");

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

    public String getTranslationString() {
        return translationString;
    }

    @NotNull
    public String getProperName(@NotNull Translation translation) {
        return translation.get(translationString);
    }

    public Color toColor() {
        return switch (this) {
            case WHITE -> Color.WHITE;
            case BLACK -> Color.BLACK;
            default -> Color.NONE;
        };
    }

    public Side getOpponent() {
        return switch (this) {
            case WHITE -> Side.BLACK;
            case BLACK -> Side.WHITE;
            default -> Side.NEUTRAL;
        };
    }
}
