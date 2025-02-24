package com.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Board {

    final int MAX_COL =8;
    final int MAX_ROW =8;
    public static final int SQUARE_SIZE =75;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;

    public void draw2DBoard(Graphics2D g2d){
        int colorCol =0;
        for (int row=0; row<MAX_ROW; row++){
            for(int col=0; col<MAX_COL; col++){
                if(colorCol ==0){
                    g2d.setColor(new Color(210,165,125));
                    colorCol =1;
                }else{
                    g2d.setColor(new Color(175,115,70));
                    colorCol=0;
                }

                g2d.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            //change color for next row
            if(colorCol ==0){
                colorCol =1;
            }else{
                colorCol=0;
            }
        }
    }

}
