package com.main;

import com.piece.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    // PIECES
    public static ArrayList<Piece> pieces = new ArrayList<>(); // Backup list
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece;
    public static Piece castlingPiece;

    // COLOR
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    // Booleans
    boolean canMove;
    boolean isValidSquare;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        SetPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Starting a thread calls a run method
    }

    public void SetPieces() {
        // White pieces
        pieces.add(new Pawn(WHITE, 0, 6));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 2, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 4, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new King(WHITE, 4, 7));

        // Black pieces
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {
        // GAME LOOP
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                updateGame();
                repaint();
                delta--;
            }
        }
    }

    private void updateGame() {
        if (mouse.pressed) {
            // Checks if a user can pick up a piece
            if (activePiece == null) {
                for (Piece piece : simPieces) {
                    // Pick a piece if mouse is on Array piece
                    if (piece.color == currentColor &&
                            piece.col == mouse.x / Board.SQUARE_SIZE &&
                            piece.row == mouse.y / Board.SQUARE_SIZE) {
                        activePiece = piece;
                        break;
                    }
                }
            } else {
                // Simulate a move hypothetically
                simulate();
            }
        }
        if (mouse.pressed == false && activePiece != null) {
            if (activePiece != null) {
                if (isValidSquare) {
                    // Update piece list in case a piece has been captured during simulation
                    copyPieces(simPieces, pieces);
                    activePiece.updatePosition();

                    // Handle en passant capture
                    if (activePiece instanceof Pawn && activePiece.hittingPiece != null && activePiece.hittingPiece.isTwoStepped) {
                        pieces.remove(activePiece.hittingPiece);
                        simPieces.remove(activePiece.hittingPiece);
                    }

                    if (castlingPiece != null) {
                        castlingPiece.updatePosition(); // Update the rook's position
                        castlingPiece = null; // Reset the castling piece
                    }

                    changePlayer();
                } else {
                    // Reset everything
                    copyPieces(pieces, simPieces);
                    activePiece.resetPosition();
                    activePiece = null; // Since it has been released
                }
            }
        }
    }

    private void simulate() {
        canMove = false;
        isValidSquare = false;

        // Reset piece list in every loop
        copyPieces(pieces, simPieces);

        // Reset castling
        if (castlingPiece != null) {
            castlingPiece.col = castlingPiece.preCol;
            castlingPiece.x = castlingPiece.getX(castlingPiece.col);
            castlingPiece = null;
        }

        // Subtract half square to center the mouse
        activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activePiece.col = activePiece.getCol(activePiece.x);
        activePiece.row = activePiece.getRow(activePiece.y);

        if (activePiece.canMove(activePiece.col, activePiece.row)) {
            canMove = true;

            // Handle castling
            if (activePiece instanceof King) {
                int colDiff = Math.abs(activePiece.col - activePiece.preCol);
                if (colDiff == 2) { // Castling move
                    int rookCol = (activePiece.col > activePiece.preCol) ? 7 : 0; // Kingside or queenside
                    for (Piece piece : simPieces) {
                        if (piece.col == rookCol && piece.row == activePiece.row && piece instanceof Rook && !piece.moved) {
                            castlingPiece = piece;
                            break;
                        }
                    }
                }
            }

            // Remove the captured piece from the list
            if (activePiece.hittingPiece != null) {
                simPieces.remove(activePiece.hittingPiece.getIndex());
            }
            isValidSquare = true;
        }

        checkCastling();
    }

    private void checkCastling() {
        if (castlingPiece != null) {
            int rookCol = (castlingPiece.col == 0) ? 3 : 5; // Queenside or kingside
            castlingPiece.col = rookCol;
            castlingPiece.x = castlingPiece.getX(rookCol);
        }
    }

    private void changePlayer() {
        currentColor = (currentColor == WHITE) ? BLACK : WHITE;

        // Reset the isTwoStepped flag for pawns of the new player's color
        for (Piece piece : pieces) {
            if (piece instanceof Pawn && piece.color == currentColor) {
                ((Pawn) piece).isTwoStepped = false;
            }
        }

        activePiece = null;
    }

    // Will handle all the drawing
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        // Draw board
        board.draw2DBoard(g2d);

        // Draw pieces
        for (Piece p : pieces) {
            p.drawPiece(g2d);
        }

        if (activePiece != null) {
            // Ensuring the highlight is within bounds
            if (canMove) {
                g2d.setColor(Color.white);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            // Draw the active piece
            activePiece.drawPiece(g2d);
        }

        // Status message
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
        g2d.setColor(Color.white);

        if (currentColor == WHITE) {
            g2d.drawString("White's Turn", 640, 550);
        } else {
            g2d.drawString("Black's Turn", 640, 50);
        }
    }
}