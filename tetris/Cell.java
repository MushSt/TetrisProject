package tetris;

import javafx.scene.paint.Paint;

//this class holds a coordinate and a color
public class Cell {
    
    private int row;
    private int col;
    private Paint color;
    
    public Cell(int row, int col, Paint color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
    
    public void setColor(Paint color) {
        this.color = color;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
   
}
