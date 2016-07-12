package tetrisGUI;
/*
 * This class will make the main window, organizing it accordingly 
 * it will call the StartGame class and GridGraphics
 */

//IMPORTS
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends Application {
    
    //Public Constants
    public static final int CANVAS_WIDTH = 300;
    public static final int CANVAS_HEIGHT = 600;
    public static final int CANVAS_OFFSET = 120;
    
    public static final int DEFAULT_GRIDWIDTH = 10;
    public static final int DEFAULT_GRIDHEIGHT = 20;
    
    //Private Constants
    private static final int VBOX_SPACE = 10;

    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //some constants calculation
        int gridUnit = (GridGraphics.BLOCK_PIXELS+GridGraphics.BORDER_PIXELS);
        int canvasWidth = DEFAULT_GRIDWIDTH * gridUnit +GridGraphics.BORDER_PIXELS;
        int canvasHeight = DEFAULT_GRIDHEIGHT * gridUnit + GridGraphics.BORDER_PIXELS;
        
        //borderpane for the structure of the game window
        BorderPane windowLayout = new BorderPane();
        windowLayout.setPrefSize(canvasWidth + CANVAS_OFFSET, canvasHeight);

        Scene scene = new Scene(windowLayout);
        
        //HBox for top border
        HBox topBorder = new HBox();
        topBorder.setMinHeight(30);
        windowLayout.setTop(topBorder);
        
        
        //Vbox for left and right parts
        VBox leftHold = new VBox(VBOX_SPACE);
        VBox rightStats = new VBox(VBOX_SPACE);
            //set the parts into windowLayout
        windowLayout.setLeft(leftHold);
        windowLayout.setRight(rightStats);
        
        //VBox dims
        leftHold.setPrefWidth(CANVAS_OFFSET);
        leftHold.setStyle("-fx-border-color: black;");
        rightStats.setPrefWidth(CANVAS_OFFSET);
        rightStats.setStyle("-fx-border-color: black;");
        
        //create the canvas & put it into GridGraphics with threading
        Canvas gameGrid = new Canvas(canvasWidth, canvasHeight);
        windowLayout.setCenter(gameGrid);
        
        BorderPane.setAlignment(gameGrid, Pos.CENTER);
        BorderPane.setAlignment(leftHold, Pos.CENTER);
        BorderPane.setAlignment(rightStats, Pos.CENTER);
        BorderPane.setAlignment(topBorder, Pos.CENTER);
        
        Thread gameThread = new Thread(new StartGame(gameGrid));
            //Starts the game
        gameThread.start();
        
        
        
        //execute
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
