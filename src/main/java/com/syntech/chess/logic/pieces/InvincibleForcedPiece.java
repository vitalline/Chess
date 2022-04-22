package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.ForcedXPRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;

public class InvincibleForcedPiece extends LevellingForcedPiece {

    public InvincibleForcedPiece(Side side, MovementType movementType) {
        super(side, movementType, LevellingData.RES);
    }

    @Override
    public String getLabel(@NotNull Translation translation) {
        String label = super.getLabel(translation).split("\n")[0];
        label += '\n' + translation.get("label.invincible");
        return label;
    }

    @Override
    public int getResistanceLevel() {
        return ForcedXPRules.getInvincibleResistanceLevel();
    }

    @Override
    public int getPowerLevel() {
        return 1;
    }
}
