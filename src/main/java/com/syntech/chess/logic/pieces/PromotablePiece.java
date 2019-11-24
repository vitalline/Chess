package com.syntech.chess.logic.pieces;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.Side;

public abstract class PromotablePiece extends Piece {

    private int promotionRow;
    private Piece[] possiblePromotionPieces;

    public PromotablePiece(Side side, Integer promotionRow, Piece... pieces) {
        super(side);
        baseType = PieceBaseType.PROMOTABLE_PIECE;
        this.promotionRow = promotionRow;
        this.possiblePromotionPieces = pieces;
    }

    public Piece[] getPromotionPieces() {
        return possiblePromotionPieces;
    }

    @Override
    public boolean canBePromoted(Board board) {
        return (position.x == promotionRow - 1);
    }
}
