package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JniLoaderEx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main {

    private static Translation translation = Translation.EN_US;

    public static void main(String... args) throws IOException {
        JniLoaderEx.loadGlfw();
        final int width = 1280, height = 800, cellSize = 50, margin = 10, menuButtonAmount = 15;
        try (JImGui imGui = new JImGui(width, height, "Chess")) {
            CellGraphics.initialize();

            Board board = null;
            Setup setup = null;
            int menuPage = 0;
            boolean showLog = false, showInfo = false;

            String fontName = "PureProg 12.ttf";
            File fontFile = new File(fontName);
            if (!fontFile.exists()) {
                String fontPath = "fonts/" + fontName;
                InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
                assert fontInput != null;
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            }

            NativeShort glyphRange = imGui.getIO().getFonts().getGlyphRangesForCyrillic();

            JImFont font = imGui.getIO().getFonts().addFontFromFile(fontName, (float) cellSize / 2, glyphRange);
            font.setDisplayOffsetX((float) cellSize / 25);
            font.setDisplayOffsetY((float) -cellSize / 25);

            imGui.setBackground(Color.BACKGROUND.getColor());

            imGui.initBeforeMainLoop();

            while (!imGui.windowShouldClose()) {

                imGui.initNewFrame();

                imGui.pushStyleColor(JImStyleColors.WindowBg, Color.BORDER.getColor());
                imGui.pushStyleColor(JImStyleColors.Button, Color.BUTTON.getColor());
                imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.BUTTON.getHoveredColor());
                imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.BUTTON.getActiveColor());

                imGui.setWindowPos("Menu", margin, margin);

                if (board == null) {
                    imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                    for (int i = menuPage * menuButtonAmount; i < (menuPage + 1) * menuButtonAmount && i < Setup.values().length; i++) {
                        Board newBoard = Setup.values()[i].boardButton(imGui, translation);
                        if (newBoard != null) {
                            setup = Setup.values()[i];
                            board = newBoard;
                        }
                    }

                    if (menuPage > 0) {
                        if (CellGraphics.display(imGui, "left", translation.get("action_previous"), cellSize, Color.WHITE, -2)) {
                            --menuPage;
                        }
                    }

                    if (menuPage < Setup.values().length / menuButtonAmount) {
                        if (CellGraphics.display(imGui, "right", translation.get("action_next"), cellSize, Color.WHITE, -2)) {
                            ++menuPage;
                        }
                    }

                    JImGuiGen.end();
                }

                imGui.begin("Language", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                for (Translation t : Translation.values()) {
                    if (t.ordinal() > 0) {
                        if (imGui.button(t.get("language#short"))) {
                            translation = t;
                            if (board != null) {
                                board.setTranslation(translation);
                            }
                        }
                        if (imGui.isItemHovered()) {
                            JImGuiGen.beginTooltip();
                            imGui.text(t.get("language"));
                            JImGuiGen.endTooltip();
                        }
                    }
                }

                imGui.setWindowPos("Language", width - JImGuiGen.getWindowWidth() - margin, margin);

                JImGuiGen.end();

                JImGuiGen.popStyleColor(3);

                if (setup != null && board != null) {

                    imGui.setWindowPos("Board", margin, margin);
                    board.display(imGui, "Board", cellSize);

                    imGui.setWindowPos("Game Info", margin, board.getWindowHeight() + margin);
                    imGui.begin("Game Info", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                    String status = board.getStatusString();
                    if (status == null) {
                        status = String.format(translation.get("status_turn"), board.getTurnSide().getProperName(translation));
                    }
                    CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, cellSize, board.getTurnSide().toColor(), -1);

                    imGui.sameLine();

                    imGui.text(status + "\n" + String.format(translation.get("status_game"), setup.getGameType(translation)));

                    if (board.getPreviousBoard() != null) {
                        if (CellGraphics.display(imGui, "left", translation.get("action_undo"), cellSize, Color.WHITE, -1)) {
                            board = board.getPreviousBoard();
                            board.setTranslation(translation);
                        }
                    } else {
                        CellGraphics.display(imGui, "left", translation.get("action_undo"), cellSize, Color.NONE, -1);
                    }

                    imGui.sameLine();

                    if (board.canRedo()) {
                        if (CellGraphics.display(imGui, "right", translation.get("action_redo"), cellSize, Color.WHITE, -1)) {
                            board.redo();
                        }
                    } else {
                        CellGraphics.display(imGui, "right", translation.get("action_redo"), cellSize, Color.NONE, -1);
                    }

                    imGui.sameLine();

                    if (CellGraphics.display(imGui, "qmark", translation.get("action_random"), cellSize, Color.WHITE, -1)) {
                        board.makeRandomMove();
                    }

                    imGui.sameLine();

                    int logHeight = 170;

                    if (showLog) {
                        board.displayLog(imGui, width - 2 * margin, logHeight, margin, height - logHeight - margin, width * 4 / cellSize);
                        if (CellGraphics.display(imGui, "info", translation.get("action_close_log"), cellSize, Color.MOVE_WHITE, -1)) {
                            showLog = false;
                        }
                    } else {
                        if (CellGraphics.display(imGui, "info", translation.get("action_open_log"), cellSize, Color.CAPTURE_WHITE, -1)) {
                            showLog = true;
                        }
                    }

                    imGui.sameLine();

                    if (CellGraphics.display(imGui, "info", translation.get("action_info"), cellSize, Color.SELECTED_WHITE, 1000)) {
                        showInfo = true;
                    }

                    imGui.sameLine();

                    if (CellGraphics.display(imGui, "restart", translation.get("action_restart"), cellSize, Color.WHITE, -1)) {
                        board = setup.getBoard(translation);
                    }

                    imGui.sameLine();

                    if (CellGraphics.display(imGui, "cross", translation.get("action_return"), cellSize, Color.CAPTURE_WHITE, -1)) {
                        board = null;
                    }

                    JImGuiGen.end();

                    if (showInfo) {
                        imGui.openPopup(translation.get("info"));
                    }

                    //And here we see the most non-user-friendly thing ever in JImGui.
                    //Like, seriously, who even declares a boolean variable like that?
                    //While it's usually possible to use "new NativeBool()", here it's
                    //necessary to initialize it to "true", else the popup won't open.

                    NativeBool alwaysTrue = new NativeBool();
                    alwaysTrue.modifyValue(true);

                    if (imGui.beginPopupModal(translation.get("info"), alwaysTrue, JImWindowFlags.NoResize)) {
                        imGui.text(setup.getGameType(translation));
                        imGui.text(setup.getGameInfo(translation));
                        if (imGui.button(translation.get("action_ok"))) {
                            showInfo = false;
                            JImGuiGen.closeCurrentPopup();
                        }
                        JImGuiGen.endPopup();
                    }

                    if (!imGui.isPopupOpen(translation.get("info"))) {
                        showInfo = false;
                    }
                }

                imGui.render();
            }
        }
    }
}