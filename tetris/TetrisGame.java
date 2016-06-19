package tetris;
import java.util.Random;


public class TetrisGame {
	final int NUMSHAPES = 7;
	
	private int width;
	private int height;
	private Random rng;
	
	//default of 10x20
	public TetrisGame() {
		width = Tetris.GRID_WIDTH;
		height = Tetris.GRID_WIDTH;
		//setup();
	}
	
	public TetrisGame(Coordinate dims) {
		width = dims.getRow();
		height = dims.getCol();
		//setup();
	}
	
	//generates a new random number to make a new shape
	private TetrisShape randomShapeGen() {
		rng = new Random(System.nanoTime());
		int shapeNum = rng.nextInt() % NUMSHAPES;
		
		TetrisShape thisShape = new TetrisShape(shapeNum);
		
		return thisShape;
	}
	
	
}
