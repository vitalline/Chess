package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.graphic.FileChooser;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JniLoaderEx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            boolean showLog = false, showInfo = false, showErrorMessage = false, saveMode = false, blockInput = false;
            String fileName = null;
            String errorMessage = null;
            FileChooser fileChooser = null;

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

                //TODO: make the UI code a bit less cumbersome to navigate

                imGui.initNewFrame();

                if (!blockInput) {

                    imGui.pushStyleColor(JImStyleColors.WindowBg, Color.BORDER.getColor());
                    imGui.pushStyleColor(JImStyleColors.Button, Color.BUTTON.getColor());
                    imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.BUTTON.getHoveredColor());
                    imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.BUTTON.getActiveColor());

                    imGui.setWindowPos("Menu", margin, margin);

                    if (setup == null || board == null) {
                        imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                        for (int i = menuPage * menuButtonAmount; i < (menuPage + 1) * menuButtonAmount && i < Setup.values().length; i++) {
                            Board newBoard = Setup.values()[i].boardButton(imGui, translation);
                            if (newBoard != null) {
                                setup = Setup.values()[i];
                                board = newBoard;
                            }
                        }

                        if (menuPage > 0) {
                            if (CellGraphics.display(imGui, "left", translation.get("action.previous"), cellSize, Color.WHITE, -2)) {
                                --menuPage;
                            }
                        } else {
                            CellGraphics.display(imGui, "left", translation.get("action.previous"), cellSize, Color.NONE, -2);
                        }

                        imGui.sameLine();

                        if (menuPage < Setup.values().length / menuButtonAmount) {
                            if (CellGraphics.display(imGui, "right", translation.get("action.next"), cellSize, Color.WHITE, -2)) {
                                ++menuPage;
                            }
                        } else {
                            CellGraphics.display(imGui, "right", translation.get("action.next"), cellSize, Color.NONE, -2);
                        }

                        imGui.sameLine();

                        if (CellGraphics.display(imGui, "load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
                            saveMode = false;
                            blockInput = true;
                            fileChooser = new FileChooser(saveMode);
                            fileChooser.start();
                        }

                        JImGuiGen.end();
                    }

                    imGui.begin("Language", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                    for (Translation t : Translation.values()) {
                        if (t.ordinal() > 0) {
                            if (imGui.button(t.get("language.short"))) {
                                translation = t;
                                if (board != null) {
                                    board.setTranslation(translation);
                                }
                            }
                            if (imGui.isItemHovered()) {
                                JImGuiGen.beginTooltip();
                                imGui.text(t.get("language.name"));
                                JImGuiGen.endTooltip();
                            }
                        }
                    }

                    imGui.setWindowPos("Language", width - JImGuiGen.getWindowWidth() - margin, margin);

                    JImGuiGen.end();

                    JImGuiGen.popStyleColor(3);

                    if (setup != null && board != null) {

                        if (board.getTranslation() != translation) {
                            board.setTranslation(translation);
                        }

                        imGui.setWindowPos("Board", margin, margin);
                        board.display(imGui, "Board", cellSize);

                        imGui.begin("Game Info", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

                        String status = board.getStatusString();
                        if (status == null) {
                            status = String.format(translation.get("status.turn"), board.getTurnSide().getProperName(translation));
                        }

                        imGui.text(status);
                        imGui.text(String.format(translation.get("status.game"), setup.getGameType(translation)));

                        if (fileName != null) {
                            if (saveMode) {
                                imGui.text(String.format(translation.get("status.saved_as"), fileName));
                            } else {
                                imGui.text(String.format(translation.get("status.file"), fileName));
                            }
                        } else {
                            imGui.text("");
                        }

                        if (board.getPreviousBoard() != null) {
                            if (CellGraphics.display(imGui, "double_left", translation.get("action.undo_all"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                board = board.getInitialBoard();
                                board.setTranslation(translation);
                            }
                            imGui.sameLine();
                            if (CellGraphics.display(imGui, "left", translation.get("action.undo"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                board = board.getPreviousBoard();
                                board.setTranslation(translation);
                            }
                        } else {
                            CellGraphics.display(imGui, "double_left", translation.get("action.undo_all"), cellSize, Color.NONE, -1);
                            imGui.sameLine();
                            CellGraphics.display(imGui, "left", translation.get("action.undo"), cellSize, Color.NONE, -1);
                        }

                        imGui.sameLine();

                        if (board.canRedo()) {
                            if (CellGraphics.display(imGui, "right", translation.get("action.redo"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                board.redo();
                            }
                            imGui.sameLine();
                            if (CellGraphics.display(imGui, "double_right", translation.get("action.redo_all"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                board.redoAll();
                            }
                        } else {
                            CellGraphics.display(imGui, "right", translation.get("action.redo"), cellSize, Color.NONE, -1);
                            imGui.sameLine();
                            CellGraphics.display(imGui, "double_right", translation.get("action.redo_all"), cellSize, Color.NONE, -1);
                        }

                        imGui.sameLine();

                        if (CellGraphics.display(imGui, "load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
                            saveMode = false;
                            fileName = null;
                            blockInput = true;
                            fileChooser = new FileChooser(saveMode);
                            fileChooser.start();
                        }

                        imGui.sameLine();

                        if (CellGraphics.display(imGui, "save", translation.get("action.save"), cellSize, Color.WHITE, -1)) {
                            saveMode = true;
                            fileName = null;
                            blockInput = true;
                            fileChooser = new FileChooser(saveMode);
                            fileChooser.start();
                        }

                        CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, cellSize, board.getTurnSide().toColor(), -1);

                        imGui.sameLine();

                        if (CellGraphics.display(imGui, "qmark", translation.get("action.random"), cellSize, Color.WHITE, -1)) {
                            fileName = null;
                            board.makeRandomMove();
                        }

                        imGui.sameLine();

                        if (showLog) {
                            board.displayLog(imGui, width - 2 * margin, height - board.getWindowHeight() - 3 * margin,
                                    margin, board.getWindowHeight() + 2 * margin, width * 4 / cellSize);
                            if (CellGraphics.display(imGui, "log_opened", translation.get("action.log.close"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                showLog = false;
                            }
                        } else {
                            if (CellGraphics.display(imGui, "log_closed", translation.get("action.log.open"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                showLog = true;
                            }
                        }

                        imGui.sameLine();

                        if (setup.gameInfoExists(translation)) {
                            if (CellGraphics.display(imGui, "info", translation.get("action.info"), cellSize, Color.WHITE, -1)) {
                                fileName = saveMode ? null : fileName;
                                showInfo = true;
                            }

                            imGui.sameLine();
                        }

                        if (CellGraphics.display(imGui, "restart", translation.get("action.restart"), cellSize, Color.CAPTURE_WHITE, -1)) {
                            fileName = null;
                            board = setup.getBoard();
                        }

                        imGui.sameLine();

                        if (CellGraphics.display(imGui, "cross", translation.get("action.return"), cellSize, Color.CAPTURE_WHITE, -1)) {
                            fileName = null;
                            board = null;
                        }

                        int infoHeight = (int) JImGuiGen.getWindowHeight();

                        JImGuiGen.end();

                        if (board != null) {
                            if (board.getWindowHeight() > cellSize && board.getWindowWidth() > cellSize) {
                                imGui.setWindowPos("Game Info", board.getWindowWidth() + 2 * margin, board.getWindowHeight() - infoHeight + margin);
                            }
                        }

                        if (showInfo) {
                            imGui.openPopup(setup.getGameType(translation));
                        }
                    }
                } else if (fileChooser != null) {
                    imGui.setWindowPos("FileNotChosen", margin, margin);
                    imGui.begin("FileNotChosen", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                    imGui.text(translation.get("status.choosing_file"));
                    JImGuiGen.end();
                    if (fileChooser.getStatus() != null) {
                        if (fileChooser.getFilePath() != null) {
                            if (saveMode) {
                                if (board != null && setup != null) {
                                    String[] path = fileChooser.getFilePath().split("[/\\\\]");
                                    fileName = path[path.length - 1];
                                    board.saveToPGN(fileChooser.getFilePath(), setup);
                                }
                            } else {
                                byte[] contents = Files.readAllBytes(Paths.get(fileChooser.getFilePath()));
                                String pgn = new String(contents, StandardCharsets.UTF_8);
                                Setup newSetup = Move.getSetupFromPGN(pgn);
                                if (newSetup == null) {
                                    showErrorMessage = true;
                                    errorMessage = translation.get("error.pgn.invalid_variant");
                                } else {
                                    Board newBoard = Move.getGameFromPGN(pgn);
                                    if (newBoard == null) {
                                        showErrorMessage = true;
                                        errorMessage = translation.get("error.pgn.invalid_move");
                                    } else {
                                        String[] path = fileChooser.getFilePath().split("[/\\\\]");
                                        fileName = path[path.length - 1];
                                        setup = newSetup;
                                        board = newBoard;
                                    }
                                }
                            }
                        }
                        blockInput = false;
                    }
                }

                if (showErrorMessage) {
                    imGui.openPopup(translation.get("error"));
                }

                //And here we see the most non-user-friendly thing ever in JImGui.
                //Like, seriously, who even declares a boolean variable like that?
                //While it's usually possible to use "new NativeBool()", here it's
                //necessary to initialize it to "true", else the popup won't open.

                NativeBool alwaysTrue = new NativeBool();
                alwaysTrue.modifyValue(true);

                if (setup != null) {
                    if (imGui.beginPopupModal(setup.getGameType(translation), alwaysTrue, JImWindowFlags.NoResize)) {
                        imGui.text(setup.getGameInfo(translation));
                        if (imGui.button(translation.get("action.ok"))) {
                            showInfo = false;
                            JImGuiGen.closeCurrentPopup();
                        }
                        JImGuiGen.endPopup();
                    }

                    if (!imGui.isPopupOpen(setup.getGameType(translation))) {
                        showInfo = false;
                    }
                }

                if (imGui.beginPopupModal(translation.get("error"), alwaysTrue, JImWindowFlags.NoResize)) {
                    if (errorMessage != null) {
                        imGui.text(errorMessage);
                    } else {
                        imGui.text(translation.get("error.generic"));
                    }
                    if (imGui.button(translation.get("action.ok"))) {
                        showErrorMessage = false;
                        errorMessage = null;
                        JImGuiGen.closeCurrentPopup();
                    }
                    JImGuiGen.endPopup();
                }

                if (!imGui.isPopupOpen(translation.get("error"))) {
                    showErrorMessage = false;
                    errorMessage = null;
                }

                imGui.render();
            }

            if (fileChooser != null && fileChooser.isAlive()) {
                fileChooser.interrupt();
            }
        }
    }
}