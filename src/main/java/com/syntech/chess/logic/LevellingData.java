package com.syntech.chess.logic;

public enum LevellingData {
    NONE(false, false, false, false),
    UP(true, false, false, false),
    DOWN(false, true, false, false),
    UP_DOWN(true, true, false, false),
    UP_RES(true, false, true, false),
    UP_RES_POW(true, false, true, true);

    private boolean canLevelUp, canLevelDown, hasResistance, hasPower;

    LevellingData(boolean canLevelUp, boolean canLevelDown, boolean hasResistance, boolean hasPower) {
        this.canLevelUp = canLevelUp;
        this.canLevelDown = canLevelDown;
        this.hasResistance = hasResistance;
        this.hasPower = hasPower;
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
}
