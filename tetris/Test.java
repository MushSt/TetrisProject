package tetris;

import java.util.Scanner;

public class Test {
  final static int[] x = { 5, 3, 2 };
  final static String var = "way";

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    TetrisShape x = new TetrisShape();
    x.shapeGen();

    x.printShape();
    System.out.println();
    x = x.rotateClock();
    x.printShape();

    System.out.println();
    
    x = x.down();
    x.printShape();
    
    System.out.println();
    
    x = x.rotateClock();
    x.printShape();
  }

}
