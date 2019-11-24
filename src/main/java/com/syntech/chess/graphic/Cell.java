package com.syntech.chess.graphic;

import com.syntech.chess.Main;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.XPRules;
import org.apache.commons.io.IOUtils;
import org.ice1000.jimgui.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    private static ArrayList<JImTextureID> textures;
    private static ArrayList<String> names;

    public static void initialize() throws IOException {
        textures = new ArrayList<>();
        names = new ArrayList<>();
        loadTexture(Side.NONE, PieceType.NONE);
        loadSet(Side.WHITE);
        loadSet(Side.BLACK);
        loadFASet(Side.WHITE);
        loadFASet(Side.BLACK);
    }

    private static void loadTexture(Side side, PieceType type) throws IOException {
        String texturePath = String.format("textures/chess/%s.png", getName(side, type));
        InputStream in = Main.class.getClassLoader().getResourceAsStream(texturePath);
        assert in != null;
        textures.add(JImTextureID.fromBytes(IOUtils.toByteArray(in)));
        names.add(getName(side, type));
    }

    private static void loadTextures(Side side, PieceType type, int amount) throws IOException {
        for (int i = 0; i < amount; i++) {
            String texturePath = String.format("textures/fa_forced/%s/%d.png", getName(side, type), i);
            InputStream in = Main.class.getClassLoader().getResourceAsStream(texturePath);
            assert in != null;
            textures.add(JImTextureID.fromBytes(IOUtils.toByteArray(in)));
            names.add(String.format("%s%d", getName(side, type), i));
        }
    }

    private static void loadSet(Side side) throws IOException {
        loadTexture(side, PieceType.PAWN);
        loadTexture(side, PieceType.KNIGHT);
        loadTexture(side, PieceType.BISHOP);
        loadTexture(side, PieceType.ROOK);
        loadTexture(side, PieceType.QUEEN);
        loadTexture(side, PieceType.MAGA);
        loadTexture(side, PieceType.KING);
    }

    private static void loadFASet(Side side) throws IOException {
        loadTextures(side, PieceType.PAWN, XPRules.PAWNLEVELUP);
        loadTextures(side, PieceType.KNIGHT, XPRules.KNIGHTLEVELUP);
        loadTextures(side, PieceType.BISHOP, XPRules.BISHOPLEVELUP);
        loadTextures(side, PieceType.ROOK, XPRules.ROOKLEVELUP);
        loadTextures(side, PieceType.QUEEN, XPRules.QUEENLEVELUP);
        loadTextures(side, PieceType.MAGA, 1);
        loadTextures(side, PieceType.KING, 1);
    }

    @Nullable
    private static JImTextureID getTexture(String name) {
        int index = names.indexOf(name);
        if (index >= 0) {
            return textures.get(index);
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
