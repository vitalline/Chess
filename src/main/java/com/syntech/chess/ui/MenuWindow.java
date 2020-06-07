package com.syntech.chess.ui;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;

public class MenuWindow {
    private Base base;
    private int menuPage = 0;
    private static final int MENU_BUTTON_AMOUNT = 15;

    public MenuWindow(Base base) {
        this.base = base;
    }

    public void display() {
        JImGui imGui = base.getImGui();
        Translation translation = base.getTranslation();
        int cellSize = base.getCellSize();

        imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);

        for (int i = menuPage * MENU_BUTTON_AMOUNT; i < (menuPage + 1) * MENU_BUTTON_AMOUNT && i < Setup.values().length; i++) {
            if (!Setup.values()[i].isEmpty()) {
                Board newBoard = Setup.values()[i].boardButton(imGui, translation);
                if (newBoard != null) {
                    base.setSetup(Setup.values()[i]);
                    base.setBoard(newBoard);
                }
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

        if (menuPage < Setup.values().length / MENU_BUTTON_AMOUNT) {
            if (CellGraphics.display(imGui, "right", translation.get("action.next"), cellSize, Color.WHITE, -2)) {
                ++menuPage;
            }
        } else {
            CellGraphics.display(imGui, "right", translation.get("action.next"), cellSize, Color.NONE, -2);
        }

        imGui.sameLine();

        if (CellGraphics.display(imGui, "info", translation.get("action.info"), cellSize, Color.WHITE, -1)) {
            base.setInfo(Setup.CREDITS);
            base.enableInfoWindow();
        }

        imGui.sameLine();

        if (CellGraphics.display(imGui, "load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
            base.lockInput();
            base.enableFileChooser(false);
        }

        JImGuiGen.end();
    }
}
