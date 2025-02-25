package com.piece;

import com.main.GamePanel;
import com.main.Type;

public class King extends Piece{
    public King(int color, int col, int row) {
        super(color, col, row);

        type = Type.KING;

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-king");
        }else{
            image = getImage("/piece/b-king");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBounds(targetCol, targetRow)){
            //handle L, R, T,B movements
            if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 || 
            /*handle diagonal movements */
                (Math.abs(targetCol -preCol) * Math.abs(targetCol - preCol) == 1)
            ){
                if(isValidSquare(targetCol, targetRow)){                    
                    return true;
                }
            }

            //handle right castling
            if(targetCol ==preCol+2 && targetRow == preRow && !isStraightPathBlocked(targetCol, targetRow)){
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == preCol+3 && piece.row == preRow && !piece.moved){
                        return  true;
                    }
                }
            }

            //left catling
            if(targetCol == preCol-2 && targetRow ==preRow && !isStraightPathBlocked(targetCol, targetRow)){
                Piece p[] = new Piece[2];
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == preCol-3 && piece.row == targetRow){
                        p[0] = piece;//Knight
                    }
                    if(piece.col == preCol-4 && piece.row == targetRow){
                        p[1] = piece;//rook
                    }
                    if(p[0] == null && p[1] !=null && !p[1].moved){
                        GamePanel.castlingPiece = p[1];
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
