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
import javafx.scene.text.Font;

public class MainMenu extends Application {
    
    //Public Constants
    
    public static final int DEFAULT_GRIDWIDTH = 10;
    public static final int DEFAULT_GRIDHEIGHT = 20;
    public static final int OFFSET_SIZE = 4;
    public static final int DISPLAY_HEIGHT = 12;
        
    //Private Constants
    private static final int VBOX_SPACE = 25;
    private static final int HORIZ_BORDER = 30;
    private static final int HBOX_SPACE = 100;

    
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
    final private static int LEVELUP = 3;
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

        Scene gameScene = new Scene(windowLayout);
        
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
        Button optionsButton = new Button("Help");
        Button pauseButton = new Button("Pause Game");
        Button quitGame = new Button("Quit");
        startButton.setAlignment(Pos.CENTER);
        optionsButton.setAlignment(Pos.CENTER);
        pauseButton.setAlignment(Pos.CENTER);
        quitGame.setAlignment(Pos.CENTER);
        
        leftHold.getChildren().add(startButton);
        leftHold.getChildren().add(optionsButton);
        leftHold.getChildren().add(pauseButton);
        leftHold.getChildren().add(quitGame);
        
        //Give gameGrid the focus immediately
        startButton.addEventFilter(ActionEvent.ANY, (e) -> gameGrid.requestFocus());
        optionsButton.addEventFilter(ActionEvent.ANY, (e) -> gameGrid.requestFocus());
        pauseButton.addEventFilter(ActionEvent.ANY, (e) -> gameGrid.requestFocus());
        quitGame.addEventFilter(ActionEvent.ANY, (e) -> gameGrid.requestFocus());
        startButton.setFocusTraversable(false);
        optionsButton.setFocusTraversable(false);
        pauseButton.setFocusTraversable(false);
        quitGame.setFocusTraversable(false);
        
        //create the option screen
        Scene options = handleOptionsScreen(primaryStage, gameScene);
        
        game = new StartGame(gameGrid);
        game.drawGrid();
        timer = new DropTimer(gameLevel, game);
        timer.pause();
        Thread timeThread = new Thread(timer);
        timeThread.setDaemon(true);
        
        //start timer
        timeThread.start();
        
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //start game button pressed
            @Override
            public void handle(MouseEvent event) {
                //save the stats and restart the game
                handleGameOver(game, timer);
                
                game.newGame();
                currScoreText.updateText("" +currentScore);
                pauseButton.setText("Pause Game");
                
                holdBox.draw();
                
                timer.unpause();
            }
       
        });
        
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
        
        optionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timer.pause();
                if(game.isInPlay()) {
                    pauseButton.setText("Unpause Game");
                }
                
                primaryStage.setScene(options);
            }
        });
        
        
        quitGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //start game button pressed
            @Override
            public void handle(MouseEvent event) {
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
                
                //handle the pause button
                if(key.equals(KeyCode.ESCAPE)) {
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
                else {
                    game.newCommand(key);
                }
                
            }
            
        });
        
        //execute
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    private Scene handleOptionsScreen(Stage primaryStage, Scene windowLayout) {
        VBox root = new VBox(HORIZ_BORDER);
        HBox topBuffer = new HBox();
        HBox botBuffer = new HBox();
        root.getChildren().add(topBuffer);
        
        topBuffer.setMinHeight(HORIZ_BORDER);
        
        Label controlsLabel = new Label("Controls:");
        controlsLabel.setFont(new Font("Arial", 20));
        
        root.setAlignment(Pos.TOP_CENTER);
        
        root.getChildren().add(controlsLabel);
        
        HBox cols = new HBox(HBOX_SPACE);
        VBox leftBuffer = new VBox();
        cols.getChildren().add(leftBuffer);
        
        VBox controlsBox = new VBox(DISPLAY_HEIGHT);
        HBox topBuffer2 = new HBox();
        
        cols.getChildren().add(controlsBox);
        
        controlsBox.getChildren().add(topBuffer2);
        
        controlsBox.getChildren().add(new Label("left arrow: move left"));
        controlsBox.getChildren().add(new Label("right arrow: move right"));
        controlsBox.getChildren().add(new Label("down arrow: move down"));
        controlsBox.getChildren().add(new Label("up arrow: rotate clockwise"));
        controlsBox.getChildren().add(new Label("z key: rotate counterClockwise"));
        controlsBox.getChildren().add(new Label("x key: rotate clockwise"));
        controlsBox.getChildren().add(new Label("shift: swap piece"));
        controlsBox.getChildren().add(new Label("esc: pause game"));
        
        Button backButton = new Button("back to game");
        
        root.getChildren().add(cols);
        root.getChildren().add(botBuffer);
        root.getChildren().add(backButton);
        controlsLabel.setAlignment(Pos.TOP_CENTER);
        
        Scene options = new Scene(root);
        
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //start game button pressed
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setScene(windowLayout);
            }
        });
        
        return options;
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
        timer.updateLevel(gameLevel);
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
