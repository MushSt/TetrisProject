package tetrisGUI;

//IMPORTS
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import tetris.GridInfo;
import tetris.TetrisShape;

/*
 * This class will start the game, taking as an argument GridGraphics in its constructor
 * There will be threading to handle the shape falling
 * It will call the various methods of GridGraphics to redraw the canvas
 * It will also handle events with keylisteners, etc
 */

public class StartGame implements Runnable, StartGameInterface{
    //enum for shape
    public enum ShapeType{
        FALLING, GHOST
    }
    
    //Constants:
    
    //Member Variables:
    private boolean gameOver;
    private GridGraphics draw;
    private static GridInfo gridState;
    
    private TetrisShape currShape;
    private TetrisShape SwapShape;
    private TetrisShape nextShape;
    
    private int width;
    private boolean playing;
    
    public StartGame(Canvas gameGrid) {
        draw = new GridGraphics(gameGrid);
        setup();
        width = gridState.getWidth();

        
    }
    
    //ActionListeners go here:
    
    
    //setters
    public void setGameOver() {
        gameOver = true;
    }
    
    private void setup() {
        gridState = new GridInfo();
        draw.constructGridLines();
        draw.drawGrid(gridState);
        
        gameOver = false;
    }
    
    //timer setting
    public void dropTick() {
        //check if can drop shape by 1, if can't set shape and get a new one
        System.out.println("DEBUG: ticked");
        if(gridState.checkShape(currShape.down())) {
            clearShape(currShape);
            currShape = currShape.down();
            drawShape(currShape);
        }
        else{
            int lines = gridState.setShape(currShape);
            MainMenu.updateLinesCleared(lines);
            MainMenu.updateScoreDrop();
            draw.drawGrid(gridState);
            
            newShape();
            
            //check if game can continue
            checkGameState();
            
            drawShape(currShape);
        }
    }
    
    public boolean isInPlay() {
        return playing;
    }
    
    private void newShape() {
        currShape = nextShape;
        currShape.starting(width);
        nextShape = new TetrisShape();
        nextShape.shapeGen();
        
        MainMenu.updateNext(nextShape);
    }
    
    public synchronized void newCommand(KeyCode key) {
        //System.out.println("DETECTED KEYCODE!!!!!!!!!***********");
        if(key.isWhitespaceKey()) {
            if(gameOver) {
                return;
            }
            gridState.dropShape(currShape);
            droppedSetup();
        }
    }
    
    public void newGame() {
        setup();
        runSetup();
    }
    
    private void runSetup() {
        boolean hasSwap = false;
        playing = true;
        
        //initialize the grid state
        gridState = new GridInfo();
        
        droppedSetup();
        
    }
    
    private void droppedSetup() {
        draw.drawGrid(gridState);
        currShape = new TetrisShape();
        currShape.shapeGen();
        currShape.starting(width);
        
        nextShape = new TetrisShape();
        nextShape.shapeGen();
        MainMenu.updateNext(nextShape);
        MainMenu.updateScoreDrop();
        
        drawShape(currShape);
        checkGameState();
    }
    
    @Override
    public void run() {
        runSetup();
        
        //debug
        int counter = 0;
        
        //main gameplay loop
        while(!gameOver) {
            if(counter < 5) {
                System.out.println(counter);
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
        
        //force timer to stop
        System.out.println("attempting stop");
        MainMenu.pauseTimer();
        MainMenu.gameOver();
        return;
    }
    
    private void drawShape(TetrisShape shape) {
        draw.fillCells(gridState.getGhost(shape), ShapeType.GHOST);
        draw.fillCells(shape, ShapeType.FALLING);
    }
    
    private void clearShape(TetrisShape shape) {
        draw.clearCells(shape);
        draw.clearCells(gridState.getGhost(shape));
    }
    
    private void checkGameState() {
        System.out.println("DEBUG: CHECK STATE");
        if(!gridState.checkShape(currShape)) {
            System.out.println("gameover");
            setGameOver();
        }
    }
    
}
