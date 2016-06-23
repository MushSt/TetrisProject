package tetris;

import java.util.Random;

public class TetrisShape {
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

    private Coordinate[] myShape;

    // null argument constructor does nothing
    public TetrisShape() {
        myShape = new Coordinate[SHAPE_SIZE];
    }

    // constructor to make a new shape object based on previous one
    public TetrisShape(Coordinate[] shape) {
        myShape = new Coordinate[SHAPE_SIZE];
        deepCopy(shape);
    }

    /*
     * method to turn TetrisShape into a random shape
     */
    public void shapeGen() {
        Random rng = new Random(System.nanoTime());
        int shapeNum = rng.nextInt(NUMSHAPES);

        switch (shapeNum) {
            case 0:
                deepCopy(shapeI);
                break;
            case 1:
                deepCopy(shapeL);
                break;
            case 2:
                deepCopy(shapeJ);
                break;
            case 3:
                deepCopy(shapeT);
                break;
            case 4:
                deepCopy(shapeO);
                break;
            case 5:
                deepCopy(shapeS);
                break;
            case 6:
                deepCopy(shapeZ);
                break;
            default:
                System.out.println("bad shape error: " + shapeNum);
                break;
        }
    }

    /*
     * rotates this shape clockwise around 0,0 algorithm for this is row = col,
     * col = -row
     */
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

        return new TetrisShape(newShape);
    }

    /*
     * rotates this shape counterclockwise algorithm is reverse of clockwise, so
     * row = -col, col = row
     */
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

        return new TetrisShape(newShape);
    }

    /*
     * returns TetrisShape of this shape moved down 1 level
     */
    public TetrisShape down() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves down, so increase row by 1, col does not change
            newShape[i] = new Coordinate(myShape[i].getRow() + 1, myShape[i].getCol());
        }

        return new TetrisShape(newShape);
    }

    /*
     * returns TetrisShape of this shape moved left 1 level
     */
    public TetrisShape left() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves left, so decrease col by 1, row does not change
            newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol() - 1);
        }

        return new TetrisShape(newShape);
    }

    /*
     * returns TetrisShape of this shape moved right 1 level
     */
    public TetrisShape right() {
        Coordinate[] newShape = new Coordinate[SHAPE_SIZE];

        for (int i = 0; i < myShape.length; ++i) {
            // moves right, so increase col by 1, row does not change
            newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol() + 1);
        }

        return new TetrisShape(newShape);
    }

    /*
     * changes coordinates of the shape to the starting position takes as input
     * the dimensions of the board
     */
    public void starting(int width) {
        // offsets based on dims of the board, should end up top/center
        int cOffset = width / 2;
        int rOffset = getHeightOffset() + PRINT_OFFSET;

        for (int i = 0; i < myShape.length; ++i) {
            myShape[i] = new Coordinate(myShape[i].getRow() + rOffset, myShape[i].getCol() + cOffset);
        }
        printCoords();
    }

    /*
     * gets the array of coordinates that defines this shape
     */
    public Coordinate[] getCoordinates() {
        return myShape;
    }

    /*
     * method to return the max and min heights returns an array of size 2,
     * [top, bot]
     */
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

    /*
     * makes a deep copy of the shape in question into myShape
     */
    private void deepCopy(Coordinate[] x) {
        for (int i = 0; i < x.length; ++i) {
            myShape[i] = new Coordinate(x[i].getRow(), x[i].getCol());
        }
    }

    /*
     * Prints Shape in a 5x5 box (testing purposes)
     */
    public void printShape() {
        int rOffset = myShape[0].getRow();
        int cOffset = myShape[0].getCol();

        char[][] smallGrid = new char[SHAPE_SIZE + 1][SHAPE_SIZE + 1];
        for (int row = 0; row <= SHAPE_SIZE; ++row) {
            for (int col = 0; col <= SHAPE_SIZE; ++col) {
                smallGrid[row][col] = '.';
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

    /*
     * helper method for starting point, finds the highest point so we can
     * figure out where to put it returns the height offset (positive)
     */
    private int getHeightOffset() {
        int holder = 0;
        for (int i = 0; i < myShape.length; ++i) {
            int row = myShape[i].getRow();

            if (row < holder) {
                holder = row;
            }
        }
        return -holder;
    }

    /*
     * helper testing method
     */
    private void printCoords() {
        for (int i = 0; i < myShape.length; ++i) {
            System.out.println(myShape[i].getRow() + ", " + myShape[i].getCol());
        }
    }
}
