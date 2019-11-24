package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.StartingPos;
import org.ice1000.jimgui.JImGui;
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
        try (JImGui imGui = new JImGui(540, 720, "Chess")) {
            CellGraphics.initialize();
            Board board = null;
            File fontFile = new File("PureProg 12.ttf");
            if (!fontFile.exists()) {
                String fontPath = "fonts/PureProg 12.ttf";
                InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
                assert fontInput != null;
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            }
            imGui.getIO().getFonts().addFontFromFile("PureProg 12.ttf", 24);
            imGui.initBeforeMainLoop();
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();
                imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                if (imGui.button("Chess")) {
                    board = new Board(StartingPos.chess, true);
                }
                if (imGui.button("Forced Chess")) {
                    board = new Board(StartingPos.forcedChess, true);
                }
                if (imGui.button("FAForcedChess")) {
                    board = new Board(StartingPos.forcedChessFA, true);
                }
                imGui.end();
                if (board != null) {
                    board.display(imGui, "Board", size);
                    imGui.begin("Turn Indicator", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                    String status = board.getStatusString();
                    if (status == null) {
                        status = board.getTurnSide().getProperName() + "'s Turn";
                    }
                    CellGraphics.display(imGui, Side.NONE, PieceType.NONE, status, size, board.getTurnSide().toColor(), 0);
                    imGui.end();
                }
                imGui.render();
            }
        }
    }

}