package com.piece;

import com.main.GamePanel;
import com.main.Type;

public class Queen extends Piece{
    public Queen(int color, int col, int row){
        super(color, col, row);
        type = Type.QUEEN;

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-queen");
        }else{
            image = getImage("/piece/b-queen");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBounds(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false){
            //vertical and horizontal
            if(targetCol == preCol  || targetRow == preRow){
                if(isValidSquare(targetCol, targetRow) && !isStraightPathBlocked(targetCol, targetRow)){
                    return true;
                }
            }

            //diagonal 
            if(Math.abs(targetCol -preCol) ==Math.abs(targetRow-preRow)){
                if(isValidSquare(targetCol, targetRow) && !isDiagonalLineBlocked(targetCol, targetRow)){
                    return true;
                }
            }

        }
        return false;
    }

}
