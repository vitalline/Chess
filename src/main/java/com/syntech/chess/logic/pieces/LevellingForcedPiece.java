package com.syntech.chess.logic.pieces;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.logic.*;
import com.syntech.chess.logic.boards.Board;
import com.syntech.chess.rules.ForcedXPRules;
import com.syntech.chess.rules.MovementType;
import com.syntech.chess.rules.SpecialFirstMoveType;
import com.syntech.chess.text.Translation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;

import static com.syntech.chess.logic.PieceFactory.cell;

public class LevellingForcedPiece extends ForcedPiece {
    private int xp, resistanceXP, powerXP, resistanceLevel, powerLevel;
    private double hp;
    private final double maxHP;
    private LevellingData levellingData;
    private Point initialPosition;
    private boolean hasCaptured = false, hasChecked = false, hasRespawned = false;

    public LevellingForcedPiece(Side side, MovementType movementType, LevellingData levellingData) {
        this(side, movementType, levellingData, 0, 0, 0, 0, 0, null);
    }

    public LevellingForcedPiece(Side side, MovementType movementType, LevellingData levellingData, int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel, Point initialPosition) {
        this(side, movementType, levellingData, null, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, initialPosition);
    }

    public LevellingForcedPiece(Side side, MovementType movementType, LevellingData levellingData, PromotionInfo promotionInfo, int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel, Point initialPosition) {
        this(side, movementType, levellingData, promotionInfo, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, 100, 100, initialPosition);
    }

    public LevellingForcedPiece(Side side, MovementType movementType, LevellingData levellingData, PromotionInfo promotionInfo, int xp, int resistanceXP, int resistanceLevel, int powerXP, int powerLevel, double hp, double maxHP, Point initialPosition) {
        super(side, movementType, promotionInfo);
        baseType = PieceBaseType.LEVELLING_FORCED_PIECE;
        this.levellingData = levellingData;
        this.xp = xp;
        this.resistanceXP = resistanceXP;
        this.resistanceLevel = resistanceLevel;
        this.powerXP = powerXP;
        this.powerLevel = powerLevel;
        this.hp = hp;
        this.maxHP = maxHP;
        this.initialPosition = initialPosition;
    }

    private int getMaxXP() {
        return ForcedXPRules.getNextLevelXP(getType());
    }

    private int getMaxResistanceXP() {
        return ForcedXPRules.getResistanceLevelXP(resistanceLevel);
    }

    private int getMaxPowerXP() {
        return ForcedXPRules.getPowerLevelXP(powerLevel);
    }

    public int getResistanceLevel() {
        return resistanceLevel;
    }

    public int getPowerLevel() {
        int currentPower = ForcedXPRules.RESISTED_PIECES.indexOf(getType()) + 1;
        return currentPower + powerLevel;
    }

    protected double getHP() {
        return hp;
    }

    public Point getInitialPosition() {
        return initialPosition;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LevellingForcedPiece clone = (LevellingForcedPiece) super.clone();
        clone.initialPosition = initialPosition;
        clone.levellingData = levellingData;
        return clone;
    }

    @Override
    public String getTextureID() {
        if (!canLevelUp()) {
            return super.getTextureID();
        }
        return String.format("%s%s%d", getSide().getTextureID(), getType().getTextureID(),
                CellGraphics.XP_BAR_STAGES * xp / getMaxXP());
    }

    @Override
    public String getLabel(@NotNull Translation translation) {
        String label = super.getLabel(translation);
        if (levellingData.hasHP()) {
            label += '\n' + translation.get("label.hp", hp, maxHP);
        }
        if (canLevelUp()) {
            label += '\n' + translation.get("label.xp", xp, getMaxXP());
            label += '\n' + translation.get("label.next_level", ForcedXPRules.getNextLevel(getType()).getTranslationString());
        }
        if (hasResistance()) {
            label += '\n' + translation.get("label.resistance_xp", resistanceXP, ForcedXPRules.getResistanceLevelXP(resistanceLevel));
        }
        if (levellingData.hasResistance()) {
            label += '\n' + translation.get("label.resistant_to", ForcedXPRules.getResistedPieceList(resistanceLevel, translation));
        }
        if (hasPower()) {
            label += '\n' + translation.get("label.power_xp", powerXP, ForcedXPRules.getPowerLevelXP(powerLevel));
        }
        if (levellingData.hasPower()) {
            label += '\n' + translation.get("label.power_level", ForcedXPRules.getPowerLabel(getPowerLevel(), translation));
        }
        if (levellingData.canLevelDown()) {
            label += '\n' + translation.get("label.started_on", Move.getCoordinates(initialPosition));
        }
        if (canLevelDown()) {
            label += '\n' + translation.get("label.will_respawn_as", ForcedXPRules.getPreviousLevel(getType()).getProperName(translation));
        }
        if (ForcedXPRules.getPieceXPWorth(getType()) != 0
                && levellingData != LevellingData.NONE
                && levellingData != LevellingData.DOWN
                && levellingData != LevellingData.FORCE_DOWN) {
            label += '\n' + translation.get("label.worth_xp", ForcedXPRules.getPieceXPWorth(getType()));
        }
        return label;
    }

    @Override
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
        if (this.initialPosition == null) {
            this.initialPosition = new Point(x, y);
        }
    }

    private void addXP(int amount) {
        xp += amount;
        resistanceXP += amount;
        powerXP += amount;
    }

    public void damage(@NotNull Board board, double dmg) {
        if (levellingData.hasHP()) {
            hp -= dmg;
            if (hp <= 0) {
                board.placePiece(cell(), position.x, position.y);
            }
        }
    }

    @Override
    public ArrayList<Move> getAvailableCaptures(@NotNull Board board) {
        ArrayList<Move> moves = super.getAvailableCaptures(board);
        ArrayList<Move> filteredMoves = new ArrayList<>();
        for (Move move : moves) {
            Piece piece = board.getPiece(move.getEndRow(), move.getEndCol());
            if (piece instanceof LevellingForcedPiece) {
                if (getPowerLevel() > ((LevellingForcedPiece) piece).getResistanceLevel()) {
                    filteredMoves.add(move);
                }
            } else {
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

    @Override
    public void move(@NotNull Board board, int row, int col) {
        if (movementType instanceof SpecialFirstMoveType) {
            ((SpecialFirstMoveType) movementType).move();
        }
        hasCaptured = false;
        hasRespawned = false;
        board.placePiece(cell(), position.x, position.y);
        Piece capturedPiece = board.getPiece(row, col);
        if (capturedPiece instanceof LevellingForcedPiece) {
            int captureXP = ForcedXPRules.getPieceXPWorth(board.getType(row, col));
            if (captureXP != 0) {
                addXP(captureXP);
                hasCaptured = true;
            }
            board.placePiece(this, row, col);
            if (((LevellingForcedPiece) capturedPiece).canLevelDown()) {
                ((LevellingForcedPiece) capturedPiece).respawn(board);
            }
        } else {
            board.placePiece(this, row, col);
        }
        if (hasRespawned) {
            hasRespawned = false;
        } else {
            levelUpPowerAndResistance();
            board.updatePieces();
            if (board.isInCheck(side.getOpponent())) {
                addXP(ForcedXPRules.getCheckXPWorth());
                hasChecked = true;
            } else {
                hasChecked = false;
            }
            if (!hasChecked && !hasCaptured) {
                addXP(ForcedXPRules.getMoveXPWorth());
            }
            levelUpPowerAndResistance();
            if (canLevelUp()) {
                if (xp >= getMaxXP()) {
                    levelUp(board);
                }
            } else {
                xp = 0;
            }
        }
    }

    private void respawn(@NotNull Board board) {
        if (levellingData.forceInvolution()) {
            Piece blockingPiece = board.getPiece(getInitialPosition().x, getInitialPosition().y);
            if (blockingPiece.getType() != PieceType.EMPTY) {
                if (blockingPiece instanceof InvincibleForcedPiece) {
                    return;
                }
                if (blockingPiece.getType() == PieceType.KING) {
                    return;
                }
                if (blockingPiece instanceof LevellingForcedPiece) {
                    ((LevellingForcedPiece) blockingPiece).respawn(board);
                }
            }
            applyInvolution(board);
        } else if (board.isFree(initialPosition.x, initialPosition.y)) {
            applyInvolution(board);
        }
    }

    private void applyInvolution(Board board) {
        hasRespawned = true;
        resistanceXP = 0;
        resistanceLevel = resistanceLevel > 0 ? resistanceLevel - 1 : 0;
        powerXP = 0;
        powerLevel = powerLevel > 0 ? powerLevel - 1 : 0;
        int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
        if (currentLevel > 0) {
            PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel - 1);
            morph(board, newPieceType, getPromotionInfo(newPieceType), 0, initialPosition);
        }
    }

    private void levelUp(@NotNull Board board) {
        int currentLevel = ForcedXPRules.LEVELS.indexOf(getType());
        if (currentLevel < ForcedXPRules.LEVELS.size() - 1) {
            PieceType newPieceType = ForcedXPRules.LEVELS.get(currentLevel + 1);
            morph(board, newPieceType, getPromotionInfo(newPieceType), xp - getMaxXP(), position);
            LevellingForcedPiece newPiece = ((LevellingForcedPiece) board.getPiece(position.x, position.y));
            board.updatePieces();
            if (hasChecked) {
                newPiece.hasChecked = true;
            } else if (board.isInCheck(side.getOpponent())) {
                if (hasCaptured) {
                    newPiece.addXP(ForcedXPRules.getCheckXPWorth());
                } else {
                    newPiece.addXP(ForcedXPRules.getCheckXPWorth() - ForcedXPRules.getMoveXPWorth());
                }
                newPiece.hasChecked = true;
            }
            newPiece.hasCaptured = hasCaptured;
            newPiece.levelUpPowerAndResistance();
            if (newPiece.canLevelUp()) {
                if (newPiece.xp >= newPiece.getMaxXP()) {
                    newPiece.levelUp(board);
                }
            }
        }
    }

    private void levelUpPowerAndResistance() {
        if (hasResistance()) {
            if (hasPower()) {
                while (powerXP >= getMaxPowerXP() && hasPower()) {
                    powerXP -= getMaxPowerXP();
                    powerLevel += 1;
                }
                if (!hasPower()) {
                    powerXP = 0;
                }
            } else {
                powerXP = 0;
            }
            if (getPowerLevel() > ForcedXPRules.RESISTED_PIECES.size()) {
                powerLevel -= getPowerLevel() - ForcedXPRules.RESISTED_PIECES.size();
            }
            while (resistanceXP >= getMaxResistanceXP() && hasResistance()) {
                resistanceXP -= getMaxResistanceXP();
                resistanceLevel += 1;
            }
            if (!hasResistance()) {
                resistanceXP = 0;
            }
        } else {
            resistanceXP = 0;
        }
    }

    @Nullable
    private PromotionInfo getPromotionInfo(PieceType newPieceType) {
        if (newPieceType == PieceType.PAWN && !canLevelUp()) {
            return new PromotionInfo(side == Side.WHITE ? 4 : 1, PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN);
        }
        return null;
    }

    private void morph(@NotNull Board board, PieceType newPieceType, PromotionInfo promotionInfo, int xp, @NotNull Point position) {
        board.placePiece(PieceFactory.piece(baseType, newPieceType, side, promotionInfo, levellingData, xp, resistanceXP, resistanceLevel, powerXP, powerLevel, hp, maxHP, initialPosition), position.x, position.y);
    }

    private boolean canLevelUp() {
        return getMaxXP() != 0 && levellingData.canLevelUp();
    }

    private boolean canLevelDown() {
        return ForcedXPRules.LEVELS.indexOf(getType()) > 0 && levellingData.canLevelDown();
    }

    private boolean hasResistance() {
        return getResistanceLevel() < ForcedXPRules.getMaxResistanceLevel() && levellingData.hasResistance();
    }

    private boolean hasPower() {
        return getPowerLevel() < ForcedXPRules.getMaxPowerLevel() && levellingData.hasPower();
    }
}
