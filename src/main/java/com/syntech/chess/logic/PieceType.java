package com.syntech.chess.logic;

import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.chess.*;
import com.syntech.chess.rules.neutral.ImmovableType;
import com.syntech.chess.rules.variants.AmazonType;
import com.syntech.chess.rules.variants.PegasusType;
import com.syntech.chess.rules.variants.SniperType;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.Nullable;

public enum PieceType {
    NONE("none", "piece_none"),
    EMPTY("Cell", "piece_empty"),
    WALL("Wall", "piece_wall"),
    PAWN("Pawn", "piece_pawn"),
    DOUBLE_PAWN("Pawn", "piece_pawn"),
    KNIGHT("Knight", "piece_knight"),
    PEGASUS("Knight", "piece_pegasus"),
    BISHOP("Bishop", "piece_bishop"),
    SNIPER("Bishop", "piece_sniper"),
    ROOK("Rook", "piece_rook"),
    CASTLING_ROOK("Rook", "piece_rook"),
    QUEEN("Queen", "piece_queen"),
    KING("King", "piece_king"),
    CASTLING_KING("King", "piece_king"),
    AMAZON("Amazon", "piece_amazon");

    private final String textureID;
    private final String translationString;

    PieceType(String textureID) {
        this.textureID = textureID;
        this.translationString = textureID.toLowerCase();
    }

    PieceType(String textureID, String translationString) {
        this.textureID = textureID;
        this.translationString = translationString;
    }

    public String getTextureID() {
        return textureID;
    }

    public String getProperName(Translation translation) {
        return translation.get(translationString);
    }

    public String getShortNameTag(Translation translation) {
        return translation.get(translationString.replace("piece", "log"));
    }

    @Nullable
    public MovementType getMovementType(Side side) {
        switch (this) {
            case NONE:
            case EMPTY:
            case WALL:
                return new ImmovableType(this);
            case PAWN:
                return new PawnType(side);
            case DOUBLE_PAWN:
                return new DoublePawnType(side);
            case KNIGHT:
                return new KnightType();
            case PEGASUS:
                return new PegasusType();
            case BISHOP:
                return new BishopType();
            case SNIPER:
                return new SniperType();
            case ROOK:
                return new RookType();
            case CASTLING_ROOK:
                return new CastlingRookType();
            case QUEEN:
                return new QueenType();
            case KING:
                return new KingType(side);
            case CASTLING_KING:
                return new CastlingKingType(side);
            case AMAZON:
                return new AmazonType();
            default:
                return null;
        }
    }
}
