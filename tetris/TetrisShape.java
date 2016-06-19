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
				myShape = shapeI;
				break;
			case 1:
				myShape = shapeL;
				break;
			case 2:
				myShape = shapeJ;
				break;
		}
	}
	
	public TetrisShape rotateClock(TetrisShape x) {
		
		
		return null;
	}
	
	public TetrisShape rotateCounter(TetrisShape x) {
		
		return null;
	}
	
	public Coordinate[] getCoordinates() {
		
		return myShape;
	}
	
	private Coordinate[] deepCopy(Coordinate[] x) {
		
		return null;
	}
}
