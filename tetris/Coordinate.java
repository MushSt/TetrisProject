package tetris;
/*------------------------------------------------------
 * 	Coordinates object, (row,col) 
 * row, col because dealing with a matrix, easier to 
 * work with 
 *-----------------------------------------------------*/

public class Coordinate {

  private int row;
  private int col;

  // no argument constructor sets a coord of 0,0
  public Coordinate() {
    this(0, 0);
  }

  public Coordinate(int x, int y) {
    row = x;
    col = y;
  }

  // public mutators
  public void setRow(int x) {
    row = x;
  }

  public void setCol(int y) {
    col = y;
  }

  // public accessors
  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public boolean equals(Coordinate x) {
    int xrow = x.getRow();
    int xcol = x.getCol();

    if (xrow == row && xcol == col) {
      return true;
    } else {
      return false;
    }
  }

}
