package tetrisGUI;

import javafx.scene.canvas.Canvas;
import tetris.Coordinate;
import tetris.GridInfo;
import tetris.TetrisShape;

public interface GridGraphicsInterface {
    
    /*
     * This class handles drawing the canvas and updating the shapes
     */
    
    /**--------------------------------------------------------------------------
     * Redraws the canvas according to the gridState, used at setup and
     * when pieces are dropped to check for line clears
     * 
     * @param gridState
     *             instance of GridInfo currently in play
     * @return the new canvas that we drew
     *-------------------------------------------------------------------------*/
    public Canvas drawGrid(GridInfo gridState);
    
    /**--------------------------------------------------------------------------
     * fills the Canvas's corresponding coordinates
     * Note: may support color param in the future
     * 
     * @param shape
     *             the shape to be drawn in 
     * @return updated canvas
     *-------------------------------------------------------------------------*/
    public Canvas fillCells(TetrisShape shape);
    
    /**--------------------------------------------------------------------------
     * removes the shape at the corresponding coordinates (after a fall etc)
     * Note: may support color param in the future
     * 
     * @param shape
     *             the shape to be removed
     * @return the updated canvas
     *-------------------------------------------------------------------------*/
    public Canvas clearCells(TetrisShape shape);
}