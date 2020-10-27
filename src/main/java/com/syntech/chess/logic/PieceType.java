package com.syntech.chess.logic;

import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.chess.*;
import com.syntech.chess.rules.chess3d.*;
import com.syntech.chess.rules.neutral.ImmovableType;
import com.syntech.chess.rules.variants.AmazonType;
import com.syntech.chess.rules.variants.PegasusType;
import com.syntech.chess.rules.variants.SniperType;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;
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
    CARDINAL("Cardinal", "piece.cardinal"),
    QUEEN("Queen", "piece.queen"),
    KING("King", "piece.king"),
    CASTLING_KING("King", "piece.king"),
    AMAZON("Amazon", "piece.amazon"),
    PAWN_3D("Pawn", "piece.pawn"),
    KNIGHT_3D("Knight", "piece.knight"),
    BISHOP_3D("Bishop", "piece.bishop"),
    ROOK_3D("Rook", "piece.rook"),
    CARDINAL_3D("Cardinal", "piece.cardinal"),
    QUEEN_3D("Queen", "piece.queen"),
    KING_3D("King", "piece.king");

    private final String textureID;
    private final String translationString;

    PieceType(String textureID, String translationString) {
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

    @NotNull
    public String getShortNameTag(@NotNull Translation translation) {
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
            case PAWN_3D:
                return new Pawn3DType(side);
            case KNIGHT_3D:
                return new Knight3DType(side);
            case BISHOP_3D:
                return new Bishop3DType(side);
            case CARDINAL_3D:
                return new Cardinal3DType(side);
            case ROOK_3D:
                return new Rook3DType(side);
            case QUEEN_3D:
                return new Queen3DType(side);
            case KING_3D:
                return new King3DType(side);
            default:
                return null;
        }
    }
}
