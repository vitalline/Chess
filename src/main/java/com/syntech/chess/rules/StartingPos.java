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
        return piece(PieceBaseType.PIECE, PieceType.DOUBLE_PAWN, side, side == Side.WHITE ? 8 : 1, chessPawnPromotions());
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, Integer promotionRow, PieceType... promotions) {
        return piece(baseType, PieceType.PAWN, side, promotionRow, promotions);
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, Integer promotionRow) {
        return forcedChessPawn(baseType, side, promotionRow, forcedChessPawnPromotions());
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, PieceType... promotions) {
        return forcedChessPawn(baseType, side, side == Side.WHITE ? 4 : 1, promotions);
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    private static PieceType[] chessPawnPromotions() {
        return new PieceType[]{PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    private static PieceType[] forcedChessPawnPromotions() {
        return new PieceType[]{PieceType.ROOK, PieceType.QUEEN};
    }

    public static final Piece[][] chess = {
            {
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE)
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
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK)
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
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] modestForcedChess = {
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK)
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] forceMajorChess = {
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK)
            }
    };

    public static final Piece[][] sniperChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    wall(),
                    cell(),
                    cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE)
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    wall(),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    wall()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    wall(),
                    cell()
            },
            {
                    wall(),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    wall()
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    wall(),
                    cell(),
                    cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };

    public static final Piece[][] modestSniperChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    wall(),
                    cell(),
                    cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE)
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    wall(),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    wall()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    wall(),
                    cell()
            },
            {
                    wall(),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    wall()
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    wall(),
                    cell(),
                    cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };

    public static final Piece[][] reserveChess = {
            {
                    cell(),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell()
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE)
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    wall(),
                    cell()
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK)
            },
            {
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    cell()
            }
    };

    public static final Piece[][] modestReserveChess = {
            {
                    cell(),
                    cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell()
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE)
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK, 3),
                    wall(),
                    cell()
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK)
            },
            {
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    cell()
            }
    };

    public static final Piece[][] sniperReserveChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    wall(),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE)
            },
            {
                    wall(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    wall(),
                    wall()
            },
            {
                    wall(),
                    wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    wall(),
                    wall()
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK)
            },
            {
                    cell(),
                    cell(),
                    wall(),
                    wall(),
                    wall(),
                    wall(),
                    cell(),
                    cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    wall(),
                    wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };

    public static final Piece[][] involutionForcedChess = {
            {
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.WHITE, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.WHITE, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.WHITE, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.WHITE, chessPawnPromotions())
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.BLACK, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.BLACK, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.BLACK, chessPawnPromotions()),
                    forcedChessPawn(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, Side.BLACK, chessPawnPromotions())
            },
            {
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_DOWN_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] mmoRPGForcedChess = {
            {
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.WHITE)
            },
            {
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.PAWN, Side.BLACK)
            },
            {
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_UP_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] involutionMMORPGForcedChess = {
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE)
            },
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK)
            },
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };

    public static final Piece[][] cloningForcedChess = {
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.BISHOP, Side.WHITE)
            },
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.WHITE),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.WHITE)
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    cell(),
                    cell(),
                    cell(),
                    cell()
            },
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.PAWN, Side.BLACK)
            },
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.BISHOP, Side.BLACK)
            },
            {
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.CLONING_FORCED_PIECE, PieceType.ROOK, Side.BLACK)
            }
    };
}
