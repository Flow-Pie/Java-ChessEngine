package com.main;

import com.piece.*;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;



public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    final int FPS =60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    //PIECES
    public static ArrayList<Piece> pieces = new ArrayList<>();//backup list
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece, checkingPiece;
    public static Piece castlingPiece;
        ArrayList<Piece> promoPieces = new ArrayList<>();

    //COLOR
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    //Booleans
    boolean canMove;
    boolean isValidSquare;
    boolean promotion;
    boolean gameOver;

    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        SetPieces();
        copyPieces(pieces , simPieces);
    }
    
    private Piece getKing(boolean opponent){
        Piece king = null;

        for(Piece p : simPieces){
            if(opponent){
                if(p.type == Type.KING && p.color != currentColor){
                    king=p;
                }
            }else{
                if(p.type == Type.KING && p.color == currentColor){
                    king=p;
                }
            }
        }
        return king;
    }

    public void launchGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); 
    }

    public void SetPieces(){
        //White pieces
        pieces.add(new Pawn(WHITE,0,6));
        pieces.add(new Pawn(WHITE,1,6));
        pieces.add(new Pawn(WHITE,2,6));
        pieces.add(new Pawn(WHITE,3,6));
        pieces.add(new Pawn(WHITE,4,6));
        pieces.add(new Pawn(WHITE,5,6));
        pieces.add(new Pawn(WHITE,6,6));
        pieces.add(new Pawn(WHITE,7,6));
        pieces.add(new Rook(WHITE,0,7));
        pieces.add(new Rook(WHITE,7,7));
        pieces.add(new Knight(WHITE,1,7));
        pieces.add(new Knight(WHITE,6,7));
        pieces.add(new Bishop(WHITE,2,7));
        pieces.add(new Bishop(WHITE,5,7));
        pieces.add(new Queen(WHITE,3,7));
        pieces.add(new King(WHITE,4,7));


        //Black pieces
        pieces.add(new Pawn(BLACK,0,1));
        pieces.add(new Pawn(BLACK,1,1));
        pieces.add(new Pawn(BLACK,2,1));
        pieces.add(new Pawn(BLACK,3,1));
        pieces.add(new Pawn(BLACK,4,1));
        pieces.add(new Pawn(BLACK,5,1));
        pieces.add(new Pawn(BLACK,6,1));
        pieces.add(new Pawn(BLACK,7,1));
        pieces.add(new Rook(BLACK,0,0));
        pieces.add(new Rook(BLACK,7,0));
        pieces.add(new Knight(BLACK,1,0));
        pieces.add(new Knight(BLACK,6,0));
        pieces.add(new Bishop(BLACK,2,0));
        pieces.add(new Bishop(BLACK,5,0));
        pieces.add(new Queen(BLACK,3,0));
        pieces.add(new King(BLACK,4,0));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target){
        target.clear();
        for(int i=0; i<source.size(); i++){
            target.add(source.get(i));
        }

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
        if(promotion){
            promoting();
        }else if (gameOver == false){
            if(mouse.pressed){
                if(activePiece == null){                
                    for(Piece piece : simPieces){
                        if(piece.color == currentColor &&
                        piece.col == mouse.x/Board.SQUARE_SIZE &&
                        piece.row == mouse.y/Board.SQUARE_SIZE
                        ){
                            activePiece = piece;
                            System.err.println("Picked a piece at: "+piece.col+" , "+ piece.row);
                            break;
    
                        }
                    }
                }else{
                    //simulate a move hypothetically
                    simulate();
                }
            }
            if(mouse.pressed==false && activePiece !=null){

                if(activePiece !=null){

                    if(isValidSquare){
                        //MOVE CONFIRMED


                        copyPieces(simPieces, pieces);
                        activePiece.updatePosition();
    
                        if (castlingPiece != null) {
                            castlingPiece.updatePosition(); // Update the rook's position
                            castlingPiece = null; // !!Reset the castling piece
                        }

                        if(isKingInCheck() && isCheckMate()){
                            System.out.println("CHECK GAME OVER!!");
                            gameOver = true;
                        }else{//game continues
                             if(canPromote()){
                                 promotion =true;
                             }else{
                                 changePlayer();
                             }

                         }
    
                       
                    }else{
    
                        //Reset everything
                        copyPieces(pieces, simPieces);
                        activePiece.resetPosition();            
                        activePiece = null;//since it has been released
    
                        System.out.println("Invalid Position");
                    }
                }
            }
        }

        
    }

    private void simulate(){
    canMove = false;
    isValidSquare = false;

    //Reset piece list in every loop
    copyPieces(pieces, simPieces);

    //reset castling
    if(castlingPiece !=null){
        castlingPiece.col = castlingPiece.preCol;
        castlingPiece.x=castlingPiece.getX(castlingPiece.col);
        castlingPiece = null;
    }

    //subtract half square to center the mouse
    activePiece.x = mouse.x-Board.HALF_SQUARE_SIZE;
    activePiece.y = mouse.y-Board.HALF_SQUARE_SIZE;
    activePiece.col = activePiece.getCol(activePiece.x);
    activePiece.row = activePiece.getRow(activePiece.y);

    if (activePiece.canMove(activePiece.col, activePiece.row)) {
        canMove = true;

        // Handle castling
        if (activePiece instanceof King) {
            int colDiff = Math.abs(activePiece.col - activePiece.preCol);
            if (colDiff == 2) { // Castling move
                if (activePiece.col > activePiece.preCol) { // Kingside castling
                    for (Piece piece : simPieces) {
                        if (piece.col == 7 && piece.row == activePiece.row && piece instanceof Rook && !piece.moved) {
                            castlingPiece = piece;
                            // Update rook's column to the new position
                            castlingPiece.col = activePiece.col - 1;
                            break;
                        }
                    }
                } else { // Queenside castling
                    for (Piece piece : simPieces) {
                        if (piece.col == 0 && piece.row == activePiece.row && piece instanceof Rook && !piece.moved) {
                            castlingPiece = piece;
                            // Update rook's column to the new position
                            castlingPiece.col = activePiece.col + 1;
                            break;
                        }
                    }
                }
            }
        }

        if (activePiece.hittingPiece != null) {
            simPieces.remove(activePiece.hittingPiece.getIndex());
        }
        isValidSquare = true;
    }


    // Check for valid square considering king's safety
    if (canMove) {
        if (isIllegalKingMove(activePiece) || opponentCanCapturKing()) {
            isValidSquare = false;
        }
    }
}

    private boolean isIllegalKingMove(Piece king) {
        if (king.type == Type.KING) {
            for (Piece p : simPieces) {
                if (p != king && p.color != king.color && p.canMove(king.col, king.row)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKingInCheck(){
        Piece king = getKing(true);

        if(activePiece.canMove(king.col, king.row)){
            checkingPiece = activePiece;
            return true;
        }else{
            checkingPiece = null;
        }

        return false;
    }

    public boolean opponentCanCapturKing(){

        Piece king = getKing(false);

        for(Piece piece : simPieces){
            if(piece.color != king.color && piece.canMove(king.col, king.row)){
                return true;
            }
        }
        
        return false;
    }

    private boolean isCheckMate(){
        Piece king = getKing(true);
        if(kingCanMove(king)) {
            return false;
        }
        else{
            int colDiff = Math.abs(checkingPiece.col-king.col);
            int rowDiff = Math.abs(checkingPiece.row-king.row);

            if(colDiff==0) {
                // check piece is attacking vertically
                if(checkingPiece.row < king.row){
                    for(int row=checkingPiece.row; row<king.row; row++){
                        for(Piece piece : simPieces){
                            if(piece!=king && piece.color !=currentColor && piece.canMove(checkingPiece.col, row)) return false;
                        }
                    }
                }
                if(checkingPiece.row > king.row){
                    //below king
                    for(int row=checkingPiece.row; row>king.row; row--){
                        for(Piece piece : simPieces){
                            if(piece!=king && piece.color !=currentColor && piece.canMove(checkingPiece.col, row)) return false;
                        }
                    }
                }
            }else if(rowDiff==0) {
                //check piece is attacking horizontallly
                if(checkingPiece.col < king.col){
                    for(int col=checkingPiece.col; col<king.col; col++){
                        for(Piece piece : simPieces){
                            if(piece!=king && piece.color !=currentColor && piece.canMove(col, checkingPiece.row)) return false;
                        }
                    }
                }
                if(checkingPiece.col > king.col){
                    for(int col=checkingPiece.col; col>king.col; col--){
                        for(Piece piece : simPieces){
                            if(piece!=king && piece.color !=currentColor && piece.canMove(col, checkingPiece.row)) return false;
                        }
                    }
                }

            }else if(colDiff==rowDiff){
                //the checking piece is attacking diagonally
                if(checkingPiece.row < king.row){
                    if(checkingPiece.col < king.col){
                        for(int col=checkingPiece.col, row=checkingPiece.row; col<king.col; col++, row++){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)) return false;
                            }
                        }
                    }
                    if(checkingPiece.col> king.col){
                        for(int col=checkingPiece.col, row=checkingPiece.row; col<king.col; col--, row++){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)) return false;
                            }
                        }
                    }
                }

                if(checkingPiece.row > king.row){
                    if(checkingPiece.col < king.col){
                        for(int col=checkingPiece.col, row=checkingPiece.row; col<king.col; col++, row--){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)) return false;
                            }
                        }
                    }
                    if(checkingPiece.col> king.col){
                        for(int col=checkingPiece.col, row=checkingPiece.row; col>king.col; col--, row--){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)) return false;
                            }
                        }
                    }
                }
            }

        }
        return true;
    }
    private boolean kingCanMove(Piece king){
        if(isValidMove(king, -1,-1)) return true;
        if(isValidMove(king, 0,-1)) return true;
        if(isValidMove(king, 1,-1)) return true;
        if(isValidMove(king, -1,0)) return true;
        if(isValidMove(king, 1,0)) return true;
        if(isValidMove(king, -1,1)) return true;
        if(isValidMove(king, 0,1)) return true;
        if(isValidMove(king, 1,1)) return true;
        return false;
    }
    private boolean isValidMove(Piece king, int colPlus, int rowPlus){
        boolean isValidMove = false;

        //update king position
        king.col +=colPlus;
        king.row +=rowPlus;

        if(king.canMove(king.col, king.row)){
            if(king.hittingPiece !=null) simPieces.remove(king.hittingPiece.getIndex());
            if(!isIllegalKingMove(king)) isValidMove = true;
        }

        king.resetPosition();
        copyPieces(pieces, simPieces);

        return isValidMove;
    }

    private void checkCastling() {
        if (activePiece instanceof King) {
            int colDiff = Math.abs(activePiece.col - activePiece.preCol);
            if (colDiff == 2) { // Castling move
                if (activePiece.col > activePiece.preCol) { // Kingside castling
                    for (Piece piece : simPieces) {
                        if (piece.col == 7 && piece.row == activePiece.row && piece instanceof Rook && !piece.moved) {
                            castlingPiece = piece;
                            break;
                        }
                    }
                } else { // Queenside castling
                    for (Piece piece : simPieces) {
                        if (piece.col == 0 && piece.row == activePiece.row && piece instanceof Rook && !piece.moved) {
                            castlingPiece = piece;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void changePlayer(){
        if(currentColor == WHITE){
            currentColor =BLACK;
            for(Piece piece : pieces){
                if(piece.color == BLACK){
                    piece.twoStepped = false;
                }
            }
        }else{
            currentColor =WHITE;
            for(Piece piece : pieces){
                if(piece.color == WHITE){
                    piece.twoStepped = false;
                }
            }
        }
        activePiece = null;
    }

   private  boolean canPromote(){
       if(activePiece.type == Type.PAWN){
            if(currentColor == WHITE && activePiece.row==0 || currentColor==BLACK && activePiece.row ==7){
                promoPieces.clear();
                promoPieces.add(new Rook(currentColor, 9,2));
                promoPieces.add(new Knight(currentColor, 9,3));
                promoPieces.add(new Bishop(currentColor, 9,4));
                promoPieces.add(new Queen(currentColor, 9,5));

                return true;
            }
       }
       return false;
   }
   
    private void promoting() {
        if (mouse.pressed) {
            for (Piece p : promoPieces) {
                if (p.col == mouse.x / Board.SQUARE_SIZE && p.row == mouse.y / Board.SQUARE_SIZE) {
                    switch (p.type) {
                        case ROOK:
                            simPieces.add(new Rook(currentColor, activePiece.col, activePiece.row));
                            break;
                        case KNIGHT:
                            simPieces.add(new Knight(currentColor, activePiece.col, activePiece.row));
                            break;
                        case BISHOP:
                            simPieces.add(new Bishop(currentColor, activePiece.col, activePiece.row));
                            break;
                        case QUEEN:
                            simPieces.add(new Queen(currentColor, activePiece.col, activePiece.row));
                            break;
                        default:
                            break;
                    }
                    simPieces.remove(activePiece.getIndex());
                    copyPieces(simPieces, pieces);
                    activePiece = null;
                    promotion = false;
                    changePlayer();
                }
            }
        }
    }

    //will handle all the drawing
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;

        // Draw the board
        board.draw2DBoard(g2d);

        // Draw the pieces
        for (Piece p : pieces) {
            p.drawPiece(g2d);
        }

        // Highlight the active piece's valid moves
        if (activePiece != null) {
            if (canMove || opponentCanCapturKing()) {
                if (isIllegalKingMove(activePiece)) {
                    g2d.setColor(Color.red);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2d.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                } else {
                    g2d.setColor(Color.white);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2d.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }

            activePiece.drawPiece(g2d);
        }

        if (checkingPiece != null) {
            Piece king = getKing(false); 
            if (king != null) {
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(4)); 
                g2d.drawRect(king.col * Board.SQUARE_SIZE, king.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            }
        }

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
        g2d.setColor(Color.white);

        if (promotion) {
            // Promotion UI
            g2d.drawString("Promote to: ", 650, 100);
            for (Piece p : promoPieces) {
                g2d.drawImage(p.image, p.getX(p.col), p.getY(p.row), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
            }
        } else {
            if (currentColor == WHITE) {
                g2d.drawString("White's Turn", 640, 550);
                if (checkingPiece != null && checkingPiece.color == BLACK) {
                    g2d.setColor(Color.red);
                    g2d.drawString("The king is in check!", 640, 580);
                }
            } else {
                g2d.drawString("Black's Turn", 640, 50);
                if (checkingPiece != null && checkingPiece.color == WHITE) {
                    g2d.setColor(Color.red);
                    g2d.drawString("The king is in check!", 640, 80);
                }
            }
        }

         if (gameOver) {
             String s =currentColor==WHITE? " GameOver White wins": "GameOver Black Wins";

             g2d.setColor(Color.red);
             g2d.setFont(new Font("Book Antiqua", Font.BOLD, 40));
             g2d.drawString(s, WIDTH / 2 - 150, HEIGHT / 2);
         }
    }


}
