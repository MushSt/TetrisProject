package tetrisGUI;


/*
 * This class will be used solely for keeping track of ticks
 * returns after the set delay
 */
public class DropTimer implements Runnable {
    public static final int DEFAULT_DELAY = 500;
    public static final int MIN_DELAY = 100;
    public static final int DELAY_OFFSET = 36;
    
    private static final int UNPAUSE_DELAY = 500;
    
    private StartGame game;
    private int gameLevel;
    private boolean stop;
    private static boolean paused;
    
    DropTimer(int gameLevel, StartGame game) {
        this.gameLevel = gameLevel;
        this.game = game;
        paused = false;
    }

    @Override
    public void run() {
        stop = false;
        while(!stop) {
            int delay = calcDelay();
            System.out.println("Slept: " + delay);
            
            //checkPause();
            
            try { Thread.sleep(delay); } 
            catch (InterruptedException e) { 
                System.out.println("Failed");
            }
            
            checkPause();
            
            game.dropTick();
        }
        return;
    }
    private void checkPause() {
        if(paused) {
            while(paused) {
                try { Thread.sleep(DELAY_OFFSET); } 
                catch (InterruptedException e) { 
                    System.out.println("Failed");
                }
            }
            try { Thread.sleep(UNPAUSE_DELAY); } 
            catch (InterruptedException e) { 
                System.out.println("Failed");
            }
        }
        return;
    }
    
    private int calcDelay() {
        int delay = -DELAY_OFFSET*gameLevel + DEFAULT_DELAY;
        
        if(delay < MIN_DELAY) {
            return MIN_DELAY;
        }
        if(delay > DEFAULT_DELAY) {
            return DEFAULT_DELAY;
        }
        return delay;
    }
    
    public synchronized void updateLevel(int level) {
        gameLevel = level;
    }
    
    public synchronized void stop() {
        stop = true;
    }
    
    public synchronized void pause() {
        paused = true;
    }
    
    public synchronized void unpause() {
        paused = false;
    }
    
    public synchronized boolean isPaused() {
        return paused;
    }
 }