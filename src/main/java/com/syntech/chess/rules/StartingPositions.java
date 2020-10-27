package com.syntech.chess.rules;

import com.syntech.chess.logic.*;
import com.syntech.chess.logic.pieces.Piece;
import org.jetbrains.annotations.NotNull;

public class StartingPositions extends PieceFactory {

    @NotNull
    private static Piece chessPawn(Side side) {
        return chessPawn(PieceBaseType.PIECE, side);
    }

    @NotNull
    private static Piece chessPawn(PieceBaseType baseType, Side side) {
        return piece(baseType, PieceType.DOUBLE_PAWN, side, new PromotionInfo(side == Side.WHITE ? 8 : 1, chessPawnPromotions()));
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side,
                                         int promotionRow, PieceType[] promotions, LevellingData levellingData) {
        return piece(baseType, PieceType.PAWN, side, new PromotionInfo(promotionRow, promotions), levellingData);
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
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
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

    public static final Piece[][] forcedChess8x8 = {
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_ROOK, Side.WHITE)
            },
            {
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE)
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    chessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CASTLING_ROOK, Side.BLACK)
            }
    };
    public static final Piece[][] chess4x4 = {
            {
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.PIECE, Side.WHITE)
            },
            {
                    forcedChessPawn(PieceBaseType.PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.PIECE, Side.BLACK)
            },
            {
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.ROOK, Side.BLACK)
            }
    };
    public static final Piece[][] forceMinorChess = {
            {
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE)
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
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK)
            }
    };
    public static final Piece[][] sniperChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    wall(),
                    cell(), cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE)
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    wall(), wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(), wall()
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
                    wall(), wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(), wall()
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    wall(),
                    cell(), cell(),
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
                    cell(), cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE)
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    wall(), wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(), wall()
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
                    wall(), wall(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(), wall()
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    wall(),
                    cell(), cell(),
                    wall(),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };
    public static final Piece[][] reserveChess = {
            {
                    cell(), cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(), cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(), cell()
            },
            {
                    cell(),
                    wall(), wall(), wall(), wall(), wall(), wall(),
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
                    wall(), wall(), wall(), wall(), wall(), wall(),
                    cell()
            },
            {
                    cell(), cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(), cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(), cell()
            }
    };
    public static final Piece[][] modestReserveChess = {
            {
                    cell(), cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(), cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(), cell()
            },
            {
                    cell(),
                    wall(), wall(), wall(), wall(), wall(), wall(),
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
                    wall(), wall(), wall(), wall(), wall(), wall(),
                    cell()
            },
            {
                    cell(), cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(), cell(),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(), cell()
            }
    };
    public static final Piece[][] pegasiReserveChess = {
            {
                    cell(), cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    cell(), cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    cell(), cell()
            },
            {
                    cell(),
                    wall(), wall(), wall(), wall(), wall(), wall(),
                    cell()
            },
            {
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE)
            },
            {
                    cell(), wall(),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    wall(), cell()
            },
            {
                    cell(), wall(),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    wall(), cell()
            },
            {
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK)
            },
            {
                    cell(),
                    wall(), wall(), wall(), wall(), wall(), wall(),
                    cell()
            },
            {
                    cell(), cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    cell(), cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    cell(), cell()
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
    public static final Piece[][] sniperReserveChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    wall(), wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
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
                    wall(), wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.WHITE, 6),
                    wall(), wall()
            },
            {
                    wall(), wall(),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.FORCED_PIECE, Side.BLACK, 3),
                    wall(), wall()
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
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    wall(), wall(),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };
    public static final Piece[][] sniperPegasiReserveChess = {
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    wall(), wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.WHITE),
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.WHITE)
            },
            {
                    wall(), wall(),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, 6),
                    wall(), wall()
            },
            {
                    wall(), wall(),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, 3),
                    wall(), wall()
            },
            {
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK),
                    wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK)
            },
            {
                    cell(), cell(),
                    wall(), wall(), wall(), wall(),
                    cell(), cell()
            },
            {
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    wall(), wall(),
                    piece(PieceBaseType.INVINCIBLE_FORCED_PIECE, PieceType.PEGASUS, Side.BLACK),
                    cell(),
                    piece(PieceBaseType.SHOOTING_PIECE, PieceType.SNIPER, Side.BLACK)
            }
    };
    public static final Piece[][] forcedInvolutionForcedChess = {
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, LevellingData.FORCE_DOWN)
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.FORCE_DOWN)
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.FORCE_DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.FORCE_DOWN)
            },
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK, LevellingData.FORCE_DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, LevellingData.FORCE_DOWN)
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
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell()
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
    public static final Piece[][] chess8x12 = {
            {
                    cell(), cell(),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    cell(), cell()
            },
            {
                    cell(), cell(),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    cell(), cell()
            },
            {
                    cell(), cell(),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    cell(), cell()
            }
    };
    public static final Piece[][] chess8x16 = {
            {
                    cell(), cell(), cell(), cell(),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.WHITE),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.WHITE),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    chessPawn(Side.WHITE),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell(),
                    cell(), cell(), cell(), cell(), cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    chessPawn(Side.BLACK),
                    cell(), cell(), cell(), cell()
            },
            {
                    cell(), cell(), cell(), cell(),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.QUEEN, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_KING, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.BISHOP, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.KNIGHT, Side.BLACK),
                    piece(PieceBaseType.PIECE, PieceType.CASTLING_ROOK, Side.BLACK),
                    cell(), cell(), cell(), cell()
            }
    };

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, PieceType[] promotions, LevellingData levellingData) {
        return forcedChessPawn(baseType, side, side == Side.WHITE ? 4 : 1, promotions, levellingData);
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, PieceType[] promotions) {
        return forcedChessPawn(baseType, side, promotions, LevellingData.NONE);
    }

    public static final Piece[][] involutionForcedChess = {
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, LevellingData.DOWN)
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.WHITE, chessPawnPromotions(), LevellingData.DOWN)
            },
            {
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.DOWN),
                    forcedChessPawn(PieceBaseType.LEVELLING_FORCED_PIECE, Side.BLACK, chessPawnPromotions(), LevellingData.DOWN)
            },
            {
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK, LevellingData.DOWN),
                    piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, LevellingData.DOWN)
            }
    };

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side) {
        return forcedChessPawn(baseType, side, forcedChessPawnPromotions());
    }

    @NotNull
    private static Piece forcedChessPawn3D(PieceBaseType baseType, Side side) {
        return piece(baseType, PieceType.PAWN_3D, side, new PromotionInfo(side == Side.WHITE ? 4 : 1, forcedChessPawn3DPromotions()), LevellingData.NONE);
    }

    @NotNull
    private static PieceType[] chessPawnPromotions() {
        return new PieceType[]{PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};
    }

    @NotNull
    private static PieceType[] forcedChessPawnPromotions() {
        return new PieceType[]{PieceType.ROOK, PieceType.QUEEN};
    }

    @NotNull
    private static PieceType[] forcedChessPawn3DPromotions() {
        return new PieceType[]{PieceType.KNIGHT_3D, PieceType.BISHOP_3D, PieceType.ROOK_3D, PieceType.CARDINAL_3D, PieceType.QUEEN_3D};
    }

    @NotNull
    private static Piece forcedChessPawn(PieceBaseType baseType, Side side, int promotionRow) {
        return forcedChessPawn(baseType, side, promotionRow, forcedChessPawnPromotions(), LevellingData.NONE);
    }

    @NotNull
    public static Piece[][] mmoRPGForcedChess(LevellingData levellingData) {
        return new Piece[][]{
                {
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.WHITE, levellingData)
                },
                {
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.WHITE, levellingData)
                },
                {
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.PAWN, Side.BLACK, levellingData)
                },
                {
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.QUEEN, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.KING, Side.BLACK, levellingData),
                        piece(PieceBaseType.LEVELLING_FORCED_PIECE, PieceType.ROOK, Side.BLACK, levellingData)
                }
        };
    }

    public static final Piece[][] forcedChess3D = {
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CARDINAL_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.QUEEN_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CARDINAL_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CARDINAL_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.CARDINAL_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KING_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK)
            }
    };


    public static final Piece[][] modestForcedChess3D = {
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.CARDINAL_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.CARDINAL_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.CARDINAL_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.CARDINAL_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK)
            },
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.WHITE),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    forcedChessPawn3D(PieceBaseType.MODEST_FORCED_PIECE, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KNIGHT_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.BISHOP_3D, Side.BLACK),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK_3D, Side.BLACK)
            }
    };
}
