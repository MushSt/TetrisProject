package tetris;

import java.util.Scanner;

public class TetrisGame {

  private int width;
  private int height;
  private GridInfo board;

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
      TetrisShape currShape = generate();
      currShape.starting(width);

      // 2nd while loop for dropping and rotating
      while (true) {
        // check if the shape can be placed there
        if (!board.checkShape(currShape)) {
          board.printGrid(currShape);
          System.out.println(gameOver);
          gameOver = true;
        }
        // gets the user input
        String command = scan.next();

        //

      }
    }
    // resets the board
    board.gameOver();
  }

  private TetrisShape generate() {
    TetrisShape x = new TetrisShape();
    x.shapeGen();

    return x;
  }

}
