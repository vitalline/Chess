package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.rules.StartingPos;
import org.ice1000.jimgui.*;
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
        try (JImGui imGui = new JImGui(900, 720, "Chess")) {
            CellGraphics.initialize();
            Board board = null;
            String gameType = null;
            File fontFile = new File("PureProg 12.ttf");
            if (!fontFile.exists()) {
                String fontPath = "fonts/PureProg 12.ttf";
                InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
                assert fontInput != null;
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            }
            NativeShort glyphRange = imGui.getIO().getFonts().getGlyphRangesForCyrillic();
            JImFont font = imGui.getIO().getFonts().addFontFromFile("PureProg 12.ttf", (float) size / 2, glyphRange);
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
                imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                if (imGui.button("Regular Chess")) {
                    board = new Board(StartingPos.chess, true);
                    gameType = "Chess";
                }
                if (imGui.button("Forced Chess")) {
                    board = new Board(StartingPos.forcedChess, true);
                    gameType = "Forced Chess";
                }
                if (imGui.button("Modest Forced Chess")) {
                    board = new Board(StartingPos.modestForcedChess, true);
                    gameType = "Modest Forced Chess";
                }
                if (imGui.button("Force Major Chess")) {
                    board = new Board(StartingPos.forceMajorChess, true);
                    gameType = "Force Major Chess";
                }
                if (imGui.button("Sniper Forced Chess")) {
                    board = new Board(StartingPos.sniperChess, true);
                    gameType = "Sniper Forced Chess";
                }
                if (imGui.button("Modest Sniper Frc. Ch.")) {
                    board = new Board(StartingPos.modestSniperChess, true);
                    gameType = "Modest Sniper Forced Chess";
                }
                if (imGui.button("Reserve Forced Chess")) {
                    board = new Board(StartingPos.reserveChess, true);
                    gameType = "Reserve Forced Chess";
                }
                if (imGui.button("Modest Reserve Frc. Ch.")) {
                    board = new Board(StartingPos.modestReserveChess, true);
                    gameType = "Modest Reserve Forced Chess";
                }
                if (imGui.button("Sniper-Reserve Frc. Ch.")) {
                    board = new Board(StartingPos.sniperReserveChess, true);
                    gameType = "Sniper-Reserve Forced Chess";
                }
                if (imGui.button("Involution Forced Chess")) {
                    board = new Board(StartingPos.involutionForcedChess, true);
                    gameType = "Involution Forced Chess";
                }
                if (imGui.button("MMORPG Forced Chess")) {
                    board = new Board(StartingPos.mmoRPGForcedChess, true);
                    gameType = "MMORPG Forced Chess";
                }
                if (imGui.button("Inv.-MMORPG Forced Chess")) {
                    board = new Board(StartingPos.involutionMMORPGForcedChess, true);
                    gameType = "Involution-MMORPG Forced Chess";
                }
                if (imGui.button("Cloning Forced Chess")) {
                    board = new Board(StartingPos.cloningForcedChess, true);
                    gameType = "Cloning Forced Chess";
                }
                imGui.end();
                imGui.popStyleColor(3);
                if (board != null) {
                    board.display(imGui, "Board", size);
                    imGui.begin("Turn Indicator", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                    String status = board.getStatusString();
                    if (status == null) {
                        status = board.getTurnSide().getProperName() + "'s Turn";
                    }
                    CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, size, board.getTurnSide().toColor(), -1);
                    imGui.sameLine();
                    imGui.text(status + "\nGame: " + gameType);
                    imGui.end();
                }
                imGui.popStyleColor();
                imGui.render();
            }
        }
    }
}