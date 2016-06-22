package tetris;

import java.util.Scanner;

public class TetrisGame {

  final private char LEFT = 'a';
  final private char RIGHT = 'd';
  final private char DOWN = 's';
  final private char DROP = ' ';
  final private char CLOCKWISE = 'w';
  final private char COUNTER_CLOCKWISE = 'r';
  
  private int width;
  private int height;
  private GridInfo board;
  private TetrisShape currShape;

  // default of 10x20
  public TetrisGame() {
    this(new Coordinate(Tetris.DEFAULT_GRID_WIDTH, Tetris.DEFAULT_GRID_HEIGHT));
  }

  public TetrisGame(Coordinate dims) {
    width = dims.getRow();
    height = dims.getCol();

    // make the game board
    board = new GridInfo(width, height);
  }

  /*
   * starts the game loop until game over, then reset
   */
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
        // check if the shape can be placed there, and gameOver condition
        if (!board.checkShape(currShape)) {
          board.printGrid(currShape);
          System.out.println(gameOver);
          gameOver = true;
          break;
        }
        
        board.printGrid(currShape);
        
        // gets the user input
        String command = scan.nextLine();

        
        //does something with the input
        if(parseCommand(command)) {
          //if returns true, we set the currShape and need a ne one
          break;
        }
      }
    }
    // resets the board
    board.gameOver();
  }
  
  /*
   * helper method for parsing the user input and executes
   * checks for invalid input
   * a left, d right, space drop, s down, w rotateC, r rotate CC
   * returns true if piece was set
   */
  private boolean parseCommand(String command) {
    //first find out what command it is, if invalid then do nothing
    char letterCommand = command.charAt(0);
    TetrisShape tempShape = currShape;
    
    switch(letterCommand) {
      case LEFT: //left
        tempShape = currShape.left();
        break;
      case RIGHT: //right
        tempShape = currShape.right();
        break;
      case DOWN: //down
        if(board.canSetShape(currShape)) {
          board.setShape(currShape);
          return true;
        }
        tempShape = currShape.down();
        break;
      case CLOCKWISE: //clockwise
        tempShape = currShape.rotateClock();
        break;
      case COUNTER_CLOCKWISE: //counterclockwise
        tempShape = currShape.rotateCounter();
        break;
      case DROP:
        board.dropShape(currShape);
        return true;
      default:
        break;
    }
    //if translated position is valid, then update it
    if(board.checkShape(tempShape)) {
      currShape = tempShape;
    }
    return false;
  }

}
