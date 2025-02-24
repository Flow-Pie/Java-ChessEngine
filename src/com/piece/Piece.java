package com.piece;

import com.main.Board;
import com.main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;



public class Piece {
    public BufferedImage image;
    public int x,y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingPiece;

    public Piece(int color, int col, int row){
        this.color = color;
        this.col=col;
        this.row=row;
        this.x=getX(col);
        this.y=getY(row);
        preCol=col;
        preRow=row;
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        System.out.println("Attempting to load image: " + imagePath + ".png");
    
        try {
            // Load the image from the classpath
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            if (image == null) {
                System.out.println("Image not found: " + imagePath + ".png");
            }
        } catch (IOException e) {
            System.out.println("Error while reading image: " + imagePath + ".png");
            e.printStackTrace();
        }
    
        return image;
    }

    public int getX(int col){
        return  col*Board.SQUARE_SIZE;
    }

    public int getY(int row){
        return  row*Board.SQUARE_SIZE;
    }

    public int getCol(int x){
        //program should detect piece col based on center point on the board
        return ((x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE);
    }

    public int getRow(int y){
        return ((y+ Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE);
    }

    public int getIndex(){
        for(int index=0; index<GamePanel.simPieces.size(); index++){
            if(GamePanel.simPieces.get(index) == this){
                return index;
            }
        }
        return 0;
    }
    public Piece getHittingPiece(int targetCol,int targetRow ){
        for(Piece piece : GamePanel.simPieces){
            if(piece.col == targetCol && piece.row == targetRow && piece !=this){/*this represent active piece */
                return piece;
            }
        }
        return null;
    }


    public void updatePosition(){
        //place piece at the center
        x =getX(col);
        y =getY(row);

        preCol = getCol(x);
        preRow = getRow(y);
    }

    public void resetPosition(){
        col = preCol;
        row = preRow;

        x = getX(col);
        y= getY(row);       

    }


    //to be overwritten/ overrriden by each piece type
    public boolean canMove(int targetCol, int targetRow){
        return false;
    }

    public boolean isWithinBounds(int targetCol, int targetRow){
        if(targetCol>=0 && targetCol<=7 && targetRow >=0 && targetRow <=7){
            return true;
        }
        return false;
    }
    
    public boolean isValidSquare(int targetCol, int targetRow){
        hittingPiece = getHittingPiece(targetCol, targetRow);
        if(hittingPiece == null){
            //square vacant
            return true;
        }else {
            if(hittingPiece.color != this.color){
                //piece can be captured
                return true;
            }else{
                hittingPiece = null;
            }
        }
        return false;
    }
    
    public boolean isSameSquare(int targetCol, int targetRow){
        if(targetCol==preCol && targetRow ==preRow){
            return true;
        }
        return false;
    }

    public boolean isStraightPathBlocked(int targetCol, int targetRow) {
        //(left or right)
        if (targetRow == preRow) {
            int step = (targetCol > preCol) ? 1 : -1; 
            for (int c = preCol + step; c != targetCol; c += step) {
                if (isSquareOccupied(c, targetRow)) {
                    return true;
                }
            }
        }
        // Moving(up or down)
        else if (targetCol == preCol) {
            int step = (targetRow > preRow) ? 1 : -1; // Determine direction
            for (int r = preRow + step; r != targetRow; r += step) {
                if (isSquareOccupied(targetCol, r)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDiagonalLineBlocked(int targetCol, int targetRow){
        if(targetRow < preRow){
            //upleft
            for(int c= preCol-1; c>targetCol; c--){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col ==c && piece.row ==preRow -diff){
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
            //upright
            for(int c= preCol+1; c<targetCol; c++){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col ==c && piece.row ==preRow -diff){
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
        }
        if(targetRow > preRow){
            //Down left 
            for(int c= preCol-1; c>targetCol; c--){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col ==c && piece.row ==preRow + diff){
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
            //Down right
            for(int c= preCol+1; c<targetCol; c++){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col ==c && piece.row ==preRow + diff){
                        hittingPiece = piece;
                        return true;
                    }
                }
            }
        
        }

        return false;
    }

    
    /**
     * Checks if a specific square is occupied by any piece.
     */
    private boolean isSquareOccupied(int col, int row) {
        for (Piece piece : GamePanel.simPieces) {
            if (piece.col == col && piece.row == row) {
                hittingPiece = piece; // Set the hitting piece
                return true;
            }
        }
        return false;
    }
    public void drawPiece(Graphics2D graphic2d){
        graphic2d.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
