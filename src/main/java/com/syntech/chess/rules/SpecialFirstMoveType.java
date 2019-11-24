package com.syntech.chess.rules;

import org.jetbrains.annotations.Contract;

public abstract class SpecialFirstMoveType extends MovementType {

    private boolean hasNotMoved = true;

    @Contract(pure = true)
    protected SpecialFirstMoveType() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SpecialFirstMoveType type = (SpecialFirstMoveType) super.clone();
        type.hasNotMoved = hasNotMoved;
        return type;
    }

    @Contract(pure = true)
    public SpecialFirstMoveType(MovementType movementType) {
        if (movementType instanceof SpecialFirstMoveType) {
            this.hasNotMoved = ((SpecialFirstMoveType) movementType).hasNotMoved;
        }
    }

    protected boolean hasNotMoved() {
        return hasNotMoved;
    }

    public void move() {
        hasNotMoved = false;
    }
}
