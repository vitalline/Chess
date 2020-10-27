package com.syntech.chess.logic;

public class PromotionInfo implements Cloneable {
    private int promotionCoordinate;
    private PieceType[] possiblePromotionPieces;

    public PromotionInfo(int promotionCoordinate, PieceType... pieces) {
        this.promotionCoordinate = promotionCoordinate;
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

    public boolean canBePromoted(int coordinate) {
        return (coordinate == promotionCoordinate - 1);
    }
}
