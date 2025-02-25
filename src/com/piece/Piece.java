package com.piece;

import com.main.Board;
import com.main.GamePanel;
import com.main.Type;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;



public class Piece {
    public Type type;
    public BufferedImage image;
    public int x,y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingPiece;
    public boolean moved, isTwoStepped;

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
        try {
            // Load the image from the classpath
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            if (image == null) {
                throw new IOException("Image not found: " + imagePath + ".png");
            }
            return image;
        } catch (IOException e) {
            System.err.println("Error loading image: " + imagePath + ".png");
            e.printStackTrace();
            return null;
        }
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


    public void updatePosition() {
        // Update the previous position before moving
        preCol = col;
        preRow = row;

        x = getX(col);
        y = getY(row);

        if (type == Type.PAWN) {
            if (Math.abs(row - preRow) == 2) {
                isTwoStepped = true; // Set the flag if the pawn moves two squares forward
            }
        }

        moved = true;
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
    private boolean isSquareOccupied(int col, int row) {
        for (Piece piece : GamePanel.simPieces) {
            if (piece.col == col && piece.row == row) {
                return true;
            }
        }
        return false;
    }

    public boolean isDiagonalLineBlocked(int targetCol, int targetRow) {
        int colStep = (targetCol > preCol) ? 1 : -1; // Direction of column movement
        int rowStep = (targetRow > preRow) ? 1 : -1; // Direction of row movement

        int c = preCol + colStep;
        int r = preRow + rowStep;

        while (c != targetCol && r != targetRow) {
            if (isSquareOccupied(c, r)) {
                return true;
            }
            c += colStep;
            r += rowStep;
        }
        return false;
    }



    public boolean isStraightPathBlocked(int targetCol, int targetRow) {
        int colStep = (targetCol != preCol) ? (targetCol > preCol ? 1 : -1) : 0;
        int rowStep = (targetRow != preRow) ? (targetRow > preRow ? 1 : -1) : 0;

        int c = preCol + colStep;
        int r = preRow + rowStep;

        while (c != targetCol || r != targetRow) {
            if (isSquareOccupied(c, r)) {
                return true;
            }
            c += colStep;
            r += rowStep;
        }
        return false;
    }
    public void drawPiece(Graphics2D graphic2d){
        graphic2d.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
