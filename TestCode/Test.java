package TestCode;

import java.util.concurrent.TimeUnit;

public class Test implements Runnable{
    
    private static final long DELAY = 1000;
    public static boolean x = true;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
            (new Thread(new Test())).start();
            
            try { TimeUnit.MILLISECONDS.sleep(DELAY);}
            catch (InterruptedException e){};
            
            x = false;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(x) {
            System.out.println("Hello from a thread!");
        }
    }

}
