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
				myShape = deepCopy(shapeI);
				break;
			case 1:
				myShape = deepCopy(shapeL);
				break;
			case 2:
				myShape = deepCopy(shapeJ);
				break;
			case 3:
				myShape = deepCopy(shapeT);
				break;
			case 4: 
				myShape = deepCopy(shapeO);
				break;
			case 5:
				myShape = deepCopy(shapeS);
				break;
			case 6: 
				myShape = deepCopy(shapeZ);
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
	
	public Coordinate[] getCoordinates() {
		
		return myShape;
	}
	
	private Coordinate[] deepCopy(Coordinate[] x) {
		
		return null;
	}
}
