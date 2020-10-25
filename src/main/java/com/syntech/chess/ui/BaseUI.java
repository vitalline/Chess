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

public class BaseUI {
    private static final int MAX_PGN_SIZE = 10240;
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
    //private NativeBool alwaysTrue; //This is needed for the popups to work. Don't ask.

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
            board.displayLog(width - 2 * margin, height - board.getWindowHeight() - 3 * margin,
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
        infoPosX = -1;
        infoPosY = -1;
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
            ImGui.setWindowPos("Menu", margin, margin);
            displayLanguageWindow();
            ImGui.popStyleColor(3);
            if (setup != null && board != null) {
                if (board.getTranslation() != translation) {
                    board.setTranslation(translation);
                }
                if (board.display("Board", cellSize)) {
                    resetFilename();
                    resetAI();
                }
                ImGui.setWindowPos("Board", margin, margin);
                statusWindow.display();
                if (board != null) {
                    if (board.getWindowHeight() > cellSize && board.getWindowWidth() > cellSize) {
                        ImGui.setWindowPos("Game Status",
                                board.getWindowWidth() + 2 * margin,
                                board.getWindowHeight() - statusWindow.getWindowHeight() + margin);
                    }
                }
            }
            if (infoSetup != null && showInfo) {
                ImGui.openPopup(translation.get("window.info"));
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
        displayInfoPopupIfNeeded();
        displayErrorPopupIfNeeded();

        ImGui.render();
    }

    private void displayInfoPopupIfNeeded() {
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
                infoPosX = tweakCoordinate(infoPosX, (width - ImGui.getWindowWidth()) / 2, speed);
                infoPosY = tweakCoordinate(infoPosY, (height - ImGui.getWindowHeight()) / 2, speed);
                ImGui.setWindowPos(translation.get("window.info"), infoPosX, infoPosY);
            } else if (infoPosX == -1 && infoPosY == -1) {
                infoPosX = (width - ImGui.getWindowWidth()) / 2;
                infoPosY = (height - ImGui.getWindowHeight()) / 2;
            } else {
                windowLoaded = true;
                infoPosX = (width - ImGui.getWindowWidth()) / 2;
                infoPosY = (height - ImGui.getWindowHeight()) / 2;
            }
            ImGui.endPopup();
        }

        if (!ImGui.isPopupOpen(translation.get("window.info")) && !prev && !next) {
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
