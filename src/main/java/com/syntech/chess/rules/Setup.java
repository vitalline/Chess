package com.syntech.chess.rules;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.CyclicBoard;
import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.jetbrains.annotations.NotNull;

public enum Setup {
    CHESS("chess", StartingPositions.chess),
    CYCLIC_CHESS("chess.cyclic", StartingPositions.chess),
    FORCED_CHESS("chess.forced", StartingPositions.forcedChess),
    MODEST_FORCED_CHESS("chess.forced.modest", StartingPositions.modestForcedChess),
    FORCE_MAJOR_CHESS("chess.forced.major", StartingPositions.forceMajorChess),
    FORCE_MINOR_CHESS("chess.forced.minor", StartingPositions.forceMinorChess),
    CYCLIC_MODEST_FORCED_CHESS("chess.forced.modest.cyclic", StartingPositions.modestForcedChess),
    CHESS_4x4("chess.4x4", StartingPositions.chess4x4),
    SNIPER_FORCED_CHESS("chess.forced.sniper", StartingPositions.sniperChess),
    MODEST_SNIPER_FORCED_CHESS("chess.forced.sniper.modest", StartingPositions.modestSniperChess),
    RESERVE_FORCED_CHESS("chess.forced.reserve", StartingPositions.reserveChess),
    MODEST_RESERVE_FORCED_CHESS("chess.forced.reserve.modest", StartingPositions.modestReserveChess),
    PEGASI_RESERVE_FORCED_CHESS("chess.forced.pegasi_reserve", StartingPositions.pegasiReserveChess),
    SNIPER_RESERVE_FORCED_CHESS("chess.forced.sniper.reserve", StartingPositions.sniperReserveChess),
    SNIPER_PEGASI_RESERVE_FORCED_CHESS("chess.forced.sniper.pegasi_reserve", StartingPositions.sniperPegasiReserveChess),
    INVOLUTION_FORCED_CHESS("chess.forced.down", StartingPositions.involutionForcedChess),
    MMORPG_FORCED_CHESS("chess.forced.up", StartingPositions.mmoRPGForcedChess(LevellingData.UP)),
    INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN)),
    RESISTANCE_MMORPG_FORCED_CHESS("chess.forced.up.res", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES)),
    RESISTANCE_POWER_MMORPG_FORCED_CHESS("chess.forced.up.res.pow", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES_POW)),
    RESISTANCE_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.res", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES)),
    RESISTANCE_POWER_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.res.pow", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES_POW)),
    FORCED_INVOLUTION_FORCED_CHESS("chess.forced.down.telefrag", StartingPositions.forcedInvolutionForcedChess),
    FORCED_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.telefrag", StartingPositions.mmoRPGForcedChess(LevellingData.FORCE_UP_DOWN)),
    CLONING_FORCED_CHESS("chess.forced.cloning", StartingPositions.cloningForcedChess),
    CYCLIC_CHESS_8X12("chess.cyclic.8x12", StartingPositions.chess8x12),
    CYCLIC_CHESS_8X16("chess.cyclic.8x16", StartingPositions.chess8x16);

    private final String gameType;
    private final Piece[][] pieces;

    Setup(String gameType, Piece[][] pieces) {
        this.gameType = gameType;
        this.pieces = pieces;
    }

    @NotNull
    public String getGameType() {
        return gameType + ".name";
    }

    @NotNull
    public String getGameType(@NotNull Translation translation) {
        return translation.get(gameType + ".name");
    }

    @NotNull
    public String getGameInfo(@NotNull Translation translation) {
        return translation.get(gameType + ".info");
    }

    public boolean gameInfoExists(@NotNull Translation translation) {
        return translation.exists(gameType + ".info");
    }

    @NotNull
    public Board getBoard() {
        if (gameType.contains("cyclic")) {
            return new CyclicBoard(pieces, true, true);
        } else {
            return new Board(pieces, true, true);
        }
    }

    public Board boardButton(@NotNull JImGui imGui, @NotNull Translation translation) {
        Board board = null;
        if (imGui.button(translation.get(gameType + ".short"))) {
            board = getBoard();
        }
        displayTooltip(imGui, translation);
        return board;
    }

    private void displayTooltip(@NotNull JImGui imGui, Translation translation) {
        if (imGui.isItemHovered()) {
            JImGuiGen.beginTooltip();
            imGui.text(getGameType(translation));
            JImGuiGen.endTooltip();
        }
    }
}
