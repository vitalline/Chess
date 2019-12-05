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
    NONE("none", "piece.none"),
    EMPTY("Cell", "piece.empty"),
    WALL("Wall", "piece.wall"),
    PAWN("Pawn", "piece.pawn"),
    DOUBLE_PAWN("Pawn", "piece.pawn"),
    KNIGHT("Knight", "piece.knight"),
    PEGASUS("Knight", "piece.pegasus"),
    BISHOP("Bishop", "piece.bishop"),
    SNIPER("Bishop", "piece.sniper"),
    ROOK("Rook", "piece.rook"),
    CASTLING_ROOK("Rook", "piece.rook"),
    QUEEN("Queen", "piece.queen"),
    KING("King", "piece.king"),
    CASTLING_KING("King", "piece.king"),
    AMAZON("Amazon", "piece.amazon");

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
                return new KnightType(side);
            case PEGASUS:
                return new PegasusType(side);
            case BISHOP:
                return new BishopType(side);
            case SNIPER:
                return new SniperType(side);
            case ROOK:
                return new RookType(side);
            case CASTLING_ROOK:
                return new CastlingRookType(side);
            case QUEEN:
                return new QueenType(side);
            case KING:
                return new KingType(side);
            case CASTLING_KING:
                return new CastlingKingType(side);
            case AMAZON:
                return new AmazonType(side);
            default:
                return null;
        }
    }
}
