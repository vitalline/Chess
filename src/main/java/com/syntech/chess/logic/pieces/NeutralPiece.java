package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.text.Translation;

public class NeutralPiece extends Piece {

    public NeutralPiece(Side side, MovementType movementType) {
        super(side, movementType);
        baseType = PieceBaseType.NEUTRAL_PIECE;
    }

    @Override
    public String getLabel(Translation translation) {
        return getType().getProperName(translation);
    }

}
