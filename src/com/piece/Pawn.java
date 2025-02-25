package com.piece;

import com.main.GamePanel;
import com.main.Type;

public class Pawn extends Piece {

    public Pawn(int color, int col, int row) {
        super(color, col, row);

        type = Type.PAWN;

        if (color == GamePanel.WHITE) {
            image = getImage("/piece/w-pawn");
        } else {
            image = getImage("/piece/b-pawn");
        }
    }

    public boolean canMove(int targetCol, int targetRow) {
        if (isWithinBounds(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            // Define a move based on pawn color
            int moveValue = (color == GamePanel.WHITE) ? -1 : 1;

            // Check the hitting piece
            hittingPiece = getHittingPiece(targetCol, targetRow);

            // 1 square movement
            if (targetCol == preCol && targetRow == preRow + moveValue && hittingPiece == null) {
                return true;
            }

            // 2 square movement
            if (targetCol == preCol && targetRow == preRow + moveValue * 2 &&
                    hittingPiece == null && !moved &&
                    !isStraightPathBlocked(targetCol, targetRow)) {
                return true;
            }

            // Diagonal movement for capture
            if (Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue) {
                // Regular capture
                if (hittingPiece != null && hittingPiece.color != color) {
                    return true;
                }

                // En passant
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == targetCol && piece.row == preRow && piece instanceof Pawn && piece.isTwoStepped) {
                        hittingPiece = piece; // Set the opponent's pawn as the hitting piece
                        return true;
                    }
                }
            }
        }
        return false;
    }
}