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
    private static Piece modestForcedChessPawn(Side side, Integer promotionRow) {
        return piece(PieceBaseType.PROMOTABLE_MODEST_FORCED_PIECE, PieceType.PAWN, side, promotionRow, forcedChessPawnPromotions(side));
    }

    @NotNull
    private static Piece modestForcedChessPawn(Side side) {
        return modestForcedChessPawn(side, side == Side.WHITE ? 4 : 1);
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

    public static final Piece[][] modestForcedChess = {
            {
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.QUEEN, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.KING, Side.WHITE),
                    piece(PieceBaseType.MODEST_FORCED_PIECE, PieceType.ROOK, Side.WHITE)
            },
            {
                    modestForcedChessPawn(Side.WHITE),
                    modestForcedChessPawn(Side.WHITE),
                    modestForcedChessPawn(Side.WHITE),
                    modestForcedChessPawn(Side.WHITE)
            },
            {
                    modestForcedChessPawn(Side.BLACK),
                    modestForcedChessPawn(Side.BLACK),
                    modestForcedChessPawn(Side.BLACK),
                    modestForcedChessPawn(Side.BLACK)
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
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
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
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
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
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
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
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    modestForcedChessPawn(Side.WHITE, 6),
                    wall(),
                    cell()
            },
            {
                    cell(),
                    wall(),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
                    modestForcedChessPawn(Side.BLACK, 3),
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
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    forcedChessPawn(Side.WHITE, 6),
                    wall(),
                    wall()
            },
            {
                    wall(),
                    wall(),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
                    forcedChessPawn(Side.BLACK, 3),
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
