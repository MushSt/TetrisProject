package tetrisGUI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

public class TextDraw {
    
    private final Paint TEXT_COLOR = Color.BLACK;
    private final int LEFT_OFFSET = 20;
    private final int TOP_OFFSET = 10;
    
    private GraphicsContext gc;
    
    public TextDraw(Canvas display) {
        this.gc = display.getGraphicsContext2D();
    }
    
    
    public void updateText(String text) {
        Canvas disp = gc.getCanvas();
        double w = disp.getWidth();
        double h = disp.getHeight();
        gc.clearRect(0, 0, w, h);
        gc.setFill(TEXT_COLOR);
        gc.strokeText(text, LEFT_OFFSET, TOP_OFFSET);
        //gc.fillRect(0, 0, 40, 40);
    }
}
