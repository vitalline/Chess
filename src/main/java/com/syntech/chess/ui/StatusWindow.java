package com.syntech.chess.ui;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;

public class StatusWindow {
    private Base base;
    private int windowHeight;

    public StatusWindow(Base base) {
        this.base = base;
    }

    int getWindowHeight() {
        return windowHeight;
    }

    public void display() {
        JImGui imGui = base.getImGui();
        Translation translation = base.getTranslation();
        int cellSize = base.getCellSize();

        imGui.begin("Game Status", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

        base.displayStatusText();

        if (base.canUndo()) {
            if (CellGraphics.display(imGui, "double_left", translation.get("action.undo_all"), cellSize, Color.WHITE, -1)) {
                base.undoAll();
            }
            imGui.sameLine();
            if (CellGraphics.display(imGui, "left", translation.get("action.undo"), cellSize, Color.WHITE, -1)) {
                base.undo();
            }
        } else {
            CellGraphics.display(imGui, "double_left", translation.get("action.undo_all"), cellSize, Color.NONE, -1);
            imGui.sameLine();
            CellGraphics.display(imGui, "left", translation.get("action.undo"), cellSize, Color.NONE, -1);
        }
        imGui.sameLine();
        if (base.canRedo()) {
            if (CellGraphics.display(imGui, "right", translation.get("action.redo"), cellSize, Color.WHITE, -1)) {
                base.redo();
            }
            imGui.sameLine();
            if (CellGraphics.display(imGui, "double_right", translation.get("action.redo_all"), cellSize, Color.WHITE, -1)) {
                base.redoAll();
            }
        } else {
            CellGraphics.display(imGui, "right", translation.get("action.redo"), cellSize, Color.NONE, -1);
            imGui.sameLine();
            CellGraphics.display(imGui, "double_right", translation.get("action.redo_all"), cellSize, Color.NONE, -1);
        }
        imGui.sameLine();
        if (base.gameInfoExists()) {
            if (CellGraphics.display(imGui, "info", translation.get("action.info"), cellSize, Color.WHITE, -1)) {
                base.resetFilenameIfGameWasSaved();
                base.enableInfoWindow();

            }
        } else {
            CellGraphics.display(imGui, "info", translation.get("action.info"), cellSize, Color.NONE, -1);
        }
        imGui.sameLine();
        if (CellGraphics.display(imGui, "load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
            base.resetAI();
            base.enableInfoWindow();
            base.lockInput();
            base.enableFileChooser(false);
        }
        imGui.sameLine();
        if (CellGraphics.display(imGui, "save", translation.get("action.save"), cellSize, Color.WHITE, -1)) {
            base.resetAI();
            base.lockInput();
            base.resetFilename();
            base.enableFileChooser(true);
        }

        base.displayStatusIndicator();
        imGui.sameLine();
        if (CellGraphics.display(imGui, "qmark", translation.get("action.random"), cellSize, Color.WHITE, -1)) {
            base.makeRandomMove();
        }
        imGui.sameLine();
        base.displayLogAndOrLogButton();
        imGui.sameLine();
        if (CellGraphics.display(imGui, "start", translation.get("action.ai.start"), cellSize, Color.MOVE_WHITE, -1)) {
            base.startAI(3);
        }
        imGui.sameLine();
        if (CellGraphics.display(imGui, "stop", translation.get("action.ai.stop"), cellSize, Color.MOVABLE_WHITE, -2)) {
            base.stopAI();
        }
        imGui.sameLine();
        if (CellGraphics.display(imGui, "restart", translation.get("action.restart"), cellSize, Color.CAPTURE_WHITE, -1)) {
            base.resetBoard();
        }
        imGui.sameLine();
        if (CellGraphics.display(imGui, "cross", translation.get("action.return"), cellSize, Color.CAPTURE_WHITE, -1)) {
            base.removeBoard();
        }

        windowHeight = (int) JImGuiGen.getWindowHeight();

        JImGuiGen.end();
    }
}
