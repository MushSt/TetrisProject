package tetris;
/*
 * Coordinates object, (row,col) 
 * row, col because dealing with a matrix, easier to 
 * work with 
 */

public class Coordinate implements CoordinateInterface {

    private int row;
    private int col;
    
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public void setCol(int col) {
        this.col = col;
    }

   
   public int getRow() {
       return row;
   }
   
   public int getCol() {
       return col;
   }
   
   public boolean equals(Coordinate other) {
       int otherRow = other.getRow();
       int otherCol = other.getCol();
       
       if(row != otherRow || col != otherCol) {
           return false;
       }
       return true;
   }

}
