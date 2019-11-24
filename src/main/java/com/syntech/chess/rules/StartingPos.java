package com.syntech.chess.rules;

import com.syntech.chess.logic.pieces.EmptyCell;
import com.syntech.chess.logic.pieces.Piece;
import com.syntech.chess.logic.pieces.chess.*;
import com.syntech.chess.logic.pieces.forced.*;
import com.syntech.chess.logic.pieces.fa_forced.*;
import com.syntech.chess.logic.Side;

public class StartingPos {
    public static final Piece[][] chess = {
            {
                    new Rook(Side.WHITE),
                    new Knight(Side.WHITE),
                    new Bishop(Side.WHITE),
                    new Queen(Side.WHITE),
                    new King(Side.WHITE),
                    new Bishop(Side.WHITE),
                    new Knight(Side.WHITE),
                    new Rook(Side.WHITE)
            },
            {
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE),
                    new Pawn(Side.WHITE)
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK),
                    new Pawn(Side.BLACK)
            },
            {
                    new Rook(Side.BLACK),
                    new Knight(Side.BLACK),
                    new Bishop(Side.BLACK),
                    new Queen(Side.BLACK),
                    new King(Side.BLACK),
                    new Bishop(Side.BLACK),
                    new Knight(Side.BLACK),
                    new Rook(Side.BLACK)
            }
    };

    public static final Piece[][] forcedChess = {
            {
                    new ForcedRook(Side.WHITE),
                    new ForcedQueen(Side.WHITE),
                    new ForcedKing(Side.WHITE),
                    new ForcedRook(Side.WHITE)
            },
            {
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE)
            },
            {
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK)
            },
            {
                    new ForcedRook(Side.BLACK),
                    new ForcedQueen(Side.BLACK),
                    new ForcedKing(Side.BLACK),
                    new ForcedRook(Side.BLACK)
            }
    };

    public static final Piece[][] forcedChess48 = {
            {
                    new ForcedRook(Side.WHITE),
                    new ForcedQueen(Side.WHITE),
                    new ForcedKing(Side.WHITE),
                    new ForcedRook(Side.WHITE)
            },
            {
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE),
                    new ForcedPawn(Side.WHITE)
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell(),
                    new EmptyCell()
            },
            {
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK),
                    new ForcedPawn(Side.BLACK)
            },
            {
                    new ForcedRook(Side.BLACK),
                    new ForcedQueen(Side.BLACK),
                    new ForcedKing(Side.BLACK),
                    new ForcedRook(Side.BLACK)
            }
    };

    public static final Piece[][] forcedChessFA = {
            {
                    new FAForcedRook(Side.WHITE),
                    new FAForcedQueen(Side.WHITE),
                    new FAForcedKing(Side.WHITE),
                    new FAForcedRook(Side.WHITE)
            },
            {
                    new FAForcedPawn(Side.WHITE),
                    new FAForcedPawn(Side.WHITE),
                    new FAForcedPawn(Side.WHITE),
                    new FAForcedPawn(Side.WHITE)
            },
            {
                    new FAForcedPawn(Side.BLACK),
                    new FAForcedPawn(Side.BLACK),
                    new FAForcedPawn(Side.BLACK),
                    new FAForcedPawn(Side.BLACK)
            },
            {
                    new FAForcedRook(Side.BLACK),
                    new FAForcedQueen(Side.BLACK),
                    new FAForcedKing(Side.BLACK),
                    new FAForcedRook(Side.BLACK)
            }
    };
}
