package com.syntech.chess.graphic;

import com.syntech.chess.Main;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import org.ice1000.jimgui.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class Cell {
    private static JImTextureID[] textures;
    private static String[] names;

    public static void initialize() throws URISyntaxException {
        String[] textureList = new File(Main.class.getResource("/textures/chess/").toURI()).list();
        assert textureList != null;
        textures = new JImTextureID[textureList.length];
        names = new String[textureList.length];
        int textureID = 0;
        loadTexture(textureID++, Side.NONE, PieceType.NONE);
        textureID = loadSet(textureID, Side.WHITE);
        textureID = loadSet(textureID, Side.BLACK);
    }

    private static void loadTexture(int textureID, Side side, PieceType type) throws URISyntaxException {
        textures[textureID] = JImTextureID.fromUri(Main.class.getResource(String.format("/textures/chess/%s.png", getName(side, type))).toURI());
        names[textureID] = getName(side, type);
    }

    private static int loadSet(int textureID, Side side) throws URISyntaxException {
        loadTexture(textureID++, side, PieceType.PAWN);
        loadTexture(textureID++, side, PieceType.KNIGHT);
        loadTexture(textureID++, side, PieceType.BISHOP);
        loadTexture(textureID++, side, PieceType.ROOK);
        loadTexture(textureID++, side, PieceType.QUEEN);
        loadTexture(textureID++, side, PieceType.KING);
        return textureID;
    }

    @Nullable
    private static JImTextureID getTexture(String name) {
        int index = Arrays.asList(names).indexOf(name);
        if (index >= 0) {
            return textures[index];
        }
        return null;
    }

    private static JImTextureID getTexture(Side side, PieceType type) {
        return getTexture(getName(side, type));
    }

    @NotNull
    private static String getName(@NotNull Side side, @NotNull PieceType type) {
        return side.getName() + type.getName();
    }

    public static boolean display(@NotNull JImGui imGui, String name, float size, @NotNull Color color, int id) {
        imGui.pushStyleColor(JImStyleColors.Button, color.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, color.getHoveredColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, color.getActiveColor());
        imGui.pushID(id);
        boolean result = imGui.imageButton(Objects.requireNonNull(getTexture(name)), size, size);
        imGui.popID();
        imGui.popStyleColor(3);
        return result;
    }

    public static boolean display(JImGui imGui, Side side, PieceType type, float size, Color color, int id) {
        return display(imGui, getName(side, type), size, color, id);
    }

    public static boolean display(JImGui imGui, @NotNull Piece piece, float size, Color color, int id) {
        return display(imGui, piece.getName(), size, color, id);
    }
}
