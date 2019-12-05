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
    CYCLIC_CHESS("cyclic_chess", StartingPositions.chess),
    FORCED_CHESS("forced_chess", StartingPositions.forcedChess),
    MODEST_FORCED_CHESS("modest_forced_chess", StartingPositions.modestForcedChess),
    FORCE_MAJOR_CHESS("force_major_chess", StartingPositions.forceMajorChess),
    FORCE_MINOR_CHESS("force_minor_chess", StartingPositions.forceMinorChess),
    CYCLIC_MODEST_FORCED_CHESS("cyclic_modest_forced_chess", StartingPositions.modestForcedChess),
    CHESS_4x4("classical_chess_4x4", StartingPositions.chess4x4),
    SNIPER_FORCED_CHESS("sniper_forced_chess", StartingPositions.sniperChess),
    MODEST_SNIPER_FORCED_CHESS("modest_sniper_forced_chess", StartingPositions.modestSniperChess),
    RESERVE_FORCED_CHESS("reserve_forced_chess", StartingPositions.reserveChess),
    MODEST_RESERVE_FORCED_CHESS("modest_reserve_forced_chess", StartingPositions.modestReserveChess),
    PEGASI_RESERVE_FORCED_CHESS("pegasi_reserve_forced_chess", StartingPositions.pegasiReserveChess),
    SNIPER_RESERVE_FORCED_CHESS("sniper_reserve_forced_chess", StartingPositions.sniperReserveChess),
    SNIPER_PEGASI_RESERVE_FORCED_CHESS("sniper_pegasi_reserve_forced_chess", StartingPositions.sniperPegasiReserveChess),
    INVOLUTION_FORCED_CHESS("involution_forced_chess", StartingPositions.involutionForcedChess),
    MMORPG_FORCED_CHESS("mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP)),
    INVOLUTION_MMORPG_FORCED_CHESS("involution_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN)),
    RESISTANCE_MMORPG_FORCED_CHESS("resistance_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES)),
    RESISTANCE_POWER_MMORPG_FORCED_CHESS("resistance_power_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES_POW)),
    RESISTANCE_INVOLUTION_MMORPG_FORCED_CHESS("resistance_involution_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES)),
    RESISTANCE_POWER_INVOLUTION_MMORPG_FORCED_CHESS("resistance_power_involution_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES_POW)),
    FORCED_INVOLUTION_FORCED_CHESS("forced_involution_forced_chess", StartingPositions.forcedInvolutionForcedChess),
    FORCED_INVOLUTION_MMORPG_FORCED_CHESS("forced_involution_mmorpg_forced_chess", StartingPositions.mmoRPGForcedChess(LevellingData.FORCE_UP_DOWN)),
    CLONING_FORCED_CHESS("cloning_forced_chess", StartingPositions.cloningForcedChess),
    CYCLIC_CHESS_8X12("cyclic_chess_8x12", StartingPositions.chess8x12),
    CYCLIC_CHESS_8X16("cyclic_chess_8x16", StartingPositions.chess8x16);

    private final String gameType;
    private final Piece[][] pieces;

    Setup(String gameType, Piece[][] pieces) {
        this.gameType = gameType;
        this.pieces = pieces;
    }

    public String getGameType(Translation translation) {
        return translation.get(gameType);
    }

    public String getGameInfo(Translation translation) {
        return translation.get(gameType + "#info");
    }

    @NotNull
    public Board getBoard(Translation translation) {
        if (gameType.contains("cyclic")) {
            return new CyclicBoard(pieces, translation, true, true);
        } else {
            return new Board(pieces, translation, true, true);
        }
    }

    public Board boardButton(@NotNull JImGui imGui, @NotNull Translation translation) {
        Board board = null;
        if (imGui.button(translation.get(gameType + "#short"))) {
            board = getBoard(translation);
        }
        displayTooltip(imGui, translation);
        return board;
    }

    private void displayTooltip(@NotNull JImGui imGui, Translation translation) {
        if (imGui.isItemHovered()) {
            JImGuiGen.beginTooltip();
            imGui.text(translation.get(gameType));
            JImGuiGen.endTooltip();
        }
    }
}
