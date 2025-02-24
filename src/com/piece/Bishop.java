package com.piece;

import com.main.GamePanel;



public class Bishop extends Piece{
    public Bishop(int color, int col, int row){
        super(color, col, row);
        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-bishop");
        }else{
            image = getImage("/piece/b-bishop");
        }
    }

     public boolean canMove(int targetCol, int targetRow){
        if(isWithinBounds(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false){
            //ratio of row : cols = 1:1
            if(Math.abs(targetCol-preCol) == Math.abs(targetRow-preRow)){
                if(isValidSquare(targetCol, targetRow) && isDiagonalLineBlocked(targetCol, targetRow) == false){
                    return true;
                }
            }
        }
        return false;
     }

}
