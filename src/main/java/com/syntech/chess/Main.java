package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.ui.Base;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoaderEx;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        JniLoaderEx.loadGlfw();
        int width = 1366, height = 768;
        try (JImGui imGui = new JImGui(width, height, "Chess")) {
            CellGraphics.initialize();

            Base base = new Base(imGui, width, height);

            while (!imGui.windowShouldClose()) {
                base.display();
            }

            base.stopExtraThreads();
        }
    }

}