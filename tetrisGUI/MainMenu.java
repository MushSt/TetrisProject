package tetrisGUI;
/*
 * This class will make the main window, organizing it accordingly 
 * it will call the StartGame class and GridGraphics
 */

//IMPORTS
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Window;
import tetris.TetrisShape;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends Application {
    
    //Public Constants
    
    public static final int DEFAULT_GRIDWIDTH = 10;
    public static final int DEFAULT_GRIDHEIGHT = 20;
    public static final int OFFSET_SIZE = 4;
    
    //Private Constants
    private static final int VBOX_SPACE = 10;
    private static final int HORIZ_BORDER = 30;
    
        //game stats constants
    private static int gameLevel = 0;
    private static int linesCleared = 0;
    private static int currentScore = 0;
    private static int highScore = 0;
    private static boolean gameOver = false;
    
        //game constants
    private static TetrisShape swapShape;
    private static TetrisShape nextShape;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        
        //some constants calculation
        double canvasWidth = DEFAULT_GRIDWIDTH * GridGraphics.UNIT_PIXELS + GridGraphics.BORDER_PIXELS;
        double canvasHeight = DEFAULT_GRIDHEIGHT * GridGraphics.UNIT_PIXELS + GridGraphics.BORDER_PIXELS;
        double canvasOffset = OFFSET_SIZE * GridGraphics.UNIT_PIXELS + GridGraphics.BORDER_PIXELS;
        double windowWidth = canvasWidth + 2*canvasOffset;
        double windowHeight = canvasHeight + 2*HORIZ_BORDER;
        
        
        //fix window minimum size
        primaryStage.setMinWidth(windowWidth);
        primaryStage.setMinHeight(windowHeight);
        
        
        
        //borderpane for the structure of the game window
        BorderPane windowLayout = new BorderPane();
        
        windowLayout.setPrefSize(windowWidth, canvasHeight);

        Scene scene = new Scene(windowLayout);
        
        //HBox for top and bottom borders
        HBox topBorder = new HBox();
        HBox botBorder = new HBox();
        topBorder.setMinHeight(HORIZ_BORDER);
        botBorder.setMinHeight(HORIZ_BORDER);
        windowLayout.setTop(topBorder);
        windowLayout.setBottom(botBorder);
        
        //Vbox for left and right parts
        VBox leftHold = new VBox(VBOX_SPACE);
        VBox rightStats = new VBox(VBOX_SPACE);
            //set the parts into windowLayout
        windowLayout.setLeft(leftHold);
        windowLayout.setRight(rightStats);
        
        //VBox dims
        leftHold.setPrefWidth(canvasOffset);
        leftHold.setStyle("-fx-border-color: black;");
        rightStats.setPrefWidth(canvasOffset);
        rightStats.setStyle("-fx-border-color: black;");
        
        //create the canvas & put it into GridGraphics with threading
        Canvas gameGrid = new Canvas(canvasWidth, canvasHeight);
        windowLayout.setCenter(gameGrid);
        
        BorderPane.setAlignment(gameGrid, Pos.CENTER);
        BorderPane.setAlignment(leftHold, Pos.CENTER);
        BorderPane.setAlignment(rightStats, Pos.CENTER);
        BorderPane.setAlignment(topBorder, Pos.CENTER);
        
        StartGame game = new StartGame(gameGrid);
        DropTimer timer = new DropTimer(gameLevel, game);
        Thread gameThread = new Thread(game);
        Thread timeThread = new Thread(timer);
            //Starts the game
        gameThread.start();
        timeThread.start();
        
        
        //execute
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public synchronized static void updateNext(TetrisShape next) {
        nextShape = next;
    }
    
    public synchronized static void updateSwap(TetrisShape shape) {
        swapShape = shape;
    }
    
    public synchronized static void setGameLevel(int level) {
        gameLevel = level;
    }
    
    public synchronized static void updateLinesCleared(int lines) {
        linesCleared += lines;
    }
    
    public synchronized static void updateScore(int points) {
        currentScore += points;
    }
    
    public synchronized static void gameOver() {
        //update bestScore
        if(currentScore > highScore) {
            highScore = currentScore;
        }
        System.out.println("Game Over, you reached level " +gameLevel);
        System.out.println("Score: " +currentScore);
        System.out.println("High Score: " +highScore);
        System.out.println("Lines Cleared: " +linesCleared);
        
        //reset stats
        currentScore = 0;
        linesCleared = 0;
        gameLevel = 0;
        gameOver = true;
    }
}
