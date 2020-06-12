package com.syntech.chess.ui;

import com.syntech.chess.Main;
import com.syntech.chess.ai.AI;
import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.graphic.FileChooser;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImWindowFlags;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Base {
    private static final int MAX_PGN_SIZE = 10240;
    private JImGui imGui;
    private int width, height;
    private final int cellSize = 50, margin = 10, speed = 65;
    private boolean windowLoaded = false;
    private float infoPosX = -1, infoPosY = -1;
    private Translation translation = Translation.EN_US;
    private Board board = null;
    private Setup setup = null, infoSetup = null;
    private boolean showLog = false, showInfo = false, showErrorMessage = false, saveMode = false, lockInput = false;
    private String filename = null;
    private String errorMessage = null;
    private FileChooser fileChooser = null;
    private AI ai = null;
    private MenuWindow menuWindow = new MenuWindow(this);
    private StatusWindow statusWindow = new StatusWindow(this);
    private NativeBool alwaysTrue; //This is needed for the popups to work. Don't ask.

    public Base(JImGui imGui, int width, int height) {
        this.imGui = imGui;
        this.width = width;
        this.height = height;

        //And here we see the most non-user-friendly thing ever in JImGui.
        //Like, seriously, who even declares a boolean variable like that?
        //While it's usually possible to use "new NativeBool()", here it's
        //necessary to initialize it to "true", else the popup won't open.

        alwaysTrue = new NativeBool();
        alwaysTrue.modifyValue(true);

        //UI initialization stuff.

        String fontName = "PureProg 12.ttf";
        File fontFile = new File(fontName);
        if (!fontFile.exists()) {
            String fontPath = "fonts/" + fontName;
            InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
            assert fontInput != null;
            try {
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            } catch (IOException ignored) {
            }
        }

        NativeShort glyphRange = imGui.getIO().getFonts().getGlyphRangesForCyrillic();

        JImFont font = imGui.getIO().getFonts().addFontFromFile(fontName, (float) cellSize / 2, glyphRange);
        font.setDisplayOffsetX((float) cellSize / 25);
        font.setDisplayOffsetY((float) -cellSize / 25);

        imGui.setBackground(Color.BACKGROUND.getColor());

        imGui.initBeforeMainLoop();
    }

    JImGui getImGui() {
        return imGui;
    }

    Translation getTranslation() {
        return translation;
    }

    int getCellSize() {
        return cellSize;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    void setSetup(Setup setup) {
        this.setup = setup;
    }

    void setInfo(Setup infoSetup) {
        this.infoSetup = infoSetup;
    }

    void startAI(int depth) {
        resetFilename();
        ai = new AI(depth, board);
        ai.start();
    }

    void stopAI() {
        if (ai != null) {
            ai.stopEarly();
            ai.interrupt();
            board.updateMove(ai.bestMove());
            board.redo();
            ai = null;
        }
    }

    void enableInfoWindow() {
        if (setup != null) {
            this.infoSetup = setup;
        }
        this.showInfo = true;
    }

    boolean gameInfoExists() {
        return setup.gameInfoExists(translation);
    }

    private String getStatusString() {
        String status = board.getStatusString();
        if (status == null) {
            status = translation.get("status.turn", board.getTurnSide().getTranslationString());
        }
        return status;
    }

    void displayStatusText() {
        String status = getStatusString();
        if (ai != null) {
            if (ai.isAlive()) {
                String thoughts = ai.thoughts(translation);
                if (thoughts.isEmpty()) {
                    status = translation.get("status.ai_thinking.generic");
                } else {
                    status = translation.get("status.ai_thinking", ai.thoughts(translation));
                }
            } else { //this is probably the wrong place to put this but
                Move aiMove = ai.bestMove();
                if (aiMove != null) {
                    board.updateMove(aiMove);
                    board.redo();
                } else {
                    board.checkStatusConditions();
                }
                ai = null;
            }
        }
        imGui.text(status);
        imGui.text(translation.get("status.game", setup.getGameType()));
        if (filename != null) {
            if (saveMode) {
                imGui.text(translation.get("status.saved_as", filename));
            } else {
                imGui.text(translation.get("status.file", filename));
            }
        } else {
            imGui.text("");
        }
    }

    void displayStatusIndicator() {
        String status = getStatusString();
        CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, cellSize,
                board.getTurnSide().toColor(), -1);
    }

    void displayLogAndOrLogButton() {
        if (showLog) {
            board.displayLog(imGui, width - 2 * margin, height - board.getWindowHeight() - 3 * margin,
                    margin, board.getWindowHeight() + 2 * margin, width * 4 / cellSize);
            if (CellGraphics.display(imGui, "log_opened", translation.get("action.log.close"), cellSize, Color.WHITE, -1)) {
                filename = saveMode ? null : filename;
                showLog = false;
            }
        } else {
            if (CellGraphics.display(imGui, "log_closed", translation.get("action.log.open"), cellSize, Color.WHITE, -1)) {
                filename = saveMode ? null : filename;
                showLog = true;
            }
        }
    }

    boolean canUndo() {
        return (board != null) && (board.getPreviousBoard() != null);
    }

    boolean canRedo() {
        return (board != null) && (board.canRedo());
    }

    void undo() {
        if (!canUndo()) return;
        resetAI();
        resetFilenameIfGameWasSaved();
        board = board.getPreviousBoard();
        board.setTranslation(translation);
    }

    void undoAll() {
        if (!canUndo()) return;
        resetAI();
        resetFilenameIfGameWasSaved();
        board = board.getInitialBoard();
        board.setTranslation(translation);
    }

    void redo() {
        if (!canRedo()) return;
        resetAI();
        resetFilenameIfGameWasSaved();
        board.redo();
    }

    void redoAll() {
        if (!canRedo()) return;
        resetAI();
        resetFilenameIfGameWasSaved();
        board.redoAll();
    }

    void makeRandomMove() {
        resetAI();
        resetFilename();
        board.makeRandomMove();
    }

    void resetAI() {
        if (ai != null) {
            ai.stopEarly();
            ai.interrupt();
            ai = null;
        }
    }

    void resetBoard() {
        resetAI();
        resetFilename();
        board = setup.getBoard();
    }

    void resetFilename() {
        filename = null;
    }

    void resetFilenameIfGameWasSaved() {
        if (saveMode) resetFilename();
    }

    void removeBoard() {
        resetAI();
        filename = null;
        board = null;
        setup = null;
    }

    void lockInput() {
        lockInput = true;
    }

    void unlockInput() {
        lockInput = false;
    }

    public void stopExtraThreads() {
        if (fileChooser != null && fileChooser.isAlive()) {
            fileChooser.interrupt();
        }
        if (ai != null && ai.isAlive()) {
            ai.stopEarly();
            ai.interrupt();
        }
    }

    public void display() {
        int newWidth = (int) imGui.getPlatformWindowSizeX();
        int newHeight = (int) imGui.getPlatformWindowSizeY();

        //Do not update the widget positions if the window size is changing or it's minimized (size is 0x0).
        //Draw empty frames instead. (A dirty but viable solution to the Wandering Info Window bug.)
        while (newWidth != imGui.getPlatformWindowSizeX() || newHeight != imGui.getPlatformWindowSizeY() || newWidth == 0 || newHeight == 0) {
            newWidth = (int) imGui.getPlatformWindowSizeX();
            newHeight = (int) imGui.getPlatformWindowSizeY();
            windowLoaded = false;
            infoPosX = -1;
            infoPosY = -1;
            imGui.initNewFrame();
            imGui.render();
        }

        width = newWidth;
        height = newHeight;

        imGui.initNewFrame();

        if (!lockInput) {
            imGui.pushStyleColor(JImStyleColors.WindowBg, Color.BORDER.getColor());
            imGui.pushStyleColor(JImStyleColors.Button, Color.BUTTON.getColor());
            imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.BUTTON.getHoveredColor());
            imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.BUTTON.getActiveColor());
            if (setup == null || board == null) {
                menuWindow.display();
            }
            imGui.setWindowPos("Menu", margin, margin);
            displayLanguageWindow();
            JImGuiGen.popStyleColor(3);
            if (setup != null && board != null) {
                if (board.getTranslation() != translation) {
                    board.setTranslation(translation);
                }
                if (board.display(imGui, "Board", cellSize)) {
                    resetFilename();
                    resetAI();
                }
                imGui.setWindowPos("Board", margin, margin);
                statusWindow.display();
                if (board != null) {
                    if (board.getWindowHeight() > cellSize && board.getWindowWidth() > cellSize) {
                        imGui.setWindowPos("Game Status",
                                board.getWindowWidth() + 2 * margin,
                                board.getWindowHeight() - statusWindow.getWindowHeight() + margin);
                    }
                }
            }
            if (infoSetup != null && showInfo) {
                imGui.openPopup(translation.get("window.info"));
            }
        } else if (fileChooser != null) {
            try {
                waitForAndProcessFileChooserResults();
            } catch (IOException ignored) {
            }
        }
        if (showErrorMessage) {
            imGui.openPopup(translation.get("window.error"));
        }
        displayInfoPopupIfNeeded();
        displayErrorPopupIfNeeded();

        imGui.render();
    }

    private void displayInfoPopupIfNeeded() {
        boolean prev = false, next = false;

        if (imGui.beginPopupModal(translation.get("window.info"), alwaysTrue, JImWindowFlags.NoMove | JImWindowFlags.AlwaysAutoResize)) {
            imGui.text(infoSetup.getGameType(translation));
            imGui.text(infoSetup.getGameInfo(translation));
            imGui.text("");
            imGui.sameLine((JImGuiGen.getWindowWidth()) / 2 - (float) cellSize * 4 / 3);
            if (imGui.button("<<")) {
                prev = true;
            }
            imGui.sameLine((JImGuiGen.getWindowWidth()) / 2 - (float) cellSize / 3);
            if (imGui.button(translation.get("action.ok"))) {
                JImGuiGen.closeCurrentPopup();
            }
            imGui.sameLine((JImGuiGen.getWindowWidth()) / 2 + (float) cellSize * 2 / 3);
            if (imGui.button(">>")) {
                next = true;
            }
            if (windowLoaded) {
                infoPosX = tweakCoordinate(infoPosX, (width - JImGuiGen.getWindowWidth()) / 2, speed);
                infoPosY = tweakCoordinate(infoPosY, (height - JImGuiGen.getWindowHeight()) / 2, speed);
                imGui.setWindowPos(translation.get("window.info"), infoPosX, infoPosY);
            } else if (infoPosX == -1 && infoPosY == -1) {
                infoPosX = (width - JImGuiGen.getWindowWidth()) / 2;
                infoPosY = (height - JImGuiGen.getWindowHeight()) / 2;
            } else {
                windowLoaded = true;
                infoPosX = (width - JImGuiGen.getWindowWidth()) / 2;
                infoPosY = (height - JImGuiGen.getWindowHeight()) / 2;
            }
            JImGuiGen.endPopup();
        }

        if (!imGui.isPopupOpen(translation.get("window.info")) && !prev && !next) {
            showInfo = false;
            windowLoaded = false;
            infoPosX = -1;
            infoPosY = -1;
        }

        if (infoSetup != null) {
            if (prev) {
                if (infoSetup.getPrevious() != null) {
                    infoSetup = infoSetup.getPrevious();
                }
            }

            if (next) {
                if (infoSetup.getNext() != null) {
                    infoSetup = infoSetup.getNext();
                }
            }
        }
    }

    private void displayErrorPopupIfNeeded() {
        if (imGui.beginPopupModal(translation.get("window.error"), alwaysTrue, JImWindowFlags.AlwaysAutoResize)) {
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

        if (!imGui.isPopupOpen(translation.get("window.error"))) {
            showErrorMessage = false;
            errorMessage = null;
        }
    }

    private void displayLanguageWindow() {
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
    }

    void enableFileChooser(boolean saveMode) {
        this.saveMode = saveMode;
        this.fileChooser = new FileChooser(saveMode);
        this.fileChooser.start();
    }

    //TODO: apply movement to "game info" (status) window

    private static float tweakCoordinate(float from, float to, float speed) {
        return from + (to - from) * speed / 1000;
    }

    private void waitForAndProcessFileChooserResults() throws IOException {
        imGui.setWindowPos("FileNotChosen", margin, margin);
        imGui.begin("FileNotChosen", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
        imGui.text(translation.get("status.choosing_file"));
        JImGuiGen.end();
        if (fileChooser.getStatus() != null) {
            if (fileChooser.getFilePath() != null) {
                if (saveMode) {
                    if (board != null && setup != null) {
                        String[] path = fileChooser.getFilePath().split("[/\\\\]");
                        filename = path[path.length - 1];
                        board.saveToPGN(fileChooser.getFilePath(), setup);
                    }
                } else if (new File(fileChooser.getFilePath()).length() > MAX_PGN_SIZE) {
                    showErrorMessage = true;
                    errorMessage = translation.get("error.pgn.large_file", MAX_PGN_SIZE,
                            new File(fileChooser.getFilePath()).length());
                } else {
                    byte[] contents = Files.readAllBytes(Paths.get(fileChooser.getFilePath()));
                    String pgn = new String(contents, StandardCharsets.UTF_8);
                    Board newBoard = Board.getGameFromPGN(pgn);
                    if (newBoard.isErroneous()) {
                        showErrorMessage = true;
                        errorMessage = newBoard.getErrorMessage(translation);
                    } else {
                        Setup newSetup = Setup.getSetupFromPGN(pgn);
                        String[] path = fileChooser.getFilePath().split("[/\\\\]");
                        filename = path[path.length - 1];
                        setup = newSetup;
                        board = newBoard;
                    }
                }
            }
            unlockInput();
        }
    }
}
