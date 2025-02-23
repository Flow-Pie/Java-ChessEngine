package com.piece;

import java.awt.image.BufferedImage;

import com.main.Board;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;



public class Piece {
    public BufferedImage image;
    public int x,y;
    public int col, row, preCol, preRow;
    public int color;

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

    public void drawPiece(Graphics2D graphic2d){
        graphic2d.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
