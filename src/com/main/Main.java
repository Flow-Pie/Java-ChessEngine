package com.main;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame  window = new JFrame("Chess Engine");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.launchGameThread();
    }

}
