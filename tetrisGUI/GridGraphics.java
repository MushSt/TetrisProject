package tetrisGUI;

/*
 * This class handles drawing the canvas and updating the shapes
 */

//IMPORTS
import javafx.concurrent.*;
import javafx.scene.canvas.*;
import javafx.scene.*;

public class GridGraphics implements GridGraphicsInterface {
    //Constants:
    public static final int BLOCK_PIXELS = 20; //pixels in a block
    public static final int BORDER_PIXELS = 3; //pixels for grid lines
    

    //Member Variables:
    private int width;
    private int height;
    
    private Canvas gameGrid;
    
    
    public GridGraphics(Canvas gameGrid) {
        //uses default width/height params
        this(gameGrid, MainMenu.DEFAULT_GRIDWIDTH, MainMenu.DEFAULT_GRIDHEIGHT);
    }
    
    public GridGraphics(Canvas gameGrid, int userWidth, int userHeight) {
        width = userWidth;
        height = userHeight;
        
        this.gameGrid = gameGrid;
    }
    
    
    
}
