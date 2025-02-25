package com.piece;

import com.main.GamePanel;
import com.main.Type;

public class Knight extends Piece {
    public Knight(int color, int col, int row){
        super(color, col, row);

        type = Type.KNIGHT;

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-knight");
        }else{
            image = getImage("/piece/b-knight");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBounds(targetCol, targetRow)){
            //col :row = 1:2 or 2:1 product of those ratios will always equal 1
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) ==2){
                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }

}
