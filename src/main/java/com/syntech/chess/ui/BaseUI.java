package com.syntech.chess.ui;

import com.syntech.chess.ai.AI;
import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.graphic.FileChooser;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BaseUI {
    private static final int MAX_PGN_SIZE = 10240;
    static final String BOARD_WINDOW_NAME = "Board";
    static final String LOG_WINDOW_NAME = "Turn Info";
    static final String MENU_WINDOW_NAME = "Menu";
    static final String STATUS_WINDOW_NAME = "Game Status";
    private int width, height;
    private final int cellSize = 50, margin = 10, speed = 65;
    private boolean windowLoaded = false;
    private float posX = -1, posY = -1;
    private Translation translation = Translation.EN_US;
    private Board board = null;
    private Setup setup = null, infoSetup = null;
    private boolean showLog = false, showInfo = false, showSettings = false, showErrorMessage = false, saveMode = false, lockInput = false;
    private String filename = null;
    private String errorMessage = null;
    private FileChooser fileChooser = null;
    private AI ai = null;
    private int aiTurns = 3;
    private int aiSeconds = 5;
    private AIMode aiMode = AIMode.TURNS;
    private MenuWindow menuWindow = new MenuWindow(this);
    private StatusWindow statusWindow = new StatusWindow(this);

    enum AIMode {
        NONE,
        TURNS,
        SECONDS
    }

    public BaseUI(int width, int height) {
        this.width = width;
        this.height = height;
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

    private void startRegularAI(int depth) {
        resetFilename();
        ai = new AI(depth, board);
        ai.start();
    }

    private void startTimedAI(int seconds) {
        resetFilename();
        ai = new AI(25, board);
        ai.start();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.schedule(() -> {
            if (ai != null) {
                ai.stopEarly();
                ai.interrupt();
                board.updateMove(ai.bestMove());
                board.redo();
                ai = null;
            }
        }, seconds, TimeUnit.SECONDS);
        executor.shutdown();
    }

    void startAI() {
        switch (aiMode) {
            case TURNS:
                startRegularAI(aiTurns);
                break;
            case SECONDS:
                startTimedAI(aiSeconds);
                break;
        }
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

    void enableSettingsWindow() {
        if (ai != null) {
            ai.stopEarly();
            ai.interrupt();
            ai = null;
        }
        this.showSettings = true;
    }

    boolean gameInfoExists() {
        return setup.gameInfoExists(translation);
    }

    @NotNull
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
        ImGui.text(status);
        ImGui.text(translation.get("status.game", setup.getGameType()));
        if (filename != null) {
            if (saveMode) {
                ImGui.text(translation.get("status.saved_as", filename));
            } else {
                ImGui.text(translation.get("status.file", filename));
            }
        } else {
            ImGui.text("");
        }
    }

    void displayStatusIndicator() {
        String status = getStatusString();
        CellGraphics.display(board.getStatusSide(), board.getStatusPiece(), status, cellSize,
                board.getTurnSide().toColor(), -1);
    }

    void displayLogAndOrLogButton() {
        if (showLog) {
            displayLog(board, width - 2 * margin, height - board.getWindowHeight() - 3 * margin,
                    margin, board.getWindowHeight() + 2 * margin, width * 4 / cellSize);
            if (CellGraphics.display("log_opened", translation.get("action.log.close"), cellSize, Color.WHITE, -1)) {
                filename = saveMode ? null : filename;
                showLog = false;
            }
        } else {
            if (CellGraphics.display("log_closed", translation.get("action.log.open"), cellSize, Color.WHITE, -1)) {
                filename = saveMode ? null : filename;
                showLog = true;
            }
        }
    }

    public void displayLog(@NotNull Board board, float width, float height, float posX, float posY, int characterWidth) {
        ImGui.setWindowSize(LOG_WINDOW_NAME, width, height);
        ImGui.setWindowPos(LOG_WINDOW_NAME, posX, posY);
        ImGui.begin(LOG_WINDOW_NAME, new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.getTurn(); i++) {
            String move = board.getMoveLog().get(i).toNotation(translation);
            String paddedMove;
            if (i % 2 == 0) {
                move = String.format("%d. ", i / 2 + 1) + move;
            }
            paddedMove = move;
            if (i != 0) {
                if (i % 2 == 0) {
                    paddedMove = "   " + paddedMove;
                } else {
                    paddedMove = " " + paddedMove;
                }
            }
            if (sb.length() - sb.lastIndexOf("\n") + paddedMove.length() > characterWidth) {
                sb.append("\n");
                sb.append(move);
            } else {
                sb.append(paddedMove);
            }
        }
        String result = board.getResultString();
        if (result.length() > 1) {
            if (board.getTurn() % 2 == 0) {
                result = "   " + result;
            } else {
                result = " " + result;
            }
            if (sb.length() - sb.lastIndexOf("\n") + result.length() > characterWidth) {
                sb.append("\n");
                sb.append(board.getResultString());
            } else {
                sb.append(result);
            }
        }
        ImGui.text(sb.toString());
        ImGui.end();
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

    private void resetInfoPopupPosition() {
        windowLoaded = false;
        posX = -1;
        posY = -1;
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

    public void render() {

        int newWidth = (int) ImGui.getMainViewport().getSizeX();
        int newHeight = (int) ImGui.getMainViewport().getSizeY();

        //Reset the info window position if the window is being resized or minimized (size is 0x0).
        if (newWidth != ImGui.getMainViewport().getSizeX() || newHeight != ImGui.getMainViewport().getSizeY() || newWidth == 0 || newHeight == 0) {
            resetInfoPopupPosition();
        }

        width = newWidth;
        height = newHeight;

        if (!lockInput) {
            ImGui.pushStyleColor(ImGuiCol.WindowBg, Color.BORDER.getColor());
            ImGui.pushStyleColor(ImGuiCol.Button, Color.BUTTON.getColor());
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, Color.BUTTON.getHoveredColor());
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, Color.BUTTON.getActiveColor());
            if (setup == null || board == null) {
                menuWindow.display();
            }
            ImGui.setWindowPos(MENU_WINDOW_NAME, margin, margin);
            displayLanguageWindow();
            ImGui.popStyleColor(3);
            if (setup != null && board != null) {
                if (board.getTranslation() != translation) {
                    board.setTranslation(translation);
                }
                if (board.display(BOARD_WINDOW_NAME, cellSize)) {
                    resetFilename();
                    resetAI();
                }
                ImGui.setWindowPos(BOARD_WINDOW_NAME, margin, margin);
                statusWindow.display();
                if (board != null) {
                    if (board.getWindowWidth() > cellSize * 10 && board.getWindowHeight() < cellSize * 8) {
                        ImGui.setWindowPos(STATUS_WINDOW_NAME, margin, board.getWindowHeight() + 2 * margin);
                    } else {
                        ImGui.setWindowPos(STATUS_WINDOW_NAME,
                                board.getWindowWidth() + 2 * margin,
                                board.getWindowHeight() - statusWindow.getWindowHeight() + margin);
                    }
                }
            }
            if (infoSetup != null && showInfo) {
                ImGui.openPopup(translation.get("window.info"));
            }
            if (showSettings) {
                ImGui.openPopup(translation.get("window.settings"));
            }
            ImGui.popStyleColor();
        } else if (fileChooser != null) {
            try {
                waitForAndProcessFileChooserResults();
            } catch (IOException ignored) {
            }
        }
        if (showErrorMessage) {
            ImGui.openPopup(translation.get("window.error"));
        }
        if (showInfo) displayInfoPopup();
        if (showSettings) displaySettingsPopup();
        if (showErrorMessage) displayErrorPopup();

        ImGui.render();
    }

    private void displayInfoPopup() {
        boolean prev = false, next = false;

        if (ImGui.beginPopupModal(translation.get("window.info"), new ImBoolean(true), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.AlwaysAutoResize)) {
            ImGui.text(infoSetup.getGameType(translation));
            ImGui.text(infoSetup.getGameInfo(translation));
            ImGui.text("");
            ImGui.sameLine((ImGui.getWindowWidth()) / 2 - (float) cellSize * 4 / 3);
            if (ImGui.button("<<")) {
                prev = true;
            }
            ImGui.sameLine((ImGui.getWindowWidth()) / 2 - (float) cellSize / 3);
            if (ImGui.button(translation.get("action.ok"))) {
                ImGui.closeCurrentPopup();
            }
            ImGui.sameLine((ImGui.getWindowWidth()) / 2 + (float) cellSize * 2 / 3);
            if (ImGui.button(">>")) {
                next = true;
            }
            if (windowLoaded) {
                posX = tweakCoordinate(posX, (width - ImGui.getWindowWidth()) / 2, speed);
                posY = tweakCoordinate(posY, (height - ImGui.getWindowHeight()) / 2, speed);
                ImGui.setWindowPos(translation.get("window.info"), posX, posY);
            } else if (posX == -1 && posY == -1) {
                posX = (width - ImGui.getWindowWidth()) / 2;
                posY = (height - ImGui.getWindowHeight()) / 2;
            } else {
                windowLoaded = true;
                posX = (width - ImGui.getWindowWidth()) / 2;
                posY = (height - ImGui.getWindowHeight()) / 2;
            }
            ImGui.endPopup();
        }

        if (!ImGui.isPopupOpen(translation.get("window.info")) && !prev && !next) {
            showInfo = false;
            windowLoaded = false;
            posX = -1;
            posY = -1;
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

    private void displaySettingsPopup() {
        if (ImGui.beginPopupModal(translation.get("window.settings"), new ImBoolean(true), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.AlwaysAutoResize)) {

            int[] aiSettings = new int[1];

            if (CellGraphics.display("ai_turns", translation.get("settings.ai.mode.turns"), cellSize * 2,
                    (aiMode == AIMode.TURNS) ? Color.MOVE_WHITE : Color.WHITE, -1)) {
                aiMode = AIMode.TURNS;
            }

            ImGui.sameLine();

            if (CellGraphics.display("ai_seconds", translation.get("settings.ai.mode.seconds"), cellSize * 2,
                    (aiMode == AIMode.SECONDS) ? Color.MOVE_WHITE : Color.WHITE, -1)) {
                aiMode = AIMode.SECONDS;
            }

            ImGui.setNextItemWidth(ImGui.getWindowWidth() - ImGui.getStyle().getWindowPaddingX() * 2);

            switch (aiMode) {
                case TURNS:
                    aiSettings[0] = aiTurns;
                    if (ImGui.sliderInt("", aiSettings, 1, 5, translation.get("settings.ai.turns"))) {
                        aiTurns = aiSettings[0];
                    }
                    break;
                case SECONDS:
                    aiSettings[0] = aiSeconds;
                    if (ImGui.sliderInt("", aiSettings, 1, 10, translation.get("settings.ai.seconds"))) {
                        aiSeconds = aiSettings[0];
                    }
                    break;
            }

            ImGui.endPopup();
        }

        if (!ImGui.isPopupOpen(translation.get("window.settings"))) {
            showSettings = false;
        }
    }

    private void displayErrorPopup() {
        if (ImGui.beginPopupModal(translation.get("window.error"), new ImBoolean(true), ImGuiWindowFlags.AlwaysAutoResize)) {
            if (errorMessage != null) {
                ImGui.text(errorMessage);
            } else {
                ImGui.text(translation.get("error.generic"));
            }
            if (ImGui.button(translation.get("action.ok"))) {
                showErrorMessage = false;
                errorMessage = null;
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        if (!ImGui.isPopupOpen(translation.get("window.error"))) {
            showErrorMessage = false;
            errorMessage = null;
        }
    }

    private void displayLanguageWindow() {
        ImGui.begin("Language", new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.AlwaysAutoResize);
        for (Translation t : Translation.values()) {
            if (t.ordinal() > 0) {
                if (ImGui.button(t.get("language.short"))) {
                    translation = t;
                    if (board != null) {
                        board.setTranslation(translation);
                    }
                }
                if (ImGui.isItemHovered()) {
                    ImGui.beginTooltip();
                    ImGui.text(t.get("language.name"));
                    ImGui.endTooltip();
                }
            }
        }
        ImGui.setWindowPos("Language", width - ImGui.getWindowWidth() - margin, margin);
        ImGui.end();
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
        ImGui.setWindowPos("FileNotChosen", margin, margin);
        ImGui.begin("FileNotChosen", new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.AlwaysAutoResize);
        ImGui.text(translation.get("status.choosing_file"));
        ImGui.end();
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
