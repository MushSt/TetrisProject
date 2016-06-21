package tetris;

public class Test {
	final static int[] x = {5, 3, 2};
	final static String var = "way";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] y = x;
		
		y[1] = 5;
		
		//var = "we";
		Coordinate x = new Coordinate(5,6);
		
		System.out.println(x.equals(6));
		
		System.out.println(y[1]);
	}

}
