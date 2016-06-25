package tetris;

import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*------------------------------------------------------
 * Main class, use it to create a new Tetris object, 
 * maybe the overarching UI as well to get the grid size
 * 
 *-----------------------------------------------------*/

public class TetrisGUI extends Application {

    public final static int DEFAULT_GRID_WIDTH = 10;
    public final static int DEFAULT_GRID_HEIGHT = 20;
    public final static int TOP_BITS = 2;
    
    private final int TITLE_FONT = 18;
    private final int BUTTON_WIDTH = 150;
    private final int PADDING_TOP = 30;
    private final int PADDING_SIDE = 50;
    private final int SPACING = 20;
    private final int MENU_HEIGHT = 300;
    private final int MENU_WIDTH = 200;
    private final int GAME_HEIGHT = 600;
    private final int GAME_WIDTH = 400;

    public static void main(String[] args) {
        launch(args);
    }

    // user interface method
    @Override
    public void start(Stage stage) throws Exception {
        
        //Text Label menu
        Label tetris = new Label("Welcome to Tetris");
        VBox title = new VBox();
        title.getChildren().add(tetris);
        tetris.setFont(new Font("Arial", TITLE_FONT));
        title.setAlignment(Pos.TOP_CENTER);
        title.setPadding(new Insets(PADDING_TOP, 0, 0, 0));;
        
        //text label game
        Label tetrisTitle = new Label("Tetris");
        VBox gameTitle = new VBox();
        gameTitle.getChildren().add(tetrisTitle);
        gameTitle.setAlignment(Pos.TOP_CENTER);
        gameTitle.setPadding(new Insets(PADDING_TOP, 0, 0, 0));
        
        //buttons
        Button startGame = new Button("Start Game");
        Button changeDims = new Button("Change dimensions");
        startGame.setMinWidth(BUTTON_WIDTH);
        changeDims.setMinWidth(BUTTON_WIDTH);
        
        //VBox
        VBox buttons = new VBox();
        buttons.setSpacing(SPACING);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(-PADDING_TOP, 0, 0, 0)); //negative padding to offset the title padding
        //buttons.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        
        //make root for menu
        BorderPane menuRoot = new BorderPane();
        menuRoot.setCenter(buttons);
        menuRoot.setTop(title);
        
        //make root for game
        BorderPane gameRoot = new BorderPane();
        
        
        //adding children nodes to VBox in BorderPane
        buttons.getChildren().add(startGame);
        buttons.getChildren().add(changeDims);
        
        //Scene creation
        Scene menu = new Scene(menuRoot, MENU_WIDTH, MENU_HEIGHT);
        Scene game = new Scene(gameRoot, GAME_WIDTH, GAME_HEIGHT);
        

        
        //add scene to the stage
        stage.setScene(menu);
        
        //button press event
        startGame.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
                stage.setScene(game);
            }
        });
        stage.show();
    }

}
