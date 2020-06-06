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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private static ArrayList<JImTextureID> textures;

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
        loadTexture("ui", "up");
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
            textures.add(JImTextureID.fromBytes(IOUtils.toByteArray(textureInput)));
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
                    textures.add(JImTextureID.fromBytes(textureOutput.toByteArray()));
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
    @NotNull
    private static JImTextureID getTexture(String textureID) {
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
     * Internally, all the cells are handled as JImGui buttons to allow player input by clicking.
     * The return value of the {@link JImGui#imageButton(JImTextureID, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param imGui     the JImGui object to pass the graphics to
     * @param textureID the desired texture ID
     * @param label     the label to be displayed when hovering over the cell
     * @param size      the width and height of the cell (in pixels)
     * @param color     the background color of the cell
     * @param buttonID  the ID of the button (used for JImGui to determine
     *                  which of the buttons to press if there are two
     *                  buttons that look the same)
     * @return <tt>true</tt> if the button has been pressed
     */
    public static boolean display(@NotNull JImGui imGui, String textureID, String label, float size,
                                  @NotNull Color color, int buttonID) {
        imGui.pushStyleColor(JImStyleColors.Button, color.getColor());
        imGui.pushStyleColor(JImStyleColors.ButtonHovered, color.getHoveredColor());
        imGui.pushStyleColor(JImStyleColors.ButtonActive, color.getActiveColor());
        JImGui.pushID(buttonID);
        boolean result = imGui.imageButton(getTexture(textureID), size, size);
        if (imGui.isItemHovered()) {
            JImGuiGen.beginTooltip();
            imGui.text(label);
            JImGuiGen.endTooltip();
        }
        JImGuiGen.popID();
        JImGuiGen.popStyleColor(3);
        return result;
    }

    /**
     * Displays a square cell with a chess piece given by its side and type.
     * <p>
     * Internally, all the cells are handled as JImGui buttons to allow player input by clicking.
     * The return value of the {@link JImGui#imageButton(JImTextureID, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param imGui    the JImGui object to pass the graphics to
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
    public static boolean display(JImGui imGui, @NotNull Side side, @NotNull PieceType type, String label, float size,
                                  @NotNull Color color, int buttonID) {
        return display(imGui, getName(side, type), label, size, color, buttonID);
    }

    /**
     * Displays a square cell with a certain chess piece.
     * <p>
     * Internally, all the cells are handled as JImGui buttons to allow player input by clicking.
     * The return value of the {@link JImGui#imageButton(JImTextureID, float, float) imageButton}
     * function is then passed over to check if the cell has been pressed.
     *
     * @param imGui    the JImGui object to pass the graphics to
     * @param piece    the piece to be displayed
     * @param label    the label to be displayed when hovering over the cell
     * @param size     the width and height of the cell (in pixels)
     * @param color    the background color of the cell
     * @param buttonID the ID of the button (used for JImGui to determine
     *                 which of the buttons to press if there are two
     *                 buttons that look the same)
     * @return <tt>true</tt> if the button has been pressed
     */
    public static boolean display(JImGui imGui, @NotNull Piece piece, String label, float size,
                                  @NotNull Color color, int buttonID) {
        return display(imGui, piece.getTextureID(), label, size, color, buttonID);
    }
}
