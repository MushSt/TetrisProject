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

public class StartGame implements StartGameInterface{
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
    private TetrisShape swapShape;
    private TetrisShape nextShape;
    
    private int width;
    
    private boolean hasSwap;
    
    //constructor
    public StartGame(Canvas gameGrid) {
        draw = new GridGraphics(gameGrid);
        
        gridState = new GridInfo();
        
        width = gridState.getWidth();
        hasSwap = false;
        gameOver = true;
    }
    
    
    //KeyCode from a new command is detected (keylistener stuff)
    public synchronized void newCommand(KeyCode key) {
        //System.out.println("DETECTED KEYCODE!!!!!!!!!***********");
        //base case, if game over, user commands should not work
        if(gameOver) {
            System.out.println("DEBUG: game over, ignore key");
            return;
        }
        TetrisShape tempShape = currShape;
        
        switch(key) {
            case SPACE:
                handleDroppedShape();
                return;
            case LEFT:
                tempShape = currShape.left();
                break;
            case RIGHT:
                tempShape = currShape.right();
                break;
            case DOWN:
                tempShape = currShape.down();
                break;
            case UP:
                tempShape = tempShape.rotateClock();
                break;
            case X:
                tempShape = tempShape.rotateClock();
                break;
            case Z:
                tempShape = tempShape.rotateCounter();
                break;
            case SHIFT:
                //handle swapping
                handleSwapShape();
                return;
            default:
                return;
        }

        if(gridState.checkShape(tempShape)) {
            clearShape(currShape);
            currShape = tempShape;
            drawShape(currShape);
        }
        else{
            System.out.println("failed to move in that direction");
        }
        
    }
    
    private void handleSwapShape() {
        if(!hasSwap) {
            clearShape(currShape);
            
            swapShape = currShape;
            newShape(false);
            
            MainMenu.updateSwap(swapShape);
        }
        else {
            TetrisShape tempShape = swapShape;
            clearShape(currShape);
            
            swapShape = currShape;
            currShape = tempShape;
            currShape.starting(width);
            
            MainMenu.updateSwap(swapShape);
            
            drawShape(currShape);
        }
        hasSwap = true;
        
    }
    
    //initial setup for when run() is called. 
    private void init(boolean newGame) {
        if(newGame) {
            gridState = new GridInfo();
        }
        //draw the grid
        draw.drawGrid(gridState);
        
        //get a new shape, and next shape
        newShape(true);
        
        //game is now running
        gameOver = false;
    }
    
    
    
    //timer setting
    public void dropTick() {
        //check if can drop shape by 1, if can't set shape and get a new one
        //System.out.println("DEBUG: ticked");
        System.out.println("Inside dropTick");
        if(gridState.checkShape(currShape.down())) {
            System.out.println("Inside dropTick1");
            clearShape(currShape);
            currShape = currShape.down();
            drawShape(currShape);
        }
        else{
            System.out.println("Inside dropTick2start");
            handleDroppedShape();
            System.out.println("Inside dropTick2end");
        }
    }
    
    //after a shape has been dropped, handles the details
    private void handleDroppedShape() {
        int lines = gridState.dropShape(currShape);
        System.out.println(lines);

        MainMenu.updateLinesCleared(lines);
        MainMenu.updateScoreDrop();
        
        draw.drawGrid(gridState);
        
        //currShape = nextShape, update mainmenu, draw shape
        newShape(false);

        checkGameState();
    }

    
    //generate new shape, update next shape, draw the shape on the grid
    //input is true if next is not initialized yet
    private void newShape(boolean hasNext) {
        if(hasNext) {
            currShape = new TetrisShape();
            currShape.shapeGen();
        }
        else {
            currShape = nextShape;
        }
        currShape.starting(width);
        
        nextShape = new TetrisShape();
        nextShape.shapeGen();
        MainMenu.updateNext(nextShape);
        
        drawShape(currShape);
    }
    

    
    //initialize for a new game
    public void newGame() {
        init(true);
    }

    //flag for game over, and stops the timer
    public void setGameOver() {
        gameOver = true;
        MainMenu.pauseTimer();
    }
    
    //draws empty grid
    public void drawGrid() {
        draw.drawGrid(gridState);
    }    
    
    //returns whether the game is in play
    public boolean isInPlay() {
        return !gameOver;
    }
    
    //draws the shape and its ghost on the grid
    private void drawShape(TetrisShape shape) {
        draw.fillCells(gridState.getGhost(shape), ShapeType.GHOST);
        draw.fillCells(shape, ShapeType.FALLING);
    }
    
    //clears the shape and its ghost from the grid
    private void clearShape(TetrisShape shape) {
        draw.clearCells(shape);
        draw.clearCells(gridState.getGhost(shape));
    }
    
    //checks if we can continue the game
    private void checkGameState() {
        System.out.println("DEBUG: CHECK STATE");
        if(!gridState.checkShape(currShape)) {
            System.out.println("gameover");
            setGameOver();
        }
    }
    
}
