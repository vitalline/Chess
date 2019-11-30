package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.LevellingData;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.ForcedXPRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.text.Translation;

public class InvincibleForcedPiece extends LevellingForcedPiece {

    public InvincibleForcedPiece(Side side, MovementType movementType) {
        super(side, movementType, LevellingData.RES);
        baseType = PieceBaseType.INVINCIBLE_FORCED_PIECE;
    }

    @Override
    public String getLabel(Translation translation) {
        String label = super.getLabel(translation).split("\n")[0];
        label += '\n' + translation.get("label_invincible");
        return label;
    }

    @Override
    protected int getResistanceLevel() {
        return ForcedXPRules.getInvincibleResistanceLevel();
    }

    @Override
    protected int getPowerLevel() {
        return 1;
    }
}
