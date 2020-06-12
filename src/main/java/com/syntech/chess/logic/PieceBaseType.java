package com.syntech.chess.logic;

import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

public enum PieceBaseType {
    PIECE("type.none"),
    FORCED_PIECE("type.forced"),
    MODEST_FORCED_PIECE("type.forced.modest"),
    SHOOTING_PIECE("type.none"),
    INVINCIBLE_FORCED_PIECE("type.forced"),
    LEVELLING_FORCED_PIECE("type.forced"),
    CLONING_FORCED_PIECE("type.forced.cloning"),
    NEUTRAL_PIECE("type.none");

    private final String translationString;

    PieceBaseType(String translationString) {
        this.translationString = translationString;
    }

    public String getTranslationString() {
        return translationString;
    }

    @NotNull
    public String getProperName(@NotNull Translation translation) {
        return translation.get(translationString);
    }
}
