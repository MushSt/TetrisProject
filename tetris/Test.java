package tetris;

public class Test {
	final static int[] x = {5, 3, 2};
	final static String var = "way";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] y = x;
		
		y[1] = 5;
		
		//var = "we";
		
		System.out.println(y[1]);
		System.out.println(x[1]);
	}

}
