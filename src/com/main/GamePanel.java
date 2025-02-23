package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    final int FPS =60;
    Thread gameThread;
    Board board = new Board();

    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
    }

    public void launchGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //starting a thread calls a run method
    }

    @Override
    public void run(){
        //GAME LOOP
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta +=(currentTime - lastTime)/drawInterval;
            lastTime = currentTime; 
            
            if(delta >=1 ){
                updateGame();
                repaint();
                delta --;
            }

        }
    }

    
    private void updateGame(){

    }
    
    //will handle all the drawing
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        Graphics2D graphic2d = (Graphics2D) graphics;
        board.draw2DBoard(graphic2d);
    }


}
