package tetris;
/*
 * This class is a grid object, which keeps track of the state of the grid
 * also updates the grid as things happen
 */

import javafx.scene.paint.Paint;
import tetrisGUI.GridGraphics;
import tetrisGUI.MainMenu;

public class GridInfo implements GridInterface {
    public final Paint EMPTY = GridGraphics.BACKGROUND_COLOR;
    
    public static final int TOP_BITS = 2;

    // default, makes default sized grid
    private int width;
    private int height;
    private Paint[][] grid;

    // constructors
    public GridInfo() {
        this(MainMenu.DEFAULT_GRIDWIDTH, MainMenu.DEFAULT_GRIDHEIGHT);
    }

    public GridInfo(int w, int h) {
        height = h + TOP_BITS;
        width = w;
        grid = new Paint[height][width];
        resetGrid();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
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
            
            // will use SET as taken blocks
            if (grid[row][col] != EMPTY) {
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

            grid[row][col] = shape.getColor();
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
            if (grid[row][i] == EMPTY) {
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

    /**------------------------------------------------------------------------
     * resets the game board after the game ends
     *-----------------------------------------------------------------------*/
    public void gameOver() {
        resetGrid();
    }

    /**-----------------------------------------------------------------------
     * gets the current grid
     * 
     * @return the current grid as a Paint array
     *-----------------------------------------------------------------------*/
    public Paint[][] getGrid() {
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
            //System.out.println("LOOPING");
            shape = shape.down();
        }
        // when we reach a settable location, set the shape and return
        // successful

        return setShape(shape);
    }

    
    /**------------------------------------------------------------------------
     * returns the coordinates of the shape at its lowest possible position 
     * helps with aiming, known as the ghost shape
     * 
     * @param fallingShape
     *            the shape being dropped
     * @return the ghost shape
     *-----------------------------------------------------------------------*/   
    public TetrisShape getGhost(TetrisShape fallingShape) {
        TetrisShape ghostShape = fallingShape.down();
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
