package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.rules.StartingPos;
import org.ice1000.jimgui.JImFont;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JniLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main {
    public static void main(String... args) throws IOException {
        JniLoader.load();
        final int size = 50;
        try (JImGui imGui = new JImGui(800, 600, "Chess")) {
            CellGraphics.initialize();
            Board board = null;
            File fontFile = new File("PureProg 12.ttf");
            if (!fontFile.exists()) {
                String fontPath = "fonts/PureProg 12.ttf";
                InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
                assert fontInput != null;
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            }
            JImFont font = imGui.getIO().getFonts().addFontFromFile("PureProg 12.ttf", (float) size / 2);
            font.setDisplayOffsetX((float) size / 25);
            font.setDisplayOffsetY((float) -size / 25);
            imGui.setBackground(Color.BACKGROUND.getColor());
            imGui.initBeforeMainLoop();
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();
                imGui.pushStyleColor(JImStyleColors.WindowBg, Color.BORDER.getColor());
                imGui.pushStyleColor(JImStyleColors.Button, Color.BUTTON.getColor());
                imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.BUTTON.getHoveredColor());
                imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.BUTTON.getActiveColor());
                imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                if (imGui.button("Chess")) {
                    board = new Board(StartingPos.chess, true);
                }
                if (imGui.button("Forced Chess")) {
                    board = new Board(StartingPos.forcedChess, true);
                }
                if (imGui.button("Modest Forced Chess")) {
                    board = new Board(StartingPos.modestForcedChess, true);
                }
                if (imGui.button("Force Major Chess")) {
                    board = new Board(StartingPos.forceMajorChess, true);
                }
                if (imGui.button("Sniper Chess")) {
                    board = new Board(StartingPos.sniperChess, true);
                }
                if (imGui.button("Modest Sniper Chess")) {
                    board = new Board(StartingPos.modestSniperChess, true);
                }
                if (imGui.button("Reserve Chess")) {
                    board = new Board(StartingPos.reserveChess, true);
                }
                if (imGui.button("Modest Reserve Chess")) {
                    board = new Board(StartingPos.modestReserveChess, true);
                }
                if (imGui.button("Sniper Reserve Chess")) {
                    board = new Board(StartingPos.sniperReserveChess, true);
                }
                if (imGui.button("FA Forced Chess")) {
                    board = new Board(StartingPos.forcedChessFA, true);
                }
                if (imGui.button("Cloning Forced Chess")) {
                    board = new Board(StartingPos.cloningForcedChess, true);
                }
                imGui.end();
                imGui.popStyleColor(3);
                if (board != null) {
                    board.display(imGui, "Board", size);
                    imGui.begin("Turn Indicator", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                    String status = board.getStatusString();
                    if (status == null) {
                        status = board.getTurnSide().getProperName() + "'s Turn";
                    }
                    CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, size, board.getTurnSide().toColor(), -1);
                    imGui.end();
                }
                imGui.popStyleColor();
                imGui.render();
            }
        }
    }
}