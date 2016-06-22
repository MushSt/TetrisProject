package tetris;

import java.util.Scanner;

/*------------------------------------------------------
 * Main class, use it to create a new Tetris object, 
 * maybe the overarching UI as well to get the grid size
 * 
 *-----------------------------------------------------*/

public class Tetris {

  public final static int GRID_WIDTH = 10;
  public final static int GRID_HEIGHT = 20;
  public final static int TOP_BITS = 2;

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.println("Grid Size? (Default is 10x20)");
    System.out.println("Enter query as widthxheight:");

    String line = scan.nextLine();

    TetrisGame newGame = new TetrisGame(validLine(line));
    newGame.start();

    scan.close();
  }

  /*
   * takes in the user input and parses it for width/height if invalid, defaults
   * to (10x20)
   */
  private static Coordinate validLine(String line) {
    Coordinate sizes;

    if (line.length() < 3) {
      // invalid, so we go with the default
      System.out.println("bad input, using default values of 10x20");
      return new Coordinate(GRID_WIDTH, GRID_HEIGHT);
    }

    // check valid numbers
    try {
      int indX = line.indexOf('x');
      String w = line.substring(0, indX);
      String h = line.substring(indX + 1);

      int width = Integer.parseInt(w);
      int height = Integer.parseInt(h);
      sizes = new Coordinate(width, height);
      System.out.println("using " + width + " as width, and " + height + " as height");
    } catch (Exception e) {
      System.out.println("bad input, using default values of 10x20");
      return new Coordinate(GRID_WIDTH, GRID_HEIGHT);
    }

    return sizes;
  }
}
