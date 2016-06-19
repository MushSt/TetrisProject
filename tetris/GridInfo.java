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
		height = Tetris.GRID_HEIGHT + TOPBITS;
		width = Tetris.GRID_WIDTH;
		grid = new char[width][height];
		resetGrid();
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
		
		
		return false;
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
	public void printGrid() {
		for(int row = TOPBITS; row < height; ++row) {
			for(int col = 0; col < width; ++col) {
				System.out.print(grid[row][col]);
			}
			//newline at end of the row
			System.out.println();
		}
	}
}
