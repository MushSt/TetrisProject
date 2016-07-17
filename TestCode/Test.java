package TestCode;

import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Test implements Runnable{
    
    private static final long DELAY = 1000;
    public static boolean x = true;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
            (new Thread(new Test())).start();
           
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Paint x = Color.ALICEBLUE;
        
        Paint y = Color.BLACK;
        
        Paint z = Color.ALICEBLUE;
        
        Paint a = x;
        
        x = y;
        
        if(x == z) {
            System.out.println("a = z");
        }
        if(a.equals(z)) {
            System.out.println(a);
        }
        
       
    }

}
