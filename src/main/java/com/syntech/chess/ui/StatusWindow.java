package com.syntech.chess.ui;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.text.Translation;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class StatusWindow {
    private final BaseUI baseUI;
    private int windowHeight;

    public StatusWindow(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    int getWindowHeight() {
        return windowHeight;
    }

    public void display() {
        Translation translation = baseUI.getTranslation();
        int cellSize = baseUI.getCellSize();

        ImGui.begin(BaseUI.STATUS_WINDOW_NAME, new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.AlwaysAutoResize);

        baseUI.displayStatusText();

        if (baseUI.canUndo()) {
            if (CellGraphics.display("double_left", translation.get("action.undo_all"), cellSize, Color.WHITE, -1)) {
                baseUI.undoAll();
            }
            ImGui.sameLine();
            if (CellGraphics.display("left", translation.get("action.undo"), cellSize, Color.WHITE, -1)) {
                baseUI.undo();
            }
        } else {
            CellGraphics.display("double_left", translation.get("action.undo_all"), cellSize, Color.NONE, -1);
            ImGui.sameLine();
            CellGraphics.display("left", translation.get("action.undo"), cellSize, Color.NONE, -1);
        }
        ImGui.sameLine();
        if (baseUI.canRedo()) {
            if (CellGraphics.display("right", translation.get("action.redo"), cellSize, Color.WHITE, -1)) {
                baseUI.redo();
            }
            ImGui.sameLine();
            if (CellGraphics.display("double_right", translation.get("action.redo_all"), cellSize, Color.WHITE, -1)) {
                baseUI.redoAll();
            }
        } else {
            CellGraphics.display("right", translation.get("action.redo"), cellSize, Color.NONE, -1);
            ImGui.sameLine();
            CellGraphics.display("double_right", translation.get("action.redo_all"), cellSize, Color.NONE, -1);
        }
        ImGui.sameLine();
        if (baseUI.gameInfoExists()) {
            if (CellGraphics.display("info", translation.get("action.info"), cellSize, Color.WHITE, -1)) {
                baseUI.resetFilenameIfGameWasSaved();
                baseUI.enableInfoWindow();
            }
        } else {
            CellGraphics.display("info", translation.get("action.info"), cellSize, Color.NONE, -1);
        }
        ImGui.sameLine();
        if (CellGraphics.display("load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
            baseUI.resetAI();
            baseUI.lockInput();
            baseUI.enableFileChooser(false);
        }
        ImGui.sameLine();
        if (CellGraphics.display("save", translation.get("action.save"), cellSize, Color.WHITE, -1)) {
            baseUI.resetAI();
            baseUI.lockInput();
            baseUI.resetFilename();
            baseUI.enableFileChooser(true);
        }

        //baseUI.displayStatusIndicator();
        //ImGui.sameLine();
        if (CellGraphics.display("qmark", translation.get("action.random"), cellSize, Color.WHITE, -1)) {
            baseUI.makeRandomMove();
        }
        ImGui.sameLine();
        baseUI.displayLogAndOrLogButton();
        ImGui.sameLine();
        if (CellGraphics.display("start", translation.get("action.ai.start"), cellSize, Color.MOVE_WHITE, -1)) {
            baseUI.startAI();
        }
        ImGui.sameLine();
        if (CellGraphics.display("stop", translation.get("action.ai.stop"), cellSize, Color.SELECTED_WHITE, -1)) {
            baseUI.stopAI();
        }
        ImGui.sameLine();
        if (CellGraphics.display("settings", translation.get("action.settings"), cellSize, Color.MOVABLE_WHITE, -1)) {
            baseUI.enableSettingsWindow();
        }
        ImGui.sameLine();
        if (CellGraphics.display("restart", translation.get("action.restart"), cellSize, Color.CAPTURE_WHITE, -1)) {
            baseUI.resetBoard();
        }
        ImGui.sameLine();
        if (CellGraphics.display("cross", translation.get("action.return"), cellSize, Color.CAPTURE_WHITE, -1)) {
            baseUI.removeBoard();
        }

        windowHeight = (int) ImGui.getWindowHeight();

        ImGui.end();
    }
}
