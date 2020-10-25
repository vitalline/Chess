package com.syntech.chess.graphic;

import com.syntech.chess.Main;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL32.*;

/**
 * Class with a bunch of static methods used to draw square cells with various textures on top.
 * In this case, the texture correspond to chess pieces and UI elements.
 * <p>
 * Before doing anything else with this class, make sure to run <tt>initialize</tt>
 * in order to load all the textures needed for the app.
 */

public class CellGraphics {
    /**
     * The list of textures used.
     */
    private static ArrayList<Integer> textures;

    /**
     * The list of texture IDs. Used when calling <tt>display</tt>.
     */
    private static ArrayList<String> ids;

    /**
     * The total amount of different sprites used for the piece XP bar.
     */
    public static final int XP_BAR_STAGES = 48;

    /**
     * Loads all the textures into memory.
     * Always run this before using the class.
     */
    public static void initialize() throws IOException {
        textures = new ArrayList<>();
        ids = new ArrayList<>();

        //This should always be the default one. Just in case.
        loadTexture("ui", "missingno");

        loadUITextures();
        loadTexture(Side.NEUTRAL, PieceType.EMPTY);
        loadTexture(Side.NEUTRAL, PieceType.WALL);
        loadSet(Side.WHITE);
        loadSet(Side.BLACK);
        loadXPSet(Side.WHITE);
        loadXPSet(Side.BLACK);
    }

    /**
     * Loads a texture for a single chess piece, given by its side and type.
     * The ID is determined as <tt>{@link #getName(Side, PieceType) getName}(side, type)</tt>.
     */
    private static void loadTexture(Side side, PieceType type) throws IOException {
        loadTexture("chess", getName(side, type));
    }

    /**
     * Loads all the UI textures.
     */
    private static void loadUITextures() throws IOException {
        loadTexture("ui", "ai_seconds");
        loadTexture("ui", "ai_turns");
        loadTexture("ui", "cross");
        loadTexture("ui", "double_left");
        loadTexture("ui", "double_right");
        loadTexture("ui", "down");
        loadTexture("ui", "info");
        loadTexture("ui", "left");
        loadTexture("ui", "load");
        loadTexture("ui", "log_closed");
        loadTexture("ui", "log_opened");
        loadTexture("ui", "qmark");
        loadTexture("ui", "restart");
        loadTexture("ui", "right");
        loadTexture("ui", "save");
        loadTexture("ui", "settings");
        loadTexture("ui", "start");
        loadTexture("ui", "stop");
        loadTexture("ui", "up");
    }

    private static int loadTexture(@NotNull final BufferedImage image) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 for RGBA, 3 for RGB
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        final int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureID;
    }

    /**
     * Loads a single texture from the following path:
     * <tt>textures/(folder)/(name).png</tt>
     * The <tt>name</tt> parameter is then used as the texture ID.
     */
    private static void loadTexture(String folder, String name) throws IOException {
        String texturePath = String.format("textures/%s/%s.png", folder, name);
        InputStream textureInput = Main.class.getClassLoader().getResourceAsStream(texturePath);
        if (textureInput != null) {
            textures.add(loadTexture(ImageIO.read(textureInput)));
            ids.add(name);
        }
    }

    /**
     * Loads the textures for a chess piece with an XP bar.
     * The textures are built by using a single piece texture and adding the
     * XP bar textures from the <tt>textures/xp_bar</tt> folder on top.
     * The chess piece is given by its side and type.
     * The texture ID is the piece texture ID concatenated with the
     * corresponding XP bar filename (without the extension).
     */
    private static void loadXPTextures(Side side, PieceType type) throws IOException {
        String texturePath = String.format("textures/chess/%s.png", getName(side, type));
        InputStream textureInput = Main.class.getClassLoader().getResourceAsStream(texturePath);
        BufferedImage texture;
        if (textureInput != null) {
            texture = ImageIO.read(textureInput);
            Graphics2D graphics = (Graphics2D) texture.getGraphics();
            for (int i = 0; i <= XP_BAR_STAGES; i++) {
                String barTexturePath = String.format("textures/xp_bar/%d.png", i);
                InputStream barTextureInput = Main.class.getClassLoader().getResourceAsStream(barTexturePath);
                BufferedImage barTexture;
                if (barTextureInput != null) {
                    barTexture = ImageIO.read(barTextureInput);
                    graphics.drawImage(barTexture, 0, 0, null);
                    ByteArrayOutputStream textureOutput = new ByteArrayOutputStream();
                    ImageIO.write(texture, "png", textureOutput);
                    textures.add(loadTexture(ImageIO.read(new ByteArrayInputStream(textureOutput.toByteArray()))));
                    ids.add(String.format("%s%d", getName(side, type), i));
                }
            }
        }
    }

    /**
     * Loads a set of chess pieces for one side.
     */
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

    /**
     * Loads a set of chess pieces with XP bars for one side.
     */
    private static void loadXPSet(Side side) throws IOException {
        loadXPTextures(side, PieceType.EMPTY);
        loadXPTextures(side, PieceType.PAWN);
        loadXPTextures(side, PieceType.KNIGHT);
        loadXPTextures(side, PieceType.BISHOP);
        loadXPTextures(side, PieceType.ROOK);
        loadXPTextures(side, PieceType.QUEEN);
        loadXPTextures(side, PieceType.AMAZON);
        loadXPTextures(side, PieceType.KING);
    }

    /**
     * Returns a texture by its ID.
     */
    private static int getTexture(String textureID) {
        int index = ids.indexOf(textureID);
        if (index >= 0) {
            return textures.get(index);
        }
        return textures.get(0);
    }

    /**
     * Returns the default texture ID for a piece by its side and type.
     * The ID is generated by concatenating the default IDs for the side
     * and type, resulting in IDs like "whitePawn" and "blackKing".
     */
    @NotNull
    private static String getName(@NotNull Side side, @NotNull PieceType type) {
        return side.getTextureID() + type.getTextureID();
    }

    /**
     * Displays a square cell with a texture given by ID.
     * <p>
     * Internally, all the cells are handled as ImGui buttons to allow player input by clicking.
     * The return value of the {@link ImGui#imageButton(int, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param textureID the desired texture ID
     * @param label     the label to be displayed when hovering over the cell
     * @param size      the width and height of the cell (in pixels)
     * @param color     the background color of the cell
     * @param buttonID  the ID of the button (used for JImGui to determine
     *                  which of the buttons to press if there are two
     *                  buttons that look the same)
     * @return <tt>true</tt> if the button has been pressed
     */
    public static boolean display(String textureID, String label, float size,
                                  @NotNull Color color, int buttonID) {
        ImGui.pushStyleColor(ImGuiCol.Button, color.getColor());
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, color.getHoveredColor());
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, color.getActiveColor());
        ImGui.pushID(buttonID);
        boolean result = ImGui.imageButton(getTexture(textureID), size, size);
        if (ImGui.isItemHovered()) {
            ImGui.beginTooltip();
            ImGui.text(label);
            ImGui.endTooltip();
        }
        ImGui.popID();
        ImGui.popStyleColor(3);
        return result;
    }

    /**
     * Displays a square cell with a chess piece given by its side and type.
     * <p>
     * Internally, all the cells are handled as JImGui buttons to allow player input by clicking.
     * The return value of the {@link ImGui#imageButton(int, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param side     the side of the piece
     * @param type     the type of the piece
     * @param label    the label to be displayed when hovering over the cell
     * @param size     the width and height of the cell (in pixels)
     * @param color    the background color of the cell
     * @param buttonID the ID of the button (used for JImGui to determine
     *                 which of the buttons to press if there are two
     *                 buttons that look the same)
     * @return <tt>true</tt> if the button has been pressed
     */
    public static boolean display(@NotNull Side side, @NotNull PieceType type, String label, float size,
                                  @NotNull Color color, int buttonID) {
        return display(getName(side, type), label, size, color, buttonID);
    }

    /**
     * Displays a square cell with a certain chess piece.
     * <p>
     * Internally, all the cells are handled as JImGui buttons to allow player input by clicking.
     * The return value of the {@link ImGui#imageButton(int, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param piece    the piece to be displayed
     * @param label    the label to be displayed when hovering over the cell
     * @param size     the width and height of the cell (in pixels)
     * @param color    the background color of the cell
     * @param buttonID the ID of the button (used for JImGui to determine
     *                 which of the buttons to press if there are two
     *                 buttons that look the same)
     * @return <tt>true</tt> if the button has been pressed
     */
    public static boolean display(@NotNull Piece piece, String label, float size,
                                  @NotNull Color color, int buttonID) {
        return display(piece.getTextureID(), label, size, color, buttonID);
    }
}
