package tetrisGUI;

//IMPORTS
import javafx.scene.canvas.Canvas;
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
    
    public StartGame(Canvas gameGrid) {
        draw = new GridGraphics(gameGrid);
        gameOver = false;
        gridState = new GridInfo();
        width = gridState.getWidth();
        draw.constructGridLines();
        //System.out.println("DEBUG STATEMENT");
    }
    
    //ActionListeners go here:
    
    
    //setters
    public void setGameOver() {
        gameOver = true;
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
            draw.drawGrid(gridState);
            
            newShape();
            drawShape(currShape);
        }
    }
    
    private void newShape() {
        currShape = nextShape;
        currShape.starting(width);
        nextShape = new TetrisShape();
        nextShape.shapeGen();
        
        MainMenu.updateNext(nextShape);
    }
    
    @Override
    public void run() {
        boolean hasSwap = false;
        
        //initialize the grid state
        draw.drawGrid(gridState);
        gridState = new GridInfo();
        
        currShape = new TetrisShape();
        currShape.shapeGen();
        currShape.starting(width);
        
        nextShape = new TetrisShape();
        nextShape.shapeGen();
        MainMenu.updateNext(nextShape);
        
        drawShape(currShape);
        
        //main gameplay loop
        while(!gameOver) {
            System.out.println("DEBUG: Gone into run loop");
            
            gameOver = true;
        }
    }
    
    private void drawShape(TetrisShape shape) {
        draw.fillCells(gridState.getGhost(shape), ShapeType.GHOST);
        draw.fillCells(shape, ShapeType.FALLING);
    }
    
    private void clearShape(TetrisShape shape) {
        draw.clearCells(shape);
        draw.clearCells(gridState.getGhost(shape));
    }
    
}
