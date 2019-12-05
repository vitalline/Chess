package com.syntech.chess.logic;

import com.syntech.chess.text.Translation;

public enum PieceBaseType {
    PIECE("type_none"),
    FORCED_PIECE("type_forced"),
    MODEST_FORCED_PIECE("type_modest_forced"),
    SHOOTING_PIECE("type_none"),
    INVINCIBLE_FORCED_PIECE("type_forced"),
    LEVELLING_FORCED_PIECE("type_forced"),
    CLONING_FORCED_PIECE("type_cloning_forced"),
    NEUTRAL_PIECE("type_none");

    private final String translationString;

    PieceBaseType(String translationString) {
        this.translationString = translationString;
    }

    public String getProperName(Translation translation) {
        return translation.get(translationString);
    }
}
