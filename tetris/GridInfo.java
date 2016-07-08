package tetris;
/*
 * This class is a grid object, which keeps track of the state of the grid
 * also updates the grid as things happen
 */

import TestCode.Tetris;

public class GridInfo implements GridInterface {
    private final char SET = '0';
    private final char EMPTY = '.';
    private final char FALLING = '*';
    private final char GHOST = 'x';

    // default, makes default sized grid
    private int width;
    private int height;
    private char[][] grid;

    // constructors
    public GridInfo() {
        this(Tetris.DEFAULT_GRID_WIDTH, Tetris.DEFAULT_GRID_HEIGHT);
    }

    public GridInfo(int w, int h) {
        height = h + Tetris.TOP_BITS;
        width = w;
        grid = new char[height][width];
        resetGrid();
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#checkShape(tetris.TetrisShape)
     */
    @Override
    public boolean checkShape(TetrisShapeInterface shape) {
        CoordinateInterface[] shapeCords = shape.getCoordinates();
        // loop through the coordinates in shape to check validity
        for (int i = 0; i < shapeCords.length; ++i) {
            int row = shapeCords[i].getRow();
            int col = shapeCords[i].getCol();

            // if it not valid, we can return false
            // first check if its in bounds
            if (row < 0 || row >= height) {
                return false;
            }
            if (col < 0 || col >= width) {
                return false;
            }

            // will use SET as taken blocks
            if (grid[row][col] == SET) {
                return false;
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#canSetShape(tetris.TetrisShape)
     */
    @Override
    public boolean canSetShape(TetrisShapeInterface shape) {
        // base case
        if (!checkShape(shape)) {
            return false;
        }
        // conditions for successful set shape:
        // 1. there are blocks directly under (can't place shape.down)
        TetrisShapeInterface check = shape.down();

        // if 1 level below is invalid, then we can set the shape
        if (!checkShape(check)) {
            return true;
        }
        // if 1 level is valid, we cannot set the shape yet
        return false;
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#setShape(tetris.TetrisShape)
     */
    @Override
    public int setShape(TetrisShape shape) {
        CoordinateInterface[] s = shape.getCoordinates();

        for (int i = 0; i < s.length; ++i) {
            int row = s[i].getRow();
            int col = s[i].getCol();

            grid[row][col] = SET;
        }
        return checkLines(shape);
    }

    /**------------------------------------------------------------------------
     * checks the lines after setting a piece, updates the grid accordingly
     * 
     * @param shape
     *            the shape we just placed
     * @return how many lines were cleared
     *-----------------------------------------------------------------------*/
    private int checkLines(TetrisShape shape) {
        System.out.println("Checking Lines...");
        int lines = 0;
        // only have to check the lines that were placed (ie rows that include
        // shape)
        int[] bounds = shape.shapeBounds();

        for (int row = bounds[shape.TOP]; row <= bounds[shape.BOT]; ++row) {
            if (checkLine(row)) {
                ++lines;
                clearLine(row);
            }
        }
        return lines;
    }

    /**------------------------------------------------------------------------
     * checks the row to see if it is full
     * 
     * @param row
     *            the row to be checked
     * @return whether the line is full
     *-----------------------------------------------------------------------*/
    private boolean checkLine(int row) {

        for (int i = 0; i < width; ++i) {
            if (grid[row][i] != SET) {
                return false;
            }
        }
        return true;
    }

    /**------------------------------------------------------------------------
     * clears the current row
     * 
     * @param row
     *            the row to be cleared
     *-----------------------------------------------------------------------*/
    private void clearLine(int row) {
        System.out.println("Clearing Lines");
        for (int r = row; r >= 0; --r) {
            for (int col = 0; col < width; ++col) {
                if (r == 0) {
                    // top row gets reset to empty
                    grid[r][col] = EMPTY;
                } else {
                    grid[r][col] = grid[r - 1][col];
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#gameOver()
     */
    @Override
    public void gameOver() {
        resetGrid();
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#getGrid()
     */
    @Override
    public char[][] getGrid() {
        return grid;
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#dropShape(tetris.TetrisShape)
     */
    @Override
    public int dropShape(TetrisShape shape) {

        // loop moving down until we can set the shape
        while (!canSetShape(shape)) {
            shape = shape.down();
        }
        // when we reach a settable location, set the shape and return
        // successful

        return setShape(shape);
    }

    /* (non-Javadoc)
     * @see tetris.GridInterface#printGrid(tetris.TetrisShape)
     */
    @Override
    public void printGrid(TetrisShapeInterface x) {
        System.out.println("Printing Grid...");
        CoordinateInterface[] shape = x.getCoordinates();
        
        TetrisShapeInterface ghost = getGhost(x);
        CoordinateInterface[] ghostShape = ghost.getCoordinates();
        
        boolean falling = false;

        for (int row = Tetris.TOP_BITS; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                CoordinateInterface checker = new Coordinate(row, col);

                // if falling block piece here, print * instead
                for (int i = 0; i < shape.length; ++i) {
                    if (checker.equals(shape[i])) {
                        System.out.print(FALLING);
                        falling = true;
                    }
                    else {
                        //prints ghostShape if not equal to fallingShape
                        if(checker.equals(ghostShape[i])) {
                            System.out.print(GHOST);
                            falling = true;
                        }
                    }
                }
                if (!falling) {
                    System.out.print(grid[row][col]);
                }
                falling = false;
            }
            // newline at end of the row
            System.out.println();
        }
    }
    
    /**------------------------------------------------------------------------
     * returns the coordinates of the shape at its lowest possible position 
     * helps with aiming, known as the ghost shape
     * 
     * @param fallingShape
     *            the shape being dropped
     * @return the ghost shape
     *-----------------------------------------------------------------------*/   
    private TetrisShapeInterface getGhost(TetrisShapeInterface fallingShape) {
        TetrisShapeInterface ghostShape = fallingShape.down();
        if(!checkShape(ghostShape)) {
            return fallingShape;
        }
        // loop moving down until we can set the shape
        while (!canSetShape(ghostShape)) {
            ghostShape = ghostShape.down();
        }

        return ghostShape;
    }

    /**------------------------------------------------------------------------
     * sets the grid up to start as empty spaces, denoted with EMPTY
     * ----------------------------------------------------------------------*/
    private void resetGrid() {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                grid[row][col] = EMPTY;
            }
        }
    }
}
