package tetris;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class TetrisShape implements TetrisShapeInterface {
    // index 0 of each shape is (0,0), which we will use as the center of the
    // shapes
    final private Coordinate[] shapeI = { new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(-1, 0),
            new Coordinate(2, 0) };
    final private Coordinate[] shapeL = { new Coordinate(0, 0), new Coordinate(1, 1), new Coordinate(1, 0),
            new Coordinate(-1, 0) };
    final private Coordinate[] shapeJ = { new Coordinate(0, 0), new Coordinate(1, -1), new Coordinate(1, 0),
            new Coordinate(-1, 0) };
    final private Coordinate[] shapeT = { new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, -1),
            new Coordinate(-1, 0) };
    final private Coordinate[] shapeO = { new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(0, -1),
            new Coordinate(-1, -1) };
    final private Coordinate[] shapeS = { new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(0, -1),
            new Coordinate(-1, 1) };
    final private Coordinate[] shapeZ = { new Coordinate(0, 0), new Coordinate(-1, -1), new Coordinate(-1, 0),
            new Coordinate(0, 1) };
    final public int SHAPE_SIZE = 4;
    final public int NUMSHAPES = 7;
    final private int PRINT_OFFSET = 2;
    final public int TOP = 0;
    final public int BOT = 1;
    final private int BOUNDS = 2;
    
    final private Paint I_COLOR = Color.DARKTURQUOISE;
    final private Paint L_COLOR = Color.BURLYWOOD;
    final private Paint J_COLOR = Color.CHARTREUSE;
    final private Paint T_COLOR = Color.CORNFLOWERBLUE;
    final private Paint O_COLOR = Color.ORANGE;
    final private Paint S_COLOR = Color.YELLOWGREEN;
    final private Paint Z_COLOR = Color.PALEVIOLETRED;

    private Coordinate[] myShape;
    private Paint shapeColor;

    // null argument constructor does nothing
    public TetrisShape() {
        myShape = new Coordinate[SHAPE_SIZE];
    }

    // constructor to make a new shape object based on previous one
    public TetrisShape(Coordinate[] shape, Paint color) {
        myShape = new Coordinate[SHAPE_SIZE];
        shapeColor = color;
        deepCopy(shape);
    }

    public Paint getColor() {
        return shapeColor;
    }
    
    /**--------------------------------------------------------------------------
     * Turns myShape into a random shape
     *-------------------------------------------------------------------------*/
    public void shapeGen() {
        Random rng = new Random(System.nanoTime());
        int shapeNum = rng.nextInt(NUMSHAPES);

        switch (shapeNum) {
            case 0:
                deepCopy(shapeI);
                shapeColor = I_COLOR;
                break;
            case 1:
                deepCopy(shapeL);
                shapeColor = L_COLOR;
                break;
            case 2:
                deepCopy(shapeJ);
                shapeColor = J_COLOR;
                break;
            case 3:
                deepCopy(shapeT);
                shapeColor = T_COLOR;
                break;
            case 4:
                deepCopy(shapeO);
                shapeColor = O_COLOR;
                break;
            case 5:
                deepCopy(shapeS);
                shapeColor = S_COLOR;
                break;
            case 6:
                deepCopy(shapeZ);
                shapeColor = Z_COLOR;
                break;
            default:
                System.out.println("bad shape error: " + shapeNum);
                break;
        }
        
    }

    /**--------------------------------------------------------------------------
     * returns a TetrisShape rotated clockwise about the origin block
     * note:    does the rotation around the generated position, 
     *          and translates it back to the current position
     * algorithm is: row = col, col = -row
     * 
     * @return updated coordinates of the rotated shape
     *-------------------------------------------------------------------------*/
    public TetrisShape rotateClock() {
        // algorithm only works for when the origin is centered, so we calculate
        // and
        // save it
        int rOffset = myShape[0].getRow();
        int cOffset = myShape[0].getCol();

        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // do rotation at origin and then change it back (-offset, +offset)
            int newRow = myShape[i].getCol() - cOffset + rOffset;
            int newCol = -(myShape[i].getRow() - rOffset) + cOffset;
            newShape[i] = new Coordinate(newRow, newCol);
        }

        return new TetrisShape(newShape, shapeColor);
    }

    /**--------------------------------------------------------------------------
     * returns a TetrisShape rotated counterclockwise about the origin block
     * note:    does the rotation around the generated position, 
     *          and translates it back to the current position
     * algorithm is: row = -col, col = row
     * 
     * @return updated coordinates of the rotated shape
     *-------------------------------------------------------------------------*/
    public TetrisShape rotateCounter() {
        // algorithm only works for when the origin is centered, so we calculate
        // and
        // save it
        int rOffset = myShape[0].getRow();
        int cOffset = myShape[0].getCol();

        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // do rotation at origin and then change it back (-offset, +offset)
            int newRow = -(myShape[i].getCol() - cOffset) + rOffset;
            int newCol = myShape[i].getRow() - rOffset + cOffset;
            newShape[i] = new Coordinate(newRow, newCol);
        }

        return new TetrisShape(newShape, shapeColor);
    }

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved down 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    public TetrisShape down() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves down, so increase row by 1, col does not change
            newShape[i] = new Coordinate(myShape[i].getRow() + 1, myShape[i].getCol());
        }

        return new TetrisShape(newShape, shapeColor);
    }

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved left 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    public TetrisShape left() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves left, so decrease col by 1, row does not change
            newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol() - 1);
        }

        return new TetrisShape(newShape, shapeColor);
    }

    /**--------------------------------------------------------------------------
     * returns a TetrisShape moved right 1 block from the current position
     * 
     * @return updated coordinates of the shifted shape
     *-------------------------------------------------------------------------*/
    public TetrisShape right() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves right, so increase col by 1, row does not change
            newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol() + 1);
        }

        return new TetrisShape(newShape, shapeColor);
    }

    /**--------------------------------------------------------------------------
     * puts the generated shape at the starting point of the grid
     * 
     * @param width of the grid to center the piece
     *-------------------------------------------------------------------------*/
    public void starting(int width) {
        //puts the shape to starting point first
        toOrigin();
        
        // offsets based on dims of the board, should end up top/center
        int cOffset = width / 2;
        int rOffset = getHeightOffset() + PRINT_OFFSET;

        for (int i = 0; i < myShape.length; ++i) {
            myShape[i] = new Coordinate(myShape[i].getRow() + rOffset, 
                    myShape[i].getCol() + cOffset);
        }
        //printCoords();
    }

    /**--------------------------------------------------------------------------
     * get the coordinates of the shape
     * 
     * @return coordinate array of this shape
     *-------------------------------------------------------------------------*/
    public Coordinate[] getCoordinates() {
        return myShape;
    }

    /**--------------------------------------------------------------------------
     * figures out the bounds of the shape (used for clearing lines)
     * 
     * @return an array of size 2, with format [top, bot] 
     *-------------------------------------------------------------------------*/
    public int[] shapeBounds() {
        int[] bounds = new int[BOUNDS]; // initialized to 0

        for (int i = 0; i < myShape.length; ++i) {
            if (myShape[i].getRow() > bounds[BOT]) {
                bounds[BOT] = myShape[i].getRow();
            }
            if (myShape[i].getRow() < bounds[TOP]) {
                bounds[TOP] = myShape[i].getRow();
            }
        }
        return bounds;
    }

    /**--------------------------------------------------------------------------
     * prints the shape into a 5x5 grid for testing purposes
     *-------------------------------------------------------------------------*/
    public void printShape() {
        int rOffset = myShape[0].getRow();
        int cOffset = myShape[0].getCol();

        char[][] smallGrid = new char[SHAPE_SIZE + 1][SHAPE_SIZE + 1];
        for (int row = 0; row <= SHAPE_SIZE; ++row) {
            for (int col = 0; col <= SHAPE_SIZE; ++col) {
                smallGrid[row][col] = ' ';
            }
        }
        for (int i = 0; i < myShape.length; ++i) {
            int row = myShape[i].getRow() - rOffset + PRINT_OFFSET;
            int col = myShape[i].getCol() - cOffset + PRINT_OFFSET;
            smallGrid[row][col] = 'X';
        }

        for (int row = 0; row <= SHAPE_SIZE; ++row) {
            for (int col = 0; col <= SHAPE_SIZE; ++col) {
                System.out.print(smallGrid[row][col]);
            }
            System.out.println();
        }
    }

    /**--------------------------------------------------------------------------
     * helper method: gets the highest point of the shape
     * note:    only used by starting(width), so initial highest point is negative
     * 
     * @return the offset (absolute value of the highest point above 0)
     *-------------------------------------------------------------------------*/
    private int getHeightOffset() {
        int holder = 0;
        //standard loop for getting max value
        for (int i = 0; i < myShape.length; ++i) {
            int row = myShape[i].getRow();

            if (row < holder) {
                holder = row;
            }
        }
        return -holder;
    }

    /**--------------------------------------------------------------------------
     * Prints the coordinates of this shape (testing purposes)
     *-------------------------------------------------------------------------*/
    public void printCoords() {
        for (int i = 0; i < myShape.length; ++i) {
            System.out.println(myShape[i].getRow() + ", " + myShape[i].getCol());
        }
    }
    
    //sets the color
    public void setColor(Paint color) {
        shapeColor = color;
        
        //error check
        if(color == null) {
            System.out.println("some failure");
        }
    }
    
    /**--------------------------------------------------------------------------
     * makes a deep copy of the given coordinates into myShape
     *-------------------------------------------------------------------------*/
    private void deepCopy(Coordinate[] x) {
        for (int i = 0; i < x.length; ++i) {
            myShape[i] = new Coordinate(x[i].getRow(), x[i].getCol());
        }
    }
    
    /**--------------------------------------------------------------------------
     * moves the shape back to origin (used for swaps)
     *-------------------------------------------------------------------------*/
    private void toOrigin() {
        int rOffset = myShape[0].getRow();
        int cOffset = myShape[0].getCol();
        
        for(int i = 0; i < myShape.length; ++i) {
            myShape[i].setCol(myShape[i].getCol() - cOffset);
            myShape[i].setRow(myShape[i].getRow() - rOffset);
        }
    }
    
    
}
