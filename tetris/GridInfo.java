package tetris;
/*
 * This class is a grid object, which keeps track of the state of the grid
 * also updates the grid as things happen
 */

public class GridInfo {
	final private int TOPBITS = 4;
	
	//default, makes default sized grid
	private int width;
	private int height;
	private char[][] grid;
	
	//constructors
	public GridInfo() {
		this(Tetris.GRID_WIDTH, Tetris.GRID_HEIGHT);
	}
	
	public GridInfo(int w, int h) {
		height = h + TOPBITS;
		width = w;
		grid = new char[width][height];
		resetGrid();
	}
	
	/* sets the grid up to start as empty spaces
	 * empty spaces are '.'
	 */
	private void resetGrid() {
		for(int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				grid[row][col] = '.';
			}
		}
	}
	
	/* checks if the shape can be placed 
	 * shape is 4 coordinates, so checks if these coordinates are taken
	 */
	public boolean checkShape(TetrisShape shape) {
		Coordinate[] shapeCords = shape.getCoordinates();
		//loop through the coordinates in shape to check validity
		for(int i = 0; i < shapeCords.length; ++i) {
			int row = shapeCords[i].getRow();
			int col = shapeCords[i].getCol();
			
			//if it not valid, we can return false 
			//first check if its in bounds
			if(row < 0 || row >= height) {
				return false;
			}
			if(col < 0 || col >= width) {
				return false;
			}
			
			//will use 'X' as taken blocks
			if(grid[row][col] == 'X') {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * method for placing the shape (falling shape vs set shape)
	 */
	public boolean placeFallingShape(Coordinate[] shape) {
		
		return false;
	}
	
	/*
	 * checks if we can set the shape onto the grid
	 */
	public boolean canSetShape(TetrisShape shape) {
		//base case
		if(!checkShape(shape)) {
			return false;
		}
		Coordinate[] s = shape.getCoordinates();
		
		//conditions for successful set shape:
			//1. there are blocks directly under (can't place shape.down)
		Coordinate[] check = shape.down();
		
		
		return false;
	}
	
	/*
	 * set the shape onto the grid
	 */
	public void setShape(TetrisShape shape) {
		Coordinate[] s = shape.getCoordinates();
		
		for(int i = 0; i < s.length; ++i) {
			int row = s[i].getRow();
			int col = s[i].getCol();
			
			grid[row][col] = 'X';
		}
	}
 	
	//resets the grid for game over
	public void gameOver() {
		resetGrid();
	}
	
	//public getter method
	public char[][] getGrid() {
		return grid;
	}
	
	//prints the grid, but not the top 4 rows
	//prints out the falling block as '*' marks
	public void printGrid(TetrisShape x) {
		Coordinate[] shape = x.getCoordinates();
		boolean falling = false;
		
		for(int row = TOPBITS; row < height; ++row) {
			for(int col = 0; col < width; ++col) {
				Coordinate checker = new Coordinate(row, col);
				
				//if falling block piece here, print * instead
				for(int i = 0; i < shape.length; ++i) {
					if(checker.equals(shape[i])) {
						System.out.print('*');
						falling = true;
					}
				}
				if(falling) {
					System.out.print(grid[row][col]);
				}
				falling = false;
			}
			//newline at end of the row
			System.out.println();
		}
	}
}
