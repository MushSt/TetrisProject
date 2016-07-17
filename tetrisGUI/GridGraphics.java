package tetrisGUI;

/*
 * This class handles drawing the canvas and updating the shapes
 */

//IMPORTS
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tetris.Coordinate;
import tetris.GridInfo;
import tetris.TetrisShape;
import tetrisGUI.StartGame.ShapeType;

public class GridGraphics implements GridGraphicsInterface {
    //Constants:
    public static final double UNIT_PIXELS = 30;
    public static final double BORDER_PIXELS = 1; //pixels for grid lines
    public static final double BLOCK_PIXELS = UNIT_PIXELS - BORDER_PIXELS; //pixels in a block
    
    public static final Paint MESSAGE_COLOR = Color.GOLD;
    public static final Paint GHOST_COLOR = Color.GRAY;
    public static final Paint BACKGROUND_COLOR = Color.BLACK;
    public static final Paint GRIDLINE_COLOR = Color.BLACK;

    //Member Variables:
    private int width; //width in units
    private int height; //height in units
    private double pixWidth; //width in pixels
    private double pixHeight; //height in pixels
    
    private GraphicsContext gc;
    
    
    public GridGraphics(Canvas gameGrid) {
        //uses default width/height params
        this(gameGrid, MainMenu.DEFAULT_GRIDWIDTH, MainMenu.DEFAULT_GRIDHEIGHT);
    }
    
    public GridGraphics(Canvas gameGrid, int userWidth, int userHeight) {
        width = userWidth;
        height = userHeight;
        
        pixWidth = userWidth * (UNIT_PIXELS) + BORDER_PIXELS;
        pixHeight = userHeight * (UNIT_PIXELS) + BORDER_PIXELS;
        
        this.gc = gameGrid.getGraphicsContext2D();
    }
    
    /**--------------------------------------------------------------------------
     * Draws the grid lines in the grid, making an empty grid
     * Only called during the setup
     * 
     * @return the new canvas that we drew
     *-------------------------------------------------------------------------*/
    private synchronized Canvas constructGridLines() {
        //set color 
        gc.setFill(GRIDLINE_COLOR);
        //col borders
        for(int col = 0; col <= width; ++col) {
            double colPix = col * UNIT_PIXELS;
            gc.fillRect(colPix, 0, BORDER_PIXELS, pixHeight);
        }

        //row borders
        for(int row = 0; row <= height; ++row) {
            double rowPix = row * UNIT_PIXELS;
            gc.fillRect(0, rowPix, pixWidth, BORDER_PIXELS);
        }
        System.out.println("DEBUG: Grid Drawn");
        
        return this.gc.getCanvas();
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
        constructGridLines();
        
        Paint[][] grid = gridState.getGrid();
        int buffer = GridInfo.TOP_BITS;
        //for loop through the grid
        for(int row = buffer; row < height+buffer; ++row) {
            for(int col = 0; col < width; ++col) {
                //case 1: spot is empty
                double colPix = BORDER_PIXELS + (UNIT_PIXELS * col);
                double rowPix = BORDER_PIXELS + (UNIT_PIXELS * (row-GridInfo.TOP_BITS));
                
                gc.setFill(grid[row][col]);
                gc.fillRect(colPix, rowPix, BLOCK_PIXELS, BLOCK_PIXELS);
            }
        }
        
        return this.gc.getCanvas();
    }
    
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
    public synchronized Canvas fillCells(TetrisShape shape, ShapeType type) {
        //resolve color for type
        if(type == StartGame.ShapeType.FALLING) {
            gc.setFill(shape.getColor());
            
        }
        else {
            gc.setFill(GHOST_COLOR);
        }
        
        Coordinate[] shapeCoords = shape.getCoordinates();
        int size = shapeCoords.length;
        
        //loop thru 
        for(int i = 0; i < size; ++i) {
            int row = shapeCoords[i].getRow() - GridInfo.TOP_BITS;
            int col = shapeCoords[i].getCol();
            
            double rowPix = row * UNIT_PIXELS + BORDER_PIXELS;
            double colPix = col * UNIT_PIXELS + BORDER_PIXELS;
            
            gc.fillRect(colPix, rowPix, BLOCK_PIXELS, BLOCK_PIXELS);
        }
        
        return this.gc.getCanvas();
    }
    
    /**--------------------------------------------------------------------------
     * removes the shape at the corresponding coordinates (after a fall etc)
     * 
     * @param shape
     *             the shape to be removed
     * @return the updated canvas
     *-------------------------------------------------------------------------*/
    public synchronized Canvas clearCells(TetrisShape shape) {
        //fill is background color to erase the shape
        gc.setFill(BACKGROUND_COLOR);
        
        Coordinate[] shapeCoords = shape.getCoordinates();
        int size = shapeCoords.length;
        
        //loop thru 
        for(int i = 0; i < size; ++i) {
            int row = shapeCoords[i].getRow() - GridInfo.TOP_BITS;
            int col = shapeCoords[i].getCol();
            
            double rowPix = row * UNIT_PIXELS + BORDER_PIXELS;
            double colPix = col * UNIT_PIXELS + BORDER_PIXELS;
            
            gc.fillRect(colPix, rowPix, BLOCK_PIXELS, BLOCK_PIXELS);
        }
        
        return this.gc.getCanvas();
    }
    
    public void drawGameOver() {
        gc.setFill(MESSAGE_COLOR);
        
        Canvas canvas = gc.getCanvas();
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        Image gameOverMsg;
        try {
            gameOverMsg = new Image("resources/gameover.png");
            
            double picWidth = gameOverMsg.getWidth();
            double picHeight = gameOverMsg.getHeight();
            
            double topLeftRow = height/2 - picHeight/2;
            double topLeftCol = width/2 - picWidth/2;
            
            System.out.println("Attempting to draw image");
        
            gc.drawImage(gameOverMsg, topLeftCol, topLeftRow);
        }
        catch(Exception e) {
            System.out.println("DEBUG: Error in finding image");
        }
        
        
    }
}
