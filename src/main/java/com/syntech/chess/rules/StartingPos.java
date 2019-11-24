package com.syntech.chess.rules;

import com.syntech.chess.logic.PieceBaseType;
import com.syntech.chess.logic.PieceFactory;
import com.syntech.chess.logic.PieceType;
import com.syntech.chess.logic.Side;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StartingPos extends PieceFactory {
    @NotNull
    private static Piece chessPawn(Side side) {
        return piece(PieceBaseType.PROMOTABLE_PIECE, PieceType.DOUBLE_PAWN, side, side == Side.WHITE ? 8 : 1, chessPawnPromotions(side));
    }

    @NotNull
    private static Piece forcedChessPawn(Side side, Integer promotionRow) {
        return piece(PieceBaseType.PROMOTABLE_FORCED_PIECE, PieceType.PAWN, side, promotionRow, forcedChessPawnPromotions(side));
    }

    @NotNull
    private static Piece forcedChessPawn(Side side) {
        return forcedChessPawn(side, side == Side.WHITE ? 4 : 1);
    }

    @NotNull
    @Contract("_ -> new")
    private static Piece[] chessPawnPromotions(Side side) {
        return new Piece[]{
                piece(PieceBaseType.PIECE, PieceType.KNIGHT, side),
                piece(PieceBaseType.PIECE, PieceType.BISHOP, side),
                piece(PieceBaseType.PIECE, PieceType.ROOK, side),
                piece(PieceBaseType.PIECE, PieceType.QUEEN, side)
        };
    }

    @NotNull
    @Contract("_ -> new")
    private static Piece[] forcedChessPawnPromotions(Side side) {
        return new Piece[]{
                piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, side),
                piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, side)
        };
    }

    public static final Piece[][] chess = {
            {
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE)
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK)
            },
            {
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] forcedChess = {
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    forcedChessPawn(Side.WHITE),
                    forcedChessPawn(Side.WHITE),
                    forcedChessPawn(Side.WHITE),
                    forcedChessPawn(Side.WHITE)
            },
            {
                    forcedChessPawn(Side.BLACK),
                    forcedChessPawn(Side.BLACK),
                    forcedChessPawn(Side.BLACK),
                    forcedChessPawn(Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] forcedChessFA = {
            {
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.WHITE)
            },
            {
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.PAWN, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FA_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };
}
