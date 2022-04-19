package com.syntech.chess.ui;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.rules.Setup;
import com.syntech.chess.text.Translation;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class MenuWindow {
    private final BaseUI baseUI;
    private int menuPage = 0;
    private static final int MENU_BUTTON_AMOUNT = 16;

    public MenuWindow(BaseUI baseUI) {
        this.baseUI = baseUI;
    }

    public void display() {
        Translation translation = baseUI.getTranslation();
        int cellSize = baseUI.getCellSize();

        ImGui.begin(BaseUI.MENU_WINDOW_NAME, new ImBoolean(), ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.AlwaysAutoResize);

        for (int i = menuPage * MENU_BUTTON_AMOUNT; i < (menuPage + 1) * MENU_BUTTON_AMOUNT && i < Setup.values().length; i++) {
            if (Setup.values()[i].isValid()) {
                Board newBoard = Setup.values()[i].boardButton(translation);
                if (newBoard != null) {
                    baseUI.setSetup(Setup.values()[i]);
                    baseUI.setBoard(newBoard);
                }
            }
        }

        if (menuPage > 0) {
            if (CellGraphics.display("left", translation.get("action.previous"), cellSize, Color.WHITE, -2)) {
                --menuPage;
            }
        } else {
            CellGraphics.display("left", translation.get("action.previous"), cellSize, Color.NONE, -2);
        }

        ImGui.sameLine();

        if (menuPage < (Setup.values().length - 2) / MENU_BUTTON_AMOUNT) {
            if (CellGraphics.display("right", translation.get("action.next"), cellSize, Color.WHITE, -2)) {
                ++menuPage;
            }
        } else {
            CellGraphics.display("right", translation.get("action.next"), cellSize, Color.NONE, -2);
        }

        ImGui.sameLine();

        if (CellGraphics.display("info", translation.get("action.info"), cellSize, Color.WHITE, -1)) {
            baseUI.setInfo(Setup.CREDITS);
            baseUI.enableInfoWindow();
        }

        ImGui.sameLine();

        if (CellGraphics.display("load", translation.get("action.load"), cellSize, Color.WHITE, -1)) {
            baseUI.lockInput();
            baseUI.enableFileChooser(false);
        }

        ImGui.end();
    }
}
