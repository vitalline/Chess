package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.CyclicBoard;
import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.rules.StartingPositions;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JniLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main {

    private static Translation translation = Translation.EN_US;

    public static void main(String... args) throws IOException {
        JniLoader.load();
        final int width = 1300, height = 720, cellSize = 50, margin = 10;
        try (JImGui imGui = new JImGui(width, height, "Chess")) {
            CellGraphics.initialize();
            Board board = null;
            String gameType = null;
            int menuPage = 0;
            File fontFile = new File("PureProg 12.ttf");
            if (!fontFile.exists()) {
                String fontPath = "fonts/PureProg 12.ttf";
                InputStream fontInput = Main.class.getClassLoader().getResourceAsStream(fontPath);
                assert fontInput != null;
                Files.copy(fontInput, fontFile.getAbsoluteFile().toPath());
            }
            float boardX;
            NativeShort glyphRange = imGui.getIO().getFonts().getGlyphRangesForCyrillic();
            JImFont font = imGui.getIO().getFonts().addFontFromFile("PureProg 12.ttf", (float) cellSize / 2, glyphRange);
            font.setDisplayOffsetX((float) cellSize / 25);
            font.setDisplayOffsetY((float) -cellSize / 25);
            imGui.setBackground(Color.BACKGROUND.getColor());
            imGui.initBeforeMainLoop();
            imGui.setWindowPos("Menu", margin, margin);
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();
                imGui.pushStyleColor(JImStyleColors.WindowBg, Color.BORDER.getColor());
                imGui.pushStyleColor(JImStyleColors.Button, Color.BUTTON.getColor());
                imGui.pushStyleColor(JImStyleColors.ButtonHovered, Color.BUTTON.getHoveredColor());
                imGui.pushStyleColor(JImStyleColors.ButtonActive, Color.BUTTON.getActiveColor());
                imGui.begin("Menu", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                switch (menuPage) {
                    case 0:
                        if (imGui.button(translation.get("chess#short"))) {
                            board = new Board(StartingPositions.chess, translation, true);
                            gameType = "chess";
                        }
                        displayTooltip("chess", imGui);
                        if (imGui.button(translation.get("cyclic_chess#short"))) {
                            board = new CyclicBoard(StartingPositions.chess, translation, true);
                            gameType = "cyclic_chess";
                        }
                        displayTooltip("cyclic_chess", imGui);
                        if (imGui.button(translation.get("forced_chess#short"))) {
                            board = new Board(StartingPositions.forcedChess, translation, true);
                            gameType = "forced_chess";
                        }
                        displayTooltip("forced_chess", imGui);
                        if (imGui.button(translation.get("modest_forced_chess#short"))) {
                            board = new Board(StartingPositions.modestForcedChess, translation, true);
                            gameType = "modest_forced_chess";
                        }
                        displayTooltip("modest_forced_chess", imGui);
                        if (imGui.button(translation.get("force_major_chess#short"))) {
                            board = new Board(StartingPositions.forceMajorChess, translation, true);
                            gameType = "force_major_chess";
                        }
                        displayTooltip("force_major_chess", imGui);
                        if (imGui.button(translation.get("force_minor_chess#short"))) {
                            board = new Board(StartingPositions.forceMinorChess, translation, true);
                            gameType = "force_minor_chess";
                        }
                        displayTooltip("force_minor_chess", imGui);
                        if (imGui.button(translation.get("cyclic_modest_forced_chess#short"))) {
                            board = new CyclicBoard(StartingPositions.modestForcedChess, translation, true);
                            gameType = "cyclic_modest_forced_chess";
                        }
                        displayTooltip("cyclic_modest_forced_chess", imGui);
                        if (imGui.button(translation.get("classical_chess_4x4#short"))) {
                            board = new Board(StartingPositions.chess4x4, translation, true);
                            gameType = "classical_chess_4x4";
                        }
                        displayTooltip("classical_chess_4x4", imGui);
                        if (imGui.button(translation.get("sniper_forced_chess#short"))) {
                            board = new Board(StartingPositions.sniperChess, translation, true);
                            gameType = "sniper_forced_chess";
                        }
                        displayTooltip("sniper_forced_chess", imGui);
                        if (imGui.button(translation.get("modest_sniper_forced_chess#short"))) {
                            board = new Board(StartingPositions.modestSniperChess, translation, true);
                            gameType = "modest_sniper_forced_chess";
                        }
                        displayTooltip("modest_sniper_forced_chess", imGui);
                        if (imGui.button(translation.get("reserve_forced_chess#short"))) {
                            board = new Board(StartingPositions.reserveChess, translation, true);
                            gameType = "reserve_forced_chess";
                        }
                        displayTooltip("reserve_forced_chess", imGui);
                        if (imGui.button(translation.get("modest_reserve_forced_chess#short"))) {
                            board = new Board(StartingPositions.modestReserveChess, translation, true);
                            gameType = "modest_reserve_forced_chess";
                        }
                        displayTooltip("modest_reserve_forced_chess", imGui);
                        if (imGui.button(translation.get("pegasi_reserve_forced_chess#short"))) {
                            board = new Board(StartingPositions.pegasiReserveChess, translation, true);
                            gameType = "pegasi_reserve_forced_chess";
                        }
                        displayTooltip("pegasi_reserve_forced_chess", imGui);
                        if (imGui.button(translation.get("sniper_reserve_forced_chess#short"))) {
                            board = new Board(StartingPositions.sniperReserveChess, translation, true);
                            gameType = "sniper_reserve_forced_chess";
                        }
                        displayTooltip("sniper_reserve_forced_chess", imGui);
                        if (imGui.button(translation.get("sniper_pegasi_reserve_forced_chess#short"))) {
                            board = new Board(StartingPositions.sniperPegasiReserveChess, translation, true);
                            gameType = "sniper_pegasi_reserve_forced_chess";
                        }
                        displayTooltip("sniper_pegasi_reserve_forced_chess", imGui);
                        if (CellGraphics.display(imGui, "right", translation.get("action_next"), cellSize, Color.WHITE, -2)) {
                            ++menuPage;
                        }
                        break;
                    case 1:
                        if (imGui.button(translation.get("involution_forced_chess#short"))) {
                            board = new Board(StartingPositions.involutionForcedChess, translation, true);
                            gameType = "involution_forced_chess";
                        }
                        displayTooltip("involution_forced_chess", imGui);
                        if (imGui.button(translation.get("mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP), translation, true);
                            gameType = "mmorpg_forced_chess";
                        }
                        displayTooltip("mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("involution_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN), translation, true);
                            gameType = "involution_mmorpg_forced_chess";
                        }
                        displayTooltip("involution_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("resistance_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES), translation, true);
                            gameType = "resistance_mmorpg_forced_chess";
                        }
                        displayTooltip("resistance_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("resistance_power_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES_POW), translation, true);
                            gameType = "resistance_power_mmorpg_forced_chess";
                        }
                        displayTooltip("resistance_power_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("resistance_involution_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES), translation, true);
                            gameType = "resistance_involution_mmorpg_forced_chess";
                        }
                        displayTooltip("resistance_involution_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("resistance_power_involution_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES_POW), translation, true);
                            gameType = "resistance_power_involution_mmorpg_forced_chess";
                        }
                        displayTooltip("resistance_power_involution_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("forced_involution_forced_chess#short"))) {
                            board = new Board(StartingPositions.forcedInvolutionForcedChess, translation, true);
                            gameType = "forced_involution_forced_chess";
                        }
                        displayTooltip("forced_involution_forced_chess", imGui);
                        if (imGui.button(translation.get("forced_involution_mmorpg_forced_chess#short"))) {
                            board = new Board(StartingPositions.mmoRPGForcedChess(LevellingData.FORCE_UP_DOWN), translation, true);
                            gameType = "forced_involution_mmorpg_forced_chess";
                        }
                        displayTooltip("forced_involution_mmorpg_forced_chess", imGui);
                        if (imGui.button(translation.get("cloning_forced_chess#short"))) {
                            board = new Board(StartingPositions.cloningForcedChess, translation, true);
                            gameType = "cloning_forced_chess";
                        }
                        displayTooltip("cloning_forced_chess", imGui);
                        if (imGui.button(translation.get("cyclic_chess_8x12#short"))) {
                            board = new CyclicBoard(StartingPositions.chess8x12, translation, true);
                            gameType = "cyclic_chess_8x12";
                        }
                        displayTooltip("cyclic_chess_8x12", imGui);
                        if (imGui.button(translation.get("cyclic_chess_8x16#short"))) {
                            board = new CyclicBoard(StartingPositions.chess8x16, translation, true);
                            gameType = "cyclic_chess_8x16";
                        }
                        displayTooltip("cyclic_chess_8x16", imGui);
                        if (CellGraphics.display(imGui, "left", translation.get("action_previous"), cellSize, Color.WHITE, -2)) {
                            --menuPage;
                        }
                }
                boardX = JImGuiGen.getWindowWidth() + 2 * margin;
                JImGuiGen.end();
                imGui.begin("Language", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                for (Translation t : Translation.values()) {
                    if (t.ordinal() > 0) {
                        if (imGui.button(t.get("language#short"))) {
                            translation = t;
                            if (board != null) {
                                board.setTranslation(translation);
                            }
                        }
                        if (imGui.isItemHovered()) {
                            JImGuiGen.beginTooltip();
                            imGui.text(t.get("language"));
                            JImGuiGen.endTooltip();
                        }
                    }
                }
                imGui.setWindowPos("Language", width - JImGuiGen.getWindowWidth() - margin * 2.7f, margin);
                JImGuiGen.end();
                JImGuiGen.popStyleColor(3);
                if (board != null) {
                    imGui.setWindowPos("Board", boardX, margin);
                    board.display(imGui, "Board", cellSize);
                    imGui.setWindowPos("Turn Indicator", boardX, board.getWindowHeight() + 2 * margin);
                    imGui.begin("Turn Indicator", new NativeBool(), JImWindowFlags.NoMove | JImWindowFlags.NoTitleBar | JImWindowFlags.AlwaysAutoResize);
                    String status = board.getStatusString();
                    if (status == null) {
                        status = String.format(translation.get("status_turn"), board.getTurnSide().getProperName(translation));
                    }
                    CellGraphics.display(imGui, board.getStatusSide(), board.getStatusPiece(), status, cellSize, board.getTurnSide().toColor(), -1);
                    imGui.sameLine();
                    imGui.text(status + "\n" + String.format(translation.get("status_game"), translation.get(gameType)));
                    imGui.sameLine();
                    if (board.getPreviousBoard() != null) {
                        if (CellGraphics.display(imGui, "left", translation.get("action_undo"), cellSize, Color.WHITE, -1)) {
                            board = board.getPreviousBoard();
                            board.setTranslation(translation);
                        }
                    } else {
                        if (CellGraphics.display(imGui, "cross", translation.get("action_close"), cellSize, Color.CAPTURE_WHITE, -1)) {
                            board = null;
                        }

                    }
                    JImGuiGen.end();
                }
                imGui.popStyleColor();
                imGui.render();
            }
        }
    }

    private static void displayTooltip(String gameType, JImGui imGui) {
        if (imGui.isItemHovered()) {
            JImGuiGen.beginTooltip();
            imGui.text(translation.getRaw(gameType).replace('\n', ' '));
            JImGuiGen.endTooltip();
        }
    }
}