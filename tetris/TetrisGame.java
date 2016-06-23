package tetris;

import java.util.Scanner;

public class TetrisGame {

    final private char LEFT = 'a';
    final private char RIGHT = 'd';
    final private char DOWN = 's';
    final private char DROP = ' ';
    final private char CLOCKWISE = 'w';
    final private char COUNTER_CLOCKWISE = 'r';
    final private int DROP_SCORE = 10;
    final private int ONE_LINE = 40;
    final private int TWO_LINES = 100;
    final private int THREE_LINES = 300;
    final private int FOUR_LINES = 1200;
    final private int ONE = 1;
    final private int TWO = 2;
    final private int THREE = 3;
    final private int FOUR = 4;
    final private int LEVELUP = 10;

    private int width;
    private int height;
    private GridInfo board;
    private TetrisShape currShape;

    // stats to keep track of
    private int linesCleared;
    private int gameLevel;
    private int currentScore;
    private int highScore;

    // default of 10x20
    public TetrisGame() {
        this(Tetris.DEFAULT_GRID_WIDTH, Tetris.DEFAULT_GRID_HEIGHT);
    }

    public TetrisGame(int width, int height) {
        this.width = width;
        this.height = height;

        // make the game board
        board = new GridInfo(this.width, this.height);
    }

    /**------------------------------------------------------------------------
     * starts the game loop until game over, then reset
     *-----------------------------------------------------------------------*/
    public void start() {
        System.out.println("Starting Game...");
        Scanner scan = new Scanner(System.in);
        boolean gameOver = false;

        // outer while loop to generate the shapes
        while (!gameOver) {
            // generate new shape and change its coords to start
            currShape = new TetrisShape();
            currShape.shapeGen();
            currShape.starting(width);

            // 2nd while loop for dropping and rotating
            while (true) {
                // check if the shape can be placed there, and gameOver
                // condition
                if (!board.checkShape(currShape)) {
                    board.printGrid(currShape);
                    gameOver();
                    gameOver = true;
                    break;
                }

                board.printGrid(currShape);

                // gets the user input
                String command = scan.nextLine();
                if(command.length() == 0) {
                    continue;
                }
                // does something with the input
                if (parseCommand(command)) {
                    // if returns true, we set the currShape and need a new one
                    updateScoreDrop(); //increase score for successful drop
                    break;
                }
            }
        }
        // resets the board
        board.gameOver();
        scan.close();
    }

    /**------------------------------------------------------------------------
     * helper method for parsing the user input and executes checks for
     * invalid input a left, d right, space drop, s down, w rotateC, r rotate CC
     * 
     * @param command
     *            the user input
     * 
     * @return true if piece was set
     *-----------------------------------------------------------------------*/
    private boolean parseCommand(String command) {
        // first find out what command it is, if invalid then do nothing
        char letterCommand = command.charAt(0);
        TetrisShape tempShape = currShape;

        switch (letterCommand) {
            case LEFT:
                tempShape = currShape.left();
                break;
            case RIGHT:
                tempShape = currShape.right();
                break;
            case DOWN:
                if (board.canSetShape(currShape)) {
                    calculateLineScores(board.setShape(currShape));
                    return true;
                }
                tempShape = currShape.down();
                break;
            case CLOCKWISE:
                tempShape = currShape.rotateClock();
                break;
            case COUNTER_CLOCKWISE:
                tempShape = currShape.rotateCounter();
                break;
            case DROP:
                calculateLineScores(board.dropShape(currShape));
                return true;
            default:
                break;
        }
        // if translated position is valid, then update it
        if (board.checkShape(tempShape)) {
            currShape = tempShape;
        }
        return false;
    }
    
    //TODO
    /**------------------------------------------------------------------------
     * helper method for updating and calculating the scores based on
     * line clears
     * 
     * @param numLines
     *            number of lines cleared by the block
     *-----------------------------------------------------------------------*/
    private void calculateLineScores(int numLines) {
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
        linesCleared += numLines;
        updateGameLevel();
    }
    
    /**------------------------------------------------------------------------
     * helper method for updating the score after successfully setting a shape
     *-----------------------------------------------------------------------*/
    private void updateScoreDrop() {
        currentScore += DROP_SCORE;
    }
    
    /**------------------------------------------------------------------------
     * helper method to take care of the details when game over
     *-----------------------------------------------------------------------*/
    private void gameOver() {
        //update bestScore
        if(currentScore > highScore) {
            highScore = currentScore;
        }
        System.out.println("Game Over, you reached level " +gameLevel);
        System.out.println("Score: " +currentScore);
        System.out.println("High Score: " +highScore);
        System.out.println("Lines Cleared: " +linesCleared);
        
        currentScore = 0;
        linesCleared = 0;
        gameLevel = 0;
    }
    
    /**------------------------------------------------------------------------
     * updates the game level based on lines cleared
     * 
     * every LEVELUP lines cleared, we level up
     *-----------------------------------------------------------------------*/
    private void updateGameLevel() {
        gameLevel = linesCleared % LEVELUP;
    }

}
