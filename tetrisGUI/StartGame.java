package tetrisGUI;

//IMPORTS
import javafx.scene.canvas.Canvas;

/*
 * This class will start the game, taking as an argument GridGraphics in its constructor
 * There will be threading to handle the shape falling
 * It will call the various methods of GridGraphics to redraw the canvas
 * It will also handle events with keylisteners, etc
 */

public class StartGame implements Runnable, StartGameInterface{
    //Constants:
    
    //Member Variables:
    private boolean gameOver;
    private GridGraphics draw;
    
    
    public StartGame(Canvas gameGrid) {
        draw = new GridGraphics(gameGrid);
        gameOver = false;
        //System.out.println("DEBUG STATEMENT");
    }
    
    //ActionListeners go here:
    
    
    //setters
    public void setGameOver() {
        gameOver = true;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(!gameOver) {
            
        }
    }
    
}
