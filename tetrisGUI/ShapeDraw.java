package tetrisGUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tetris.Coordinate;
import tetris.TetrisShape;

/*
 * class used to draw the shape defined in swap or next
 */
public class ShapeDraw {
    //constants
    public static final int WIDTH_DIM = 5;
    public static final int HEIGHT_DIM = 6;
    public static final int SHAPE_UNIT = 20;
    public static final int SHAPE_GRIDLINE = 1;
    public static final int SHAPE_BOX = SHAPE_UNIT - SHAPE_GRIDLINE;
    public static final double SHAPE_BORDER = 0.5;
    
    private final Paint BACKGROUND_COLOR = Color.DARKSLATEGRAY;
    private final Paint GRIDLINE_COLOR = Color.BLACK;
    private final Paint BLOCK_COLOR = Color.GOLD;
    private final Paint BORDER_COLOR = Color.BLACK;
    
    private double totalHeightPx;
    private double totalWidthPx;
    
    //private Canvas background;
    private GraphicsContext gc;
    
    public ShapeDraw(Canvas grid) {
        //this.background = grid;
        this.gc = grid.getGraphicsContext2D();
        
        //constants
        totalHeightPx = HEIGHT_DIM * SHAPE_UNIT + SHAPE_GRIDLINE + 2*SHAPE_BORDER;
        totalWidthPx = WIDTH_DIM * SHAPE_UNIT + SHAPE_GRIDLINE + 2*SHAPE_BORDER;
    }
    
    /*
     * draws the shape
     */
    public void draw(TetrisShape shape) {
        TetrisShape centeredShape = getCentered(shape);
        Coordinate[] shapeType = centeredShape.getCoordinates();
        emptyGrid();
        gc.setFill(BLOCK_COLOR);
        
        for(int i = 0; i < shapeType.length; ++i) {
            int coordRow = shapeType[i].getRow();
            int coordCol = shapeType[i].getCol();
            
            double rowPix = coordRow * SHAPE_UNIT + SHAPE_GRIDLINE;
            double colPix = coordCol * SHAPE_UNIT + SHAPE_GRIDLINE;
            
            gc.fillRect(colPix, rowPix, SHAPE_BOX, SHAPE_BOX);
        }
    }
    
    /*
     * when nothing is in the hold
     */
    public void draw() {
        emptyGrid();
    }
    
    private void emptyGrid() {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, totalWidthPx, totalHeightPx);;
        drawBorder();
        //set color 
        gc.setFill(GRIDLINE_COLOR);
        //col borders
        for(int col = 0; col <= WIDTH_DIM; ++col) {
            double colPix = col * SHAPE_UNIT;
            gc.fillRect(colPix, 0, SHAPE_GRIDLINE, totalHeightPx);
        }

        //row borders
        for(int row = 0; row <= HEIGHT_DIM; ++row) {
            double rowPix = row * SHAPE_UNIT;
            gc.fillRect(0, rowPix, totalWidthPx, SHAPE_GRIDLINE);
        }
    }
    
    private void drawBorder() {
        gc.setFill(BORDER_COLOR);
        
        //constants
        double totalHeightPx = HEIGHT_DIM * SHAPE_UNIT + SHAPE_GRIDLINE + 2*SHAPE_BORDER;
        double totalWidthPx = WIDTH_DIM * SHAPE_UNIT + SHAPE_GRIDLINE + 2*SHAPE_BORDER;
        
        gc.fillRect(0, 0, SHAPE_BORDER, totalHeightPx);
        gc.fillRect(0, 0, totalWidthPx, SHAPE_BORDER);
        gc.fillRect(0, totalHeightPx-SHAPE_BORDER, totalWidthPx, SHAPE_BORDER);
        gc.fillRect(totalWidthPx-SHAPE_BORDER, 0, SHAPE_BORDER, totalHeightPx);
    }
    
    private TetrisShape getCentered(TetrisShape shape) {
        shape = getOrigin(shape);
        Coordinate[] shapeCoords = shape.getCoordinates();
        Coordinate[] newCoords = new Coordinate[shapeCoords.length];
        int rOffset = HEIGHT_DIM/2 -1;
        int cOffset = WIDTH_DIM/2;
        
        for(int i = 0; i < shapeCoords.length; ++i) {
            int row = shapeCoords[i].getRow();
            int col = shapeCoords[i].getCol();
            
            int updateRow = row + rOffset;
            int updateCol = col + cOffset;
            newCoords[i] = new Coordinate(updateRow, updateCol);
        }
        
        
        return new TetrisShape(newCoords);
    }
    
    private TetrisShape getOrigin(TetrisShape shape) {
        Coordinate[] shapeCords = shape.getCoordinates();
        Coordinate[] newCoords = new Coordinate[shapeCords.length];
        int rOffset = shapeCords[0].getRow();
        int cOffset = shapeCords[0].getCol();
        
        for(int i = 0; i < shapeCords.length; ++i) {
            int r = shapeCords[i].getRow();
            int c = shapeCords[i].getCol();
            
            int updateRow = r - rOffset;
            int updateCol = c - cOffset;
            
            newCoords[i] = new Coordinate(updateRow, updateCol);
        }
        
        return new TetrisShape(newCoords);
    }
    
}
