package tetrisGUI;
/*
 * This class will make the main window, organizing it accordingly 
 * it will call the StartGame class and GridGraphics
 */

//IMPORTS
import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;

public class MainMenu extends Application {
    
    //Constants
    public static final int CANVAS_WIDTH = 200;
    public static final int CANVAS_HEIGHT = 400;
    
    public static final int DEFAULT_GRIDWIDTH = 10;
    public static final int DEFAULT_GRIDHEIGHT = 20;
    
    
    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Group root = new Group();
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.rgb(0, 0, 0) );
        
        //create the canvas & put it into GridGraphics with threading
        Canvas gameGrid = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        Thread gameThread = new Thread(new StartGame(gameGrid));
        gameThread.start();
    }

}
