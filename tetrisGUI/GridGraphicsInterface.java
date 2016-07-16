package tetrisGUI;

import javafx.scene.canvas.Canvas;
import tetris.GridInfo;
import tetris.TetrisShape;
import tetrisGUI.StartGame.ShapeType;

public interface GridGraphicsInterface {
    
    
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
     * @param type
     *             the type of shape we're filling in (falling vs ghost)
     * @return updated canvas
     *-------------------------------------------------------------------------*/
    Canvas fillCells(TetrisShape shape, ShapeType type);
    
    /**--------------------------------------------------------------------------
     * removes the shape at the corresponding coordinates (after a fall etc)
     * 
     * @param shape
     *             the shape to be removed
     * @return the updated canvas
     *-------------------------------------------------------------------------*/
    Canvas clearCells(TetrisShape shape);
}