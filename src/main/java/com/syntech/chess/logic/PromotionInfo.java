package com.syntech.chess.logic;

public class PromotionInfo implements Cloneable {
    private int promotionRow;
    private PieceType[] possiblePromotionPieces;

    public PromotionInfo(int promotionRow, PieceType... pieces) {
        this.promotionRow = promotionRow;
        this.possiblePromotionPieces = pieces;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        PromotionInfo clone = (PromotionInfo) super.clone();
        clone.possiblePromotionPieces = this.possiblePromotionPieces.clone();
        return clone;
    }

    public PieceType[] getPromotionTypes() {
        return possiblePromotionPieces;
    }

    public boolean canBePromoted(int row) {
        return (row == promotionRow - 1);
    }
}
