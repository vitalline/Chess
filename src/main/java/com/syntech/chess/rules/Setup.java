package com.syntech.chess.rules;

import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.logic.boards.*;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.text.Translation;
import imgui.ImGui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public enum Setup {
    CHESS("chess", StartingPositions.chess),
    CYCLIC_CHESS("chess.cyclic", StartingPositions.chess),
    DOUBLE_TURN_CHESS("chess.double", StartingPositions.chess),
    FORCED_CHESS("chess.forced", StartingPositions.forcedChess),
    MODEST_FORCED_CHESS("chess.forced.modest", StartingPositions.modestForcedChess),
    FORCE_MAJOR_CHESS("chess.forced.major", StartingPositions.forceMajorChess),
    FORCE_MINOR_CHESS("chess.forced.minor", StartingPositions.forceMinorChess),
    DOUBLE_TURN_FORCED_CHESS("chess.forced.double", StartingPositions.forcedChess),
    CYCLIC_FORCED_CHESS("chess.forced.cyclic", StartingPositions.forcedChess),
    CYCLIC_MODEST_FORCED_CHESS("chess.forced.modest.cyclic", StartingPositions.modestForcedChess),
    CHESS_4x4("chess.4x4", StartingPositions.chess4x4),
    SNIPER_FORCED_CHESS("chess.forced.sniper", StartingPositions.sniperChess),
    MODEST_SNIPER_FORCED_CHESS("chess.forced.sniper.modest", StartingPositions.modestSniperChess),
    RESERVE_FORCED_CHESS("chess.forced.reserve", StartingPositions.reserveChess),
    MODEST_RESERVE_FORCED_CHESS("chess.forced.reserve.modest", StartingPositions.modestReserveChess),
    PEGASI_RESERVE_FORCED_CHESS("chess.forced.reserve.pegasi", StartingPositions.pegasiReserveChess),
    SNIPER_RESERVE_FORCED_CHESS("chess.forced.sniper.reserve", StartingPositions.sniperReserveChess),
    SNIPER_PEGASI_RESERVE_FORCED_CHESS("chess.forced.sniper.reserve.pegasi", StartingPositions.sniperPegasiReserveChess),
    INVOLUTION_FORCED_CHESS("chess.forced.down", StartingPositions.involutionForcedChess),
    MMORPG_FORCED_CHESS("chess.forced.up", StartingPositions.mmoRPGForcedChess(LevellingData.UP)),
    INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN)),
    RESISTANCE_MMORPG_FORCED_CHESS("chess.forced.up.res", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES)),
    RESISTANCE_POWER_MMORPG_FORCED_CHESS("chess.forced.up.res.pow", StartingPositions.mmoRPGForcedChess(LevellingData.UP_RES_POW)),
    RESISTANCE_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.res", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES)),
    RESISTANCE_POWER_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.res.pow", StartingPositions.mmoRPGForcedChess(LevellingData.UP_DOWN_RES_POW)),
    FORCED_INVOLUTION_FORCED_CHESS("chess.forced.down.telefrag", StartingPositions.forcedInvolutionForcedChess),
    FORCED_INVOLUTION_MMORPG_FORCED_CHESS("chess.forced.up.down.telefrag", StartingPositions.mmoRPGForcedChess(LevellingData.FORCE_UP_DOWN)),
    MMORPG_FORCED_CHESS_WITH_MOBS("chess.forced.up.mob", StartingPositions.mmoRPGForcedChessWithMobs(LevellingData.UP, false)),
    MMORPG_FORCED_CHESS_WITH_ARMED_MOBS("chess.forced.up.mob.armed", StartingPositions.mmoRPGForcedChessWithMobs(LevellingData.UP_HP, true)),
    RESISTANCE_INVOLUTION_MMORPG_FORCED_CHESS_WITH_ARMED_MOBS("chess.forced.up.down.res.mob.armed", StartingPositions.mmoRPGForcedChessWithMobs(LevellingData.UP_DOWN_RES_HP, true)),
    INVERSE_RESPAWN_MMORPG_FORCED_CHESS_WITH_ARMED_MOBS("chess.forced.up.mob.armed.respawn", StartingPositions.mmoRPGForcedChessWithMobs(LevellingData.UP_HP, true)),
    MOUNTAIN_FORCED_CHESS("chess.forced.mountain", StartingPositions.hillForcedChess),
    MOUNTAIN_RANGE_FORCED_CHESS("chess.forced.mountains", StartingPositions.hillForcedChess),
    CLONING_FORCED_CHESS("chess.forced.cloning", StartingPositions.cloningForcedChess),
    FORCED_CHESS_3D("chess.forced.3d", StartingPositions.forcedChess3D),
    MODEST_FORCED_CHESS_3D("chess.forced.3d.modest", StartingPositions.modestForcedChess3D),
    CYCLIC_CHESS_4x4("chess.4x4.cyclic", StartingPositions.chess4x4),
    DOUBLE_TURN_CHESS_4x4("chess.4x4.double", StartingPositions.chess4x4),
    CHESS_8X4("chess.8x4", StartingPositions.chess8x4),
    CYCLIC_CHESS_8X4("chess.cyclic.8x4", StartingPositions.chess8x4),
    FORCED_CHESS_8X4("chess.forced.8x4", StartingPositions.forcedChess8x4),
    CYCLIC_FORCED_CHESS_8X4("chess.forced.cyclic.8x4", StartingPositions.forcedChess8x4),
    FORCED_CHESS_8X8("chess.forced.8x8", StartingPositions.forcedChess8x8),
    CYCLIC_FORCED_CHESS_8X8("chess.forced.cyclic.8x8", StartingPositions.forcedChess8x8),
    CYCLIC_CHESS_8X12("chess.cyclic.8x12", StartingPositions.chess8x12),
    CYCLIC_CHESS_8X16("chess.cyclic.8x16", StartingPositions.chess8x16),
    CREDITS("credits", null);

    private final String gameType;
    private final Piece[][] pieces;

    Setup(String gameType, Piece[][] pieces) {
        this.gameType = gameType;
        this.pieces = pieces;
    }

    @Nullable
    public Setup getPrevious() {
        Setup[] values = values();
        for (int i = values.length - 1; i >= 0; i--) {
            if (values[i] == this) {
                if (i > 0) {
                    return values[i - 1];
                }
                return values[values.length - 1];
            }
        }
        return null;
    }

    @Nullable
    public Setup getNext() {
        Setup[] values = values();
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            if (values[i] == this) {
                if (i < valuesLength - 1) {
                    return values[i + 1];
                }
                return values[0];
            }
        }
        return null;
    }

    @NotNull
    public String getGameType(@NotNull Translation translation) {
        return translation.get(gameType + ".name");
    }

    @NotNull
    public String getGameTypeStatusString(@NotNull Translation translation) {
        String gameType = getGameType(translation);
        int startLength = translation.get("status.game").length() - 2;
        int spaceCount = 0;
        for (char ch : gameType.toCharArray()) {
            if (ch == ' ') spaceCount += 1; else break;
        }
        return translation.get("status.game", gameType.indent(Math.max(startLength - spaceCount, 0)).substring(startLength));
    }

    @NotNull
    public String getGameTypeTag() {
        return getGameType(Translation.EN_US).strip().replaceAll("\s*\n\s*", " ");
    }

    @NotNull
    public String getGameTypeString(@NotNull Translation translation) {
        return getGameType(translation).strip().replaceAll("\s*\n\s*", " ");
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
        if (isValid()) {
            boolean priority = gameType.contains("forced");
            if (gameType.contains("3d")) {
                return new Board3D(pieces, priority, 4, true, true);
            }
            if (gameType.contains("cyclic")) {
                return new CyclicBoard(pieces, priority, true, true);
            }
            if (gameType.contains("double")) {
                return new DoubleTurnBoard(pieces, priority, true, true);
            }
            if (this == MMORPG_FORCED_CHESS_WITH_MOBS) {
                return new MobBoard(pieces, priority, true, true);
            }
            if (this == MMORPG_FORCED_CHESS_WITH_ARMED_MOBS) {
                return new ArmedMobBoard(pieces, priority, true, true);
            }
            if (this == RESISTANCE_INVOLUTION_MMORPG_FORCED_CHESS_WITH_ARMED_MOBS) {
                return new IncreasingDamageArmedMobBoard(pieces, priority, true, true);
            }
            if (this == INVERSE_RESPAWN_MMORPG_FORCED_CHESS_WITH_ARMED_MOBS) {
                return new InverseRespawnArmedMobBoard(pieces, priority, true, true);
            }
            if (this == MOUNTAIN_FORCED_CHESS) {
                return new MountainBoard(pieces, priority, true, true);
            }
            if (this == MOUNTAIN_RANGE_FORCED_CHESS) {
                return new MountainRangeBoard(pieces, priority, true, true);
            }
            return new Board(pieces, priority, true, true);
        }
        return new Board("error.internal.setup_has_no_pieces");
    }

    public boolean isValid() {
        return pieces != null && pieces.length != 0 && pieces[0].length != 0;
    }

    public Board boardButton(@NotNull Translation translation) {
        Board board = null;
        if (ImGui.button(translation.get(gameType + ".short"))) {
            board = getBoard();
        }
        displayTooltip(translation);
        return board;
    }

    private void displayTooltip(Translation translation) {
        if (ImGui.isItemHovered()) {
            ImGui.beginTooltip();
            ImGui.text(getGameType(translation));
            ImGui.endTooltip();
        }
    }

    @Nullable
    public static Setup getSetupFromPGN(@NotNull String game) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(game.split("[\\[\\]\\r\\n]")));
        tokens.removeIf(String::isEmpty);
        for (String token : tokens) {
            if (token.startsWith("Variant ")) {
                ArrayList<String> varTokens = new ArrayList<>(Arrays.asList(token.split("(Variant |\")")));
                varTokens.removeIf(String::isEmpty);
                String variant = varTokens.get(0);
                for (Setup setup : Setup.values()) {
                    if (setup.getGameTypeTag().equals(variant)) {
                        return setup;
                    }
                }
                return null;
            }
        }
        return Setup.CHESS;
    }
}
