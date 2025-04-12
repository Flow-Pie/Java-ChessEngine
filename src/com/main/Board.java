package com.main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Board {
    static final int MAX_COL = 8;
    static final int MAX_ROW = 8;
    public static final int SQUARE_SIZE = 70;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
    private static final int BOARD_CORNER_RADIUS = 15;
    private static final Color COORDINATE_COLOR = new Color(160, 170, 180);

    public void draw2DBoard(Graphics2D g2d, int boardX, int boardY, Color lightColor, Color darkColor) {
        // Enable anti-aliasing for smooth edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw board background with rounded corners
        drawBoardBackground(g2d, boardX, boardY, new Color(44, 47, 51));

        // Draw chess squares
        drawChessSquares(g2d, boardX, boardY, lightColor, darkColor);

        // Draw board coordinates
        drawCoordinates(g2d, boardX, boardY);
    }

    private void drawBoardBackground(Graphics2D g2d, int x, int y, Color bgColor) {
        g2d.setColor(bgColor);
        int boardSize = MAX_COL * SQUARE_SIZE;
        g2d.fill(new RoundRectangle2D.Double(
            x - 10, y - 10,
            boardSize + 20, boardSize + 20,
            BOARD_CORNER_RADIUS, BOARD_CORNER_RADIUS
        ));
    }

    private void drawChessSquares(Graphics2D g2d, int boardX, int boardY, Color light, Color dark) {
        boolean isLight = true;
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                int x = boardX + col * SQUARE_SIZE;
                int y = boardY + row * SQUARE_SIZE;

                // Alternate square colors
                g2d.setColor(isLight ? light : dark);
                g2d.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                // Add subtle inner highlight
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.drawRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);

                isLight = !isLight;
            }
            isLight = !isLight;
        }
    }

    private void drawCoordinates(Graphics2D g2d, int boardX, int boardY) {
        g2d.setColor(COORDINATE_COLOR);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Draw column letters (a-h)
        for (int col = 0; col < MAX_COL; col++) {
            String text = String.valueOf((char) ('a' + col));
            int x = boardX + col * SQUARE_SIZE + SQUARE_SIZE/2 - 5;
            int y = boardY - 15;
            g2d.drawString(text, x, y);

            y = boardY + MAX_ROW * SQUARE_SIZE + 25;
            g2d.drawString(text, x, y);
        }

        // Draw row numbers (1-8)
        for (int row = 0; row < MAX_ROW; row++) {
            String text = String.valueOf(8 - row);
            int x = boardX - 25;
            int y = boardY + row * SQUARE_SIZE + SQUARE_SIZE/2 + 5;
            g2d.drawString(text, x, y);

            x = boardX + MAX_COL * SQUARE_SIZE + 15;
            g2d.drawString(text, x, y);
        }
    }
}