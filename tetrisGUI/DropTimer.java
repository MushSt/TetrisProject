package tetrisGUI;


/*
 * This class will be used solely for keeping track of ticks
 * returns after the set delay
 */
public class DropTimer implements Runnable {
    public static final int DEFAULT_DELAY = 1000;
    public static final int MIN_DELAY = 100;
    public static final int DELAY_OFFSET = 36;
    
    private StartGame game;
    private int gameLevel;
    private boolean gameOver;
    
    DropTimer(int gameLevel, StartGame game) {
        this.gameLevel = gameLevel;
        this.game = game;
    }

    @Override
    public void run() {
        gameOver = false;
        while(!gameOver) {
            int delay = calcDelay();
            System.out.println("Slept: " + delay);
            
            
            try { Thread.sleep(delay); } 
            catch (InterruptedException e) { 
                System.out.println("Failed");
            }
            
            System.out.println("finished sleep");
            game.dropTick();
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
    
    public synchronized void gameOver() {
        gameOver = true;
    }
 }