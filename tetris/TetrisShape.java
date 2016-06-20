package tetris;

public class TetrisShape {
	final Coordinate[] shapeI = {new Coordinate(0,0), new Coordinate(1,0), new Coordinate(-1,0), new Coordinate(-2,0)};
	final Coordinate[] shapeL = {new Coordinate(0,0), new Coordinate(0,1), new Coordinate(-1,0), new Coordinate(-2,0)};
	final Coordinate[] shapeJ = {new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(-1,0), new Coordinate(-2,0)};
	final Coordinate[] shapeT = {new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,-1), new Coordinate(-1,0)};
	final Coordinate[] shapeO = {new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(0,-1), new Coordinate(-1,-1)};
	final Coordinate[] shapeS = {new Coordinate(0,0), new Coordinate(-1,0), new Coordinate(0,-1), new Coordinate(-1,1)};
	final Coordinate[] shapeZ = {new Coordinate(0,0), new Coordinate(-1,-1), new Coordinate(-1,0), new Coordinate(0,1)};
	
	
	Coordinate[] myShape;
	
	
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
