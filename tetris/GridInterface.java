package tetris;

public interface GridInterface {

    /**--------------------------------------------------------------------------
     * checks if the shape can be placed, by checking the shape's 4 coordinates
     * 
     * @param shape
     *             the shape to be checked
     * @return whether the coordinates of the current shape are valid
     *-------------------------------------------------------------------------*/
    boolean checkShape(TetrisShapeInterface shape);

    /**--------------------------------------------------------------------------
     * checks if we can set the shape onto the grid
     * 
     * @param shape
     *             the shape to be set
     * @return whether the shape can be set
     *-------------------------------------------------------------------------*/
    boolean canSetShape(TetrisShapeInterface shape);

    /**------------------------------------------------------------------------
     * set the shape onto the grid
     * 
     * @param shape
     *            the current shape
     * @return how many lines were cleared after setting the shape
     *-----------------------------------------------------------------------*/
    int setShape(TetrisShape shape);

    /**------------------------------------------------------------------------
     * resets the game board after the game ends
     *-----------------------------------------------------------------------*/
    void gameOver();

    /**
     * -----------------------------------------------------------------------
     * - gets the current grid
     * 
     * @return the current grid as a char array
     *-----------------------------------------------------------------------*/
    char[][] getGrid();

    /**------------------------------------------------------------------------
     * drops the current shape to the lowest point it can go note: assumes
     * that the shape's current position is valid
     * 
     * @param shape
     *            the shape to be dropped
     * @return how many lines were cleared by the shape
     *-----------------------------------------------------------------------*/
    int dropShape(TetrisShape shape);

    /**------------------------------------------------------------------------
     * prints the current grid, minus the offset rows the falling blocks are
     * printed as '*'
     * 
     * @param x
     *            current falling shape that is to be printed with FALLING
     *-----------------------------------------------------------------------*/
    void printGrid(TetrisShapeInterface x);

}