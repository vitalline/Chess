package com.syntech.chess.graphic;

import com.syntech.chess.Main;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.apache.commons.io.IOUtils;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.JImTextureID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CellGraphics {
    private static ArrayList<JImTextureID> textures;
    private static ArrayList<String> names;

    public static final int XP_BAR_STAGES = 48;

    public static void initialize() throws IOException {
        textures = new ArrayList<>();
        names = new ArrayList<>();
        loadTexture("cross", "ui");
        loadTexture("left", "ui");
        loadTexture("right", "ui");
        loadTexture(Side.NEUTRAL, PieceType.EMPTY);
        loadTexture(Side.NEUTRAL, PieceType.WALL);
        loadSet(Side.WHITE);
        loadSet(Side.BLACK);
        loadXPSet(Side.WHITE);
        loadXPSet(Side.BLACK);
    }

    private static void loadTexture(Side side, PieceType type) throws IOException {
        loadTexture(getName(side, type), "chess");
    }

    private static void loadTexture(String name, String folder) throws IOException {
        String texturePath = String.format("textures/%s/%s.png", folder, name);
        InputStream textureInput = Main.class.getClassLoader().getResourceAsStream(texturePath);
        assert textureInput != null;
        textures.add(JImTextureID.fromBytes(IOUtils.toByteArray(textureInput)));
        names.add(name);
    }

    private static void loadTextures(Side side, PieceType type) throws IOException {
        String texturePath = String.format("textures/chess/%s.png", getName(side, type));
        InputStream textureInput = Main.class.getClassLoader().getResourceAsStream(texturePath);
        assert textureInput != null;
        BufferedImage texture = ImageIO.read(textureInput);
        Graphics2D graphics = (Graphics2D) texture.getGraphics();
        for (int i = 0; i <= XP_BAR_STAGES; i++) {
            String barTexturePath = String.format("textures/xp_bar/%d.png", i);
            InputStream barTextureInput = Main.class.getClassLoader().getResourceAsStream(barTexturePath);
            assert barTextureInput != null;
            BufferedImage barTexture = ImageIO.read(barTextureInput);
            graphics.drawImage(barTexture, 0, 0, null);
            ByteArrayOutputStream textureOutput = new ByteArrayOutputStream();
            ImageIO.write(texture, "png", textureOutput);
            textures.add(JImTextureID.fromBytes(textureOutput.toByteArray()));
            names.add(String.format("%s%d", getName(side, type), i));
        }
    }

    private static void loadSet(Side side) throws IOException {
        loadTexture(side, PieceType.EMPTY);
        loadTexture(side, PieceType.PAWN);
        loadTexture(side, PieceType.KNIGHT);
        loadTexture(side, PieceType.BISHOP);
        loadTexture(side, PieceType.ROOK);
        loadTexture(side, PieceType.QUEEN);
        loadTexture(side, PieceType.AMAZON);
        loadTexture(side, PieceType.KING);
    }

    private static void loadXPSet(Side side) throws IOException {
        loadTextures(side, PieceType.EMPTY);
        loadTextures(side, PieceType.PAWN);
        loadTextures(side, PieceType.KNIGHT);
        loadTextures(side, PieceType.BISHOP);
        loadTextures(side, PieceType.ROOK);
        loadTextures(side, PieceType.QUEEN);
        loadTextures(side, PieceType.AMAZON);
        loadTextures(side, PieceType.KING);
    }

    @Nullable
    private static JImTextureID getTexture(String name) {
        int index = names.indexOf(name);
        if (index >= 0) {
            return textures.get(index);
        }
        return null;
    }

    @NotNull
    private static String getName(@NotNull Side side, @NotNull PieceType type) {
        return side.getTextureID() + type.getTextureID();
    }

    public static boolean display(@NotNull JImGui imGui, String name, String label, float size, @NotNull Color color, int id) {
        imGui.pushStyleColor(JImStyleColors.Button, color.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, color.getHoveredColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, color.getActiveColor());
        JImGui.pushID(id);
        boolean result = imGui.imageButton(Objects.requireNonNull(getTexture(name)), size, size);
        if (imGui.isItemHovered()) {
            JImGuiGen.beginTooltip();
            imGui.text(label);
            JImGuiGen.endTooltip();
        }
        JImGuiGen.popID();
        JImGuiGen.popStyleColor(3);
        return result;
    }

    public static boolean display(JImGui imGui, @NotNull Side side, @NotNull PieceType type, String label, float size, Color color, int id) {
        return display(imGui, getName(side, type), label, size, color, id);
    }


    public static boolean display(JImGui imGui, @NotNull Piece piece, String label, float size, Color color, int id) {
        return display(imGui, piece.getTextureID(), label, size, color, id);
    }
}
