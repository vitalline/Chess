package com.syntech.chess.logic;

public enum LevellingData {
    NONE(false, false, false, false, false, false),
    UP(true, false, false, false, false, false),
    DOWN(false, true, false, false, false, false),
    RES(false, false, true, false, false, false),
    UP_DOWN(true, true, false, false, false, false),
    UP_RES(true, false, true, false, false, false),
    UP_RES_POW(true, false, true, true, false, false),
    UP_DOWN_RES(true, true, true, false, false, false),
    UP_DOWN_RES_POW(true, true, true, true, false, false),
    FORCE_DOWN(false, true, false, false, false, true),
    FORCE_UP_DOWN(true, true, false, false, false, true),
    UP_HP(true, false, false, false, true, false),
    UP_DOWN_RES_HP(true, true, true, false, true, false);

    private final boolean canLevelUp, canLevelDown, hasResistance, hasPower, hasHP, forceInvolution;

    LevellingData(boolean canLevelUp, boolean canLevelDown, boolean hasResistance, boolean hasPower, boolean hasHP, boolean forceInvolution) {
        this.canLevelUp = canLevelUp;
        this.canLevelDown = canLevelDown;
        this.hasResistance = hasResistance;
        this.hasPower = hasPower;
        this.hasHP = hasHP;
        this.forceInvolution = forceInvolution;
    }

    public boolean canLevelUp() {
        return canLevelUp;
    }

    public boolean canLevelDown() {
        return canLevelDown;
    }

    public boolean hasResistance() {
        return hasResistance;
    }

    public boolean hasPower() {
        return hasPower;
    }

    public boolean hasHP() {
        return hasHP;
    }

    public boolean forceInvolution() {
        return forceInvolution;
    }
}
