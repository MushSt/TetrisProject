package tetrisGUI;

/*
 * This class handles drawing the canvas and updating the shapes
 */

//IMPORTS
import javafx.concurrent.*;
import javafx.scene.canvas.*;
import tetris.Coordinate;
import tetris.GridInfo;
import tetris.TetrisShape;
import javafx.scene.*;

public class GridGraphics implements GridGraphicsInterface {
    //Constants:
    public static final int BLOCK_PIXELS = 27; //pixels in a block
    public static final int BORDER_PIXELS = 3; //pixels for grid lines
    

    //Member Variables:
    private int width;
    private int height;
    
    private Canvas gameGrid;
    private GraphicsContext gc;
    
    
    public GridGraphics(Canvas gameGrid) {
        //uses default width/height params
        this(gameGrid, MainMenu.DEFAULT_GRIDWIDTH, MainMenu.DEFAULT_GRIDHEIGHT);
    }
    
    public GridGraphics(Canvas gameGrid, int userWidth, int userHeight) {
        width = userWidth;
        height = userHeight;
        
        this.gameGrid = gameGrid;
        this.gc = gameGrid.getGraphicsContext2D();
    }
    
    /**--------------------------------------------------------------------------
     * Redraws the canvas according to the gridState, used at setup and
     * when pieces are dropped to check for line clears
     * 
     * @param gridState
     *             instance of GridInfo currently in play
     * @return the new canvas that we drew
     *-------------------------------------------------------------------------*/
    public synchronized Canvas drawGrid(GridInfo gridState) {
        
        
        return this.gc.getCanvas();
    }
    
    /**--------------------------------------------------------------------------
     * fills the Canvas's corresponding coordinates
     * Note: may support color param in the future
     * 
     * @param shape
     *             the shape to be drawn in 
     * @return updated canvas
     *-------------------------------------------------------------------------*/
    public synchronized Canvas fillCells(TetrisShape shape) {
        
        
        return this.gc.getCanvas();
    }
    
    /**--------------------------------------------------------------------------
     * removes the shape at the corresponding coordinates (after a fall etc)
     * Note: may support color param in the future
     * 
     * @param shape
     *             the shape to be removed
     * @return the updated canvas
     *-------------------------------------------------------------------------*/
    public synchronized Canvas clearCells(TetrisShape shape) {
        
        return this.gc.getCanvas();
    }
}
