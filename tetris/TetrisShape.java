package tetris;

public class TetrisShape {
	//index 0 of each shape is (0,0), which we will use as the center of the shapes
	final private Coordinate[] shapeI = {new Coordinate(0,0), new Coordinate(1,0), 
											new Coordinate(-1,0), new Coordinate(-2,0)};
	final private Coordinate[] shapeL = {new Coordinate(0,0), new Coordinate(0,1),
											new Coordinate(-1,0), new Coordinate(-2,0)};
	final private Coordinate[] shapeJ = {new Coordinate(0,0), new Coordinate(0,-1),
											new Coordinate(-1,0), new Coordinate(-2,0)};
	final private Coordinate[] shapeT = {new Coordinate(0,0), new Coordinate(0,1),
											new Coordinate(0,-1), new Coordinate(-1,0)};
	final private Coordinate[] shapeO = {new Coordinate(0,0), new Coordinate(-1,0),
											new Coordinate(0,-1), new Coordinate(-1,-1)};
	final private Coordinate[] shapeS = {new Coordinate(0,0), new Coordinate(-1,0),
											new Coordinate(0,-1), new Coordinate(-1,1)};
	final private Coordinate[] shapeZ = {new Coordinate(0,0), new Coordinate(-1,-1),
											new Coordinate(-1,0), new Coordinate(0,1)};
	final public int SHAPE_SIZE = 4;
	
	
	private Coordinate[] myShape;
	
	//null argument constructor does nothing
	public TetrisShape() {
		
	}
	
	//constructor to make a new shape object based on previous one
	public TetrisShape(Coordinate[] shape) {
		deepCopy(shape);
	}
	
	//constructor for making a random shape
	public TetrisShape(int random) {
		switch(random) {
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
				break;
		}
	}
	
	/*
	 * rotates this shape clockwise around 0,0
	 * algorithm for this is row = col, col = -row
	 */
	public Coordinate[] rotateClock() {
		Coordinate[] newShape = new Coordinate[SHAPE_SIZE];
		
		for(int i = 0; i < myShape.length; ++i) {
			//moves down, so increase row by 1, col does not change
			newShape[i] = new Coordinate(myShape[i].getCol(), -myShape[i].getRow());
		}
		
		return newShape;
	}
	
	/*
	 * rotates this shape counterclockwise
	 * algorithm is reverse of clockwise, so 
	 * row = -col, col = row
	 */
	public Coordinate[] rotateCounter() {
		//algorithm only works for when the origin is centered, so we calculate and save it
		int rOffset = myShape[0].getRow();
		int cOffset = myShape[0].getCol();
		
		Coordinate[] newShape = new Coordinate[SHAPE_SIZE];
		
		for(int i = 0; i < myShape.length; ++i) {
			//do rotation at origin and then change it back (-offset, +offset)
			int newRow = -(myShape[i].getCol() - cOffset) + rOffset;
			int newCol = myShape[i].getRow() - rOffset + cOffset;
			newShape[i] = new Coordinate(newRow, newCol);
		}
		
		return newShape;
	}
	
	/*
	 * returns TetrisShape of this shape moved down 1 level
	 */
	public Coordinate[] down() {
		Coordinate[] newShape = new Coordinate[SHAPE_SIZE];
		
		for(int i = 0; i < myShape.length; ++i) {
			//moves down, so increase row by 1, col does not change
			newShape[i] = new Coordinate(myShape[i].getRow()+1, myShape[i].getCol());
		}
		
		return newShape;
	}
	
	/*
	 * returns TetrisShape of this shape moved left 1 level
	 */
	public Coordinate[] left() {
		Coordinate[] newShape = new Coordinate[SHAPE_SIZE];
		
		for(int i = 0; i < myShape.length; ++i) {
			//moves left, so decrease col by 1, row does not change
			newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol()-1);
		}
		
		return newShape;
	}
	
	/*
	 * returns TetrisShape of this shape moved right 1 level
	 */
	public Coordinate[] right() {
		Coordinate[] newShape = new Coordinate[SHAPE_SIZE];
		
		for(int i = 0; i < myShape.length; ++i) {
			//moves right, so increase col by 1, row does not change
			newShape[i] = new Coordinate(myShape[i].getRow(), myShape[i].getCol()+1);
		}
		
		return newShape;
	}
	
	/*
	 * gets the array of coordinates that defines this shape
	 */
	public Coordinate[] getCoordinates() {
		return myShape;
	}
	
	/*
	 * makes a deep copy of the shape in question into myShape
	 */
	private void deepCopy(Coordinate[] x) {
		for(int i = 0; i < x.length; ++i) {
			myShape[i] = x[i];
		}
	}
	
	/*
	 * Prints Shape in a 4x4 box (testing purposes)
	 */
	public void printShape() {
		int rOffset = myShape[0].getRow();
		int cOffset = myShape[0].getCol();
		
		char[][] smallGrid = new char[SHAPE_SIZE][SHAPE_SIZE];
		for(int row = 0; row < SHAPE_SIZE; ++row) {
			for(int col = 0; col < SHAPE_SIZE; ++col) {
				
			}
		}
		for(int i = 0; i < myShape.length; ++i) {
			int row = myShape[i].getRow() - rOffset;
			int col = myShape[i].getCol() - cOffset;
			smallGrid[row][col] = 'X';
		}
	}
}
