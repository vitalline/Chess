package com.syntech.chess.rules.neutral;

import com.syntech.chess.logic.Board;
import com.syntech.chess.logic.Move;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.rules.MovementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class ImmovableType extends MovementType {

    private PieceType type;

    public ImmovableType(PieceType type) {
        this.type = type;
    }

    @Override
    public ArrayList<Move> getAvailableMovesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Move> getAvailableCapturesWithoutSpecialRules(@NotNull Point position, @NotNull Board board) {
        return new ArrayList<>();
    }

    @Override
    public int getEvaluationCost() {
        return 0;
    }

    @Override
    public PieceType getType() {
        return type;
    }
}
