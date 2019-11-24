package com.syntech.chess;

import com.syntech.chess.graphic.Cell;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.StartingPos;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JniLoader;

import java.io.IOException;

public class Main {
    public static void main(String... args) throws IOException {
        JniLoader.load();
        final int size = 50;
        try (JImGui imGui = new JImGui("Chess")) {
            Cell.initialize();
            Board board = new Board(StartingPos.forcedChessFA, true);
            imGui.initBeforeMainLoop();
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();
                imGui.begin("Board", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                board.display(imGui, size);
                imGui.end();
                imGui.begin("Turn Indicator", new NativeBool(), JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                Cell.display(imGui, Side.NONE, PieceType.NONE, size, board.getTurnSide().toColor(), 0);
                imGui.render();
            }
        }
    }

}