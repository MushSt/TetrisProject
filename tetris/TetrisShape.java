package tetris;

public class TetrisShape {
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
	
	
	private Coordinate[] myShape;
	
	
	//default constructor
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
	 * rotates this shape clockwise
	 */
	public TetrisShape rotateClock() {
		
		
		return null;
	}
	
	/*
	 * rotates this shape counterclockwise
	 */
	public TetrisShape rotateCounter() {
		
		return null;
	}
	
	/*
	 * returns TetrisShape of this shape moved down 1 level
	 */
	public TetrisShape down() {
		
	}
	
	/*
	 * returns TetrisShape of this shape moved left 1 level
	 */
	public TetrisShape left() {
		
	}
	
	/*
	 * returns TetrisShape of this shape moved right 1 level
	 */
	public TetrisShape right() {
		
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
}
