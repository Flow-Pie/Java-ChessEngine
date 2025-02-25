package com.piece;

import com.main.GamePanel;

public class Pawn extends Piece{

    public Pawn(int color, int col, int row){
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-pawn");
        }else{
            image = getImage("/piece/b-pawn");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if(isWithinBounds(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            //define a move based on pawn color
            int moveValue;
            if(color == GamePanel.WHITE){
                moveValue = -1;
            }else{
                moveValue = 1;
            }

            //check the hitting piece
            hittingPiece = getHittingPiece(targetCol, targetRow);

            //1 square movement
            if(targetCol == preCol && targetRow == preRow + moveValue && hittingPiece == null){
                return true;
            }

            //2 square movement
            if(targetCol == preCol && targetRow == preRow +moveValue*2 &&
                hittingPiece == null &&  !moved &&
                !isStraightPathBlocked(targetCol, targetRow)
            ){
                return true;
            }

            //Diagonal movement for capture
            if(Math.abs(targetCol-preCol) ==1 && targetRow == preRow+ moveValue && 
            hittingPiece !=null && hittingPiece.color != color){
                return true;
            }
        }
        return false;
    }

}
