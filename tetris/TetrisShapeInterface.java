package tetris;

public interface TetrisShapeInterface {

    /**--------------------------------------------------------------------------
     * Turns myShape into a random shape
     *-------------------------------------------------------------------------*/
    void shapeGen();

    /**--------------------------------------------------------------------------
     * returns a TetrisShape rotated clockwise about the origin block
     * note:    does the rotation around the generated position, 
     *          and translates it back to the current position
     * algorithm is: row = col, col = -row
     * 
     * @return updated coordinates of the rotated shape
     *-------------------------------------------------------------------------*/
    TetrisShapeInterface rotateClock();

    /**--------------------------------------------------------------------------
     * returns a TetrisShape rotated counterclockwise about the origin block
     * note:    does the rotation around the generated position, 
     *          and translates it back to the current position
     * algorithm is: row = -col, col = row
     * 
     * @return updated coordinates of the rotated shape
     *-------------------------------------------------------------------------*/
    TetrisShapeInterface rotateCounter();

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved down 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    TetrisShapeInterface down();

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved left 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    TetrisShapeInterface left();

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved right 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    TetrisShapeInterface right();

    /**--------------------------------------------------------------------------
     * puts the generated shape at the starting point of the grid
     * 
     * @param width of the grid to center the piece
     *-------------------------------------------------------------------------*/
    void starting(int width);

    /**--------------------------------------------------------------------------
     * get the coordinates of the shape
     * 
     * @return coordinate array of this shape
     *-------------------------------------------------------------------------*/
    CoordinateInterface[] getCoordinates();

    /**--------------------------------------------------------------------------
     * figures out the bounds of the shape (used for clearing lines)
     * 
     * @return an array of size 2, with format [top, bot] 
     *-------------------------------------------------------------------------*/
    int[] shapeBounds();

    /**--------------------------------------------------------------------------
     * prints the shape into a 5x5 grid for testing purposes
     *-------------------------------------------------------------------------*/
    void printShape();

    /**--------------------------------------------------------------------------
     * Prints the coordinates of this shape (testing purposes)
     *-------------------------------------------------------------------------*/
    void printCoords();

}