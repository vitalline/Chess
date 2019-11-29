package com.syntech.chess.rules;

public abstract class SpecialFirstMoveType extends MovementType {

    private boolean hasNotMoved = true;

    protected SpecialFirstMoveType() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SpecialFirstMoveType type = (SpecialFirstMoveType) super.clone();
        type.hasNotMoved = hasNotMoved;
        return type;
    }

    public SpecialFirstMoveType(MovementType movementType) {
        if (movementType instanceof SpecialFirstMoveType) {
            this.hasNotMoved = ((SpecialFirstMoveType) movementType).hasNotMoved;
        }
    }

    public boolean hasNotMoved() {
        return hasNotMoved;
    }

    public void move() {
        hasNotMoved = false;
    }
}
