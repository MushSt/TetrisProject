package tetris;
/*
 * Coordinates object, (row,col) 
 * row, col because dealing with a matrix, easier to 
 * work with 
 */

public class Coordinate implements CoordinateInterface {

    private int row;
    private int col;

    // no argument constructor sets a coord of 0,0
    public Coordinate() {
        this(0, 0);
    }

    public Coordinate(int r, int c) {
        row = r;
        col = c;
    }

    // public mutators
    /* (non-Javadoc)
     * @see tetris.CoordinateInterface#setRow(int)
     */
    @Override
    public void setRow(int x) {
        row = x;
    }

    /* (non-Javadoc)
     * @see tetris.CoordinateInterface#setCol(int)
     */
    @Override
    public void setCol(int y) {
        col = y;
    }

    // public accessors
    /* (non-Javadoc)
     * @see tetris.CoordinateInterface#getRow()
     */
    @Override
    public int getRow() {
        return row;
    }

    /* (non-Javadoc)
     * @see tetris.CoordinateInterface#getCol()
     */
    @Override
    public int getCol() {
        return col;
    }

    /* (non-Javadoc)
     * @see tetris.CoordinateInterface#equals(tetris.CoordinateInterface)
     */
    @Override
    public boolean equals(Coordinate x) {
        int xrow = x.getRow();
        int xcol = x.getCol();

        if (xrow == row && xcol == col) {
            return true;
        } else {
            return false;
        }
    }

}
