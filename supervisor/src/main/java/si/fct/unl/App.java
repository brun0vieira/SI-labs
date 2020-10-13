package si.fct.unl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Warehouse warehouse = new Warehouse();
        warehouse.initializeHardwarePorts();
        Button buttonXRight = new Button("x-right");
        Button buttonXLeft = new Button("x-left");
        Button buttonXStop = new Button("x-stop");
        buttonXRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonXLeft.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonXStop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Button buttonYInside = new Button("y-inside");
        Button buttonYOutside = new Button("y-outside");
        Button buttonYStop = new Button("y-stop");
        buttonXRight.setOnAction(event -> {
                warehouse.moveXRight();
                System.out.println("x moving right");
        });
        GridPane root = new GridPane();
        root.add(buttonXRight, 1, 1);
        root.add(buttonXLeft, 2, 1);
        root.add(buttonXStop, 3, 1);
        root.add(buttonYInside, 1, 2);
        root.add(buttonYOutside, 2, 2);
        root.add(buttonYStop, 3, 2);
        root.setHgap(10);
        root.setHgap(10);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Hello world");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}