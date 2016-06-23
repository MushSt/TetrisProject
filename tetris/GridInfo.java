package tetris;
/*
 * This class is a grid object, which keeps track of the state of the grid
 * also updates the grid as things happen
 */

public class GridInfo {

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

    /**--------------------------------------------------------------------------
     * checks if the shape can be placed, by checking the shape's 4 coordinates
     * 
     * @param shape
     *             the shape to be checked
     * @return whether the coordinates of the current shape are valid
     *-------------------------------------------------------------------------*/
    public boolean checkShape(TetrisShape shape) {
        Coordinate[] shapeCords = shape.getCoordinates();
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

            // will use 'X' as taken blocks
            if (grid[row][col] == 'X') {
                return false;
            }
        }
        return true;
    }

    /**--------------------------------------------------------------------------
     * checks if we can set the shape onto the grid
     * 
     * @param shape
     *             the shape to be set
     * @return whether the shape can be set
     *-------------------------------------------------------------------------*/
    public boolean canSetShape(TetrisShape shape) {
        // base case
        if (!checkShape(shape)) {
            return false;
        }
        // conditions for successful set shape:
        // 1. there are blocks directly under (can't place shape.down)
        TetrisShape check = shape.down();

        // if 1 level below is invalid, then we can set the shape
        if (!checkShape(check)) {
            return true;
        }
        // if 1 level is valid, we cannot set the shape yet
        return false;
    }

    /**------------------------------------------------------------------------
     * set the shape onto the grid
     * 
     * @param shape
     *            the current shape
     * @return how many lines were cleared after setting the shape
     *-----------------------------------------------------------------------*/
    public int setShape(TetrisShape shape) {
        Coordinate[] s = shape.getCoordinates();

        for (int i = 0; i < s.length; ++i) {
            int row = s[i].getRow();
            int col = s[i].getCol();

            grid[row][col] = 'X';
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
            if (grid[row][i] != 'X') {
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
                    grid[r][col] = '.';
                } else {
                    grid[r][col] = grid[r - 1][col];
                }
            }
        }
    }

    /**------------------------------------------------------------------------
     * resets the game board after the game ends
     *-----------------------------------------------------------------------*/
    public void gameOver() {
        resetGrid();
    }

    /**
     * -----------------------------------------------------------------------
     * - gets the current grid
     * 
     * @return the current grid as a char array
     *-----------------------------------------------------------------------*/
    public char[][] getGrid() {
        return grid;
    }

    /**------------------------------------------------------------------------
     * drops the current shape to the lowest point it can go note: assumes
     * that the shape's current position is valid
     * 
     * @param shape
     *            the shape to be dropped
     * @return how many lines were cleared by the shape
     *-----------------------------------------------------------------------*/
    public int dropShape(TetrisShape shape) {

        // loop moving down until we can set the shape
        while (!canSetShape(shape)) {
            shape = shape.down();
        }
        // when we reach a settable location, set the shape and return
        // successful

        return setShape(shape);
    }

    /**------------------------------------------------------------------------
     * prints the current grid, minus the offset rows the falling blocks are
     * printed as '*'
     * 
     * @param x
     *            current falling shape that is to be printed with '*'
     *-----------------------------------------------------------------------*/
    public void printGrid(TetrisShape x) {
        System.out.println("Printing Grid...");
        Coordinate[] shape = x.getCoordinates();
        boolean falling = false;

        for (int row = Tetris.TOP_BITS; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                Coordinate checker = new Coordinate(row, col);

                // if falling block piece here, print * instead
                for (int i = 0; i < shape.length; ++i) {
                    if (checker.equals(shape[i])) {
                        System.out.print('*');
                        falling = true;
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
     * sets the grid up to start as empty spaces, denoted with '.'
     * ----------------------------------------------------------------------*/
    private void resetGrid() {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                grid[row][col] = '.';
            }
        }
    }
}
