package tetrisGUI;
/*
 * This class will make the main window, organizing it accordingly 
 * it will call the StartGame class and GridGraphics
 */

//IMPORTS
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import tetris.TetrisShape;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends Application {
    
    //Public Constants
    
    public static final int DEFAULT_GRIDWIDTH = 10;
    public static final int DEFAULT_GRIDHEIGHT = 20;
    public static final int OFFSET_SIZE = 4;
    public static final int DISPLAY_HEIGHT = 12;
        
    //Private Constants
    private static final int VBOX_SPACE = 25;
    private static final int HORIZ_BORDER = 30;

    
        //game stats constants
    final private static int DROP_SCORE = 10;
    final private static int ONE_LINE = 40;
    final private static int TWO_LINES = 100;
    final private static int THREE_LINES = 300;
    final private static int FOUR_LINES = 1200;
    final private static int ONE = 1;
    final private static int TWO = 2;
    final private static int THREE = 3;
    final private static int FOUR = 4;
    final private static int LEVELUP = 5;
    final private static String ZERO = "0";
    //game stat variables
    private static int gameLevel = 0;
    private static int linesCleared = 0;
    private static int currentScore = 0;
    private static int highScore = 0;
    
    //holder variables
    private static ShapeDraw holdBox;
    private static ShapeDraw nextBox;
    private static TextDraw gameLevelText;
    private static TextDraw currScoreText;
    private static TextDraw clearedLinesText;
    private static TextDraw highScoreText;
    private static DropTimer timer;
    private static StartGame game;
    
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
        double sideWidth = ShapeDraw.WIDTH_DIM * ShapeDraw.SHAPE_UNIT + 2*ShapeDraw.SHAPE_BORDER + ShapeDraw.SHAPE_GRIDLINE;
        double sideHeight = ShapeDraw.HEIGHT_DIM * ShapeDraw.SHAPE_UNIT + 2*ShapeDraw.SHAPE_BORDER + ShapeDraw.SHAPE_GRIDLINE;
        
        
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
        
        //create the left canvas and put it into VBox
        Canvas hold = new Canvas(sideWidth, sideHeight);
        leftHold.setAlignment(Pos.TOP_CENTER);
        
        //create the right canvas and put it into VBox
        Canvas next = new Canvas(sideWidth, sideHeight);
        rightStats.setAlignment(Pos.TOP_CENTER);
        
        //VBOX for canvas
        VBox leftCanvasBox = new VBox();
        leftCanvasBox.getChildren().add(new Label("STORAGE"));
        leftCanvasBox.getChildren().add(hold);
        leftCanvasBox.setAlignment(Pos.CENTER);
        leftHold.getChildren().add(leftCanvasBox);
        VBox rightCanvasBox = new VBox();
        rightCanvasBox.getChildren().add(new Label("NEXT PIECE"));
        rightCanvasBox.getChildren().add(next);
        rightCanvasBox.setAlignment(Pos.CENTER);
        rightStats.getChildren().add(rightCanvasBox);
        
        //VBoxes for Scoring on the right
        VBox gameLevelBox = new VBox();
        gameLevelBox.setAlignment(Pos.CENTER);
        VBox linesClearedBox = new VBox();
        linesClearedBox.setAlignment(Pos.CENTER);
        VBox currentScoreBox = new VBox();
        currentScoreBox.setAlignment(Pos.CENTER);
        VBox highScoreBox = new VBox();
        highScoreBox.setAlignment(Pos.CENTER);
        
        //setup for scoring 
        Label gameLevelLabel = new Label("Game Level");
        Label linesClearedLabel = new Label("Lines Cleared");
        Label currentScoreLabel = new Label("Current Score");
        Label highScoreLabel = new Label("High Score");

        //canvas for updating the scores of each stat
        Canvas levelDisplay = new Canvas(sideWidth, DISPLAY_HEIGHT);
        gameLevelText = new TextDraw(levelDisplay);
        gameLevelText.updateText(ZERO);
        
        Canvas currentScoreDisplay = new Canvas(sideWidth, DISPLAY_HEIGHT);
        currScoreText = new TextDraw(currentScoreDisplay);
        currScoreText.updateText(ZERO);
        
        Canvas highScoreDisplay = new Canvas(sideWidth, DISPLAY_HEIGHT);
        highScoreText = new TextDraw(highScoreDisplay);
        highScoreText.updateText(ZERO);
        
        Canvas clearedLinesDisplay = new Canvas(sideWidth, DISPLAY_HEIGHT);
        clearedLinesText = new TextDraw(clearedLinesDisplay);
        clearedLinesText.updateText(ZERO);
        
        //add everything together
        gameLevelBox.getChildren().add(gameLevelLabel);
        gameLevelBox.getChildren().add(levelDisplay);
        linesClearedBox.getChildren().add(linesClearedLabel);
        linesClearedBox.getChildren().add(clearedLinesDisplay);
        currentScoreBox.getChildren().add(currentScoreLabel);
        currentScoreBox.getChildren().add(currentScoreDisplay);
        highScoreBox.getChildren().add(highScoreLabel);
        highScoreBox.getChildren().add(highScoreDisplay);
        
        rightStats.getChildren().add(gameLevelBox); 
        rightStats.getChildren().add(linesClearedBox);
        rightStats.getChildren().add(currentScoreBox);
        rightStats.getChildren().add(highScoreBox);
        
        //ShapeDraw for hold and next
        holdBox = new ShapeDraw(hold);
        nextBox = new ShapeDraw(next);
        
        //draw the initial states
        holdBox.draw();
        nextBox.draw();
        
        //create the game canvas & put it into GridGraphics with threading
        Canvas gameGrid = new Canvas(canvasWidth, canvasHeight);
        //set it to get focus
        gameGrid.setFocusTraversable(true);
        gameGrid.addEventFilter(MouseEvent.ANY, (e) -> gameGrid.requestFocus());
        windowLayout.setCenter(gameGrid);
        
        BorderPane.setAlignment(gameGrid, Pos.CENTER);
        BorderPane.setAlignment(leftHold, Pos.CENTER);
        BorderPane.setAlignment(rightStats, Pos.CENTER);
        BorderPane.setAlignment(topBorder, Pos.CENTER);
        
        
        //buttons:
        Button startButton = new Button("Start Game");
        Button pauseButton = new Button("Pause Game");
        Button quitGame = new Button("Quit");
        startButton.setAlignment(Pos.CENTER);
        pauseButton.setAlignment(Pos.CENTER);
        quitGame.setAlignment(Pos.CENTER);
        
        leftHold.getChildren().add(startButton);
        leftHold.getChildren().add(pauseButton);
        leftHold.getChildren().add(quitGame);
        
        //setup game
        game = new StartGame(gameGrid);
        timer = new DropTimer(gameLevel, game);
        timer.pause();
        Thread timeThread = new Thread(timer);
        timeThread.start();
        Thread gameThread = new Thread(game);
        
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            //start game button pressed
            @Override
            public void handle(ActionEvent event) {
                //kill the previous thread
                handleGameOver(game, timer);

                game.newGame();
                timer.unpause();
                
                try {
                    //restart the thread
                    gameThread.start();
                }
                catch(Exception e) {}
            }
        });
        
        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!game.isInPlay()) {
                    return;
                }
                
                if(timer.isPaused()) {
                    timer.unpause();
                    pauseButton.setText("Pause Game");
                }
                else {
                    timer.pause();
                    pauseButton.setText("Unpause Game");
                }
            }
        });
        
        quitGame.setOnAction(new EventHandler<ActionEvent>() {
            //start game button pressed
            @Override
            public void handle(ActionEvent event) {
                game.setGameOver();
                timer.stop();
                Platform.exit();
            }
        });
        
        //canvas keylistener
        gameGrid.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                // TODO Auto-generated method stub
                KeyCode key = event.getCode();
                game.newCommand(key);
            }
            
        });
        
        
        //execute
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private synchronized static void handleGameOver(StartGame game, DropTimer timer) {
        game.setGameOver();
        timer.pause();
        gameOver();
    }
    
    public synchronized static void updateNext(TetrisShape next) {
        nextBox.draw(next);
    }
    
    public synchronized static void updateSwap(TetrisShape shape) {
        holdBox.draw(shape);
    }
    
    private synchronized static void setGameLevel() {
        gameLevel = linesCleared / LEVELUP;
        gameLevelText.updateText(""+gameLevel);
    }
    
    public synchronized static void updateLinesCleared(int lines) {
        linesCleared += lines;
        clearedLinesText.updateText("" + linesCleared);
        setGameLevel(); //update level based on new cleared lines
        calculateLineScores(lines);
    }
    
    public synchronized static void gameOver() {
        //update bestScore
        if(currentScore > highScore) {
            highScore = currentScore;
            
            //display some message

            highScoreText.updateText("" +highScore);
        }
        System.out.println("Game Over, you reached level " +gameLevel);
        System.out.println("Score: " +currentScore);
        System.out.println("High Score: " +highScore);
        System.out.println("Lines Cleared: " +linesCleared);
        
        //reset stats
        currentScore = 0;
        linesCleared = 0;
        gameLevel = 0;
        setGameLevel(); //return to level 0
    }
    
    /**------------------------------------------------------------------------
     * helper method for updating and calculating the scores based on
     * line clears
     * 
     * @param numLines
     *            number of lines cleared by the block
     *-----------------------------------------------------------------------*/
    private static void calculateLineScores(int numLines) {
        int lineScore = 0;
        switch(numLines) {
            case ONE:
                lineScore = ONE_LINE;
                break;
            case TWO:
                lineScore = TWO_LINES;
                break;
            case THREE:
                lineScore = THREE_LINES;
                break;
            case FOUR:
                lineScore = FOUR_LINES;
                break;
            default:
                break;
        }
        
        currentScore += (lineScore * (gameLevel + 1));
        currScoreText.updateText(""+currentScore);
    }
    
    /**------------------------------------------------------------------------
     * helper method for updating the score after successfully setting a shape
     *-----------------------------------------------------------------------*/
    public static void updateScoreDrop() {
        currentScore += DROP_SCORE;
        currScoreText.updateText(""+currentScore);
    }
    
    public static void pauseTimer() {
        timer.pause();
    }
}
