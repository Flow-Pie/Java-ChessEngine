package com.piece;

import com.main.GamePanel;

public class Rook extends Piece{

    public Rook(int color, int col, int row){
        super(color, col, row);
        if(color ==GamePanel.WHITE){
            image = getImage("/piece/w-rook");
        }else{
            image = getImage("/piece/b-rook");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){        
        if(isWithinBounds(targetCol, targetRow) && isSameSquare(targetCol, targetRow) ==false){
            //moves row wise or column wise
            if(targetCol==preCol || targetRow==preRow){
                if(isValidSquare(targetCol,targetRow) && isPathBlocked(targetCol, targetRow) ==false){
                    return true;
                }
            }
        }
        return false;
    }



}
