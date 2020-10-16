package si.fct.unl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        InteligentSupervisor supervisor = new InteligentSupervisor();
        supervisor.startWebServer();
        
        // x - buttons
        Button buttonXRight = new Button("x-right");
        Button buttonXLeft = new Button("x-left");
        Button buttonXStop = new Button("x-stop");
        buttonXRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonXLeft.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonXStop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // y - buttons
        Button buttonYInside = new Button("y-inside");
        Button buttonYOutside = new Button("y-outside");
        Button buttonYStop = new Button("y-stop");
        buttonYInside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonYOutside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonYStop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // z - buttons

        
        // other buttons
        Button buttonLaunchProlog = new Button("Launch Prolog");
        Button buttonSupervisionUI = new Button("Launch SI-UI");
        Button buttonTestFunction = new Button("Test function");

        // x - events
        buttonXRight.setOnAction(event -> {
                warehouse.moveXRight();
                System.out.println("x moving right");
        });
        
        buttonXLeft.setOnAction(event -> {
            warehouse.moveXLeft();
            System.out.println("x moving left");
        });
        
        buttonXStop.setOnAction(event -> {
            warehouse.stopX();
            System.out.println("x stopped moving");
        });
        
        // y - events
        buttonYInside.setOnAction(event -> {
            warehouse.moveYInside();
            System.out.println("y moving inside");
        });
        
        buttonYOutside.setOnAction(event -> {
            warehouse.moveYOutside();
            System.out.println("y moving outside");
        });
        
        buttonYStop.setOnAction(event -> {
            warehouse.stopY();
            System.out.println("y stopped moving");
        });
        
        // z - events
        
        
        
        
        // other buttons events
        buttonSupervisionUI.setOnAction(event->{
            try {
               
                java.awt.Desktop.getDesktop().browse(new URI("http://localhost:8082/supervisor-ui.html"));
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }                
        });

        buttonLaunchProlog.setOnAction(event->{
            try {
                String folder = System.getProperty("user.dir");
                Runtime.getRuntime().exec("swipl-win.exe -f "+folder +"/kbase/supervisor.pl -g main");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }                
        });
        
        // button to test functions from warehouse.c
        buttonTestFunction.setOnAction(event -> {
            warehouse.moveLeftStationInside();
            System.out.println("\nTesting function: ");
        });
        
        GridPane root = new GridPane();
        root.add(buttonXRight, 1, 1);
        root.add(buttonXLeft, 2, 1);
        root.add(buttonXStop, 3, 1);
        root.add(buttonYInside, 1, 2);
        root.add(buttonYOutside, 2, 2);
        root.add(buttonYStop, 3, 2);
        root.add(buttonLaunchProlog, 1, 3);
        root.add(buttonSupervisionUI, 2, 3);
        root.add(buttonTestFunction, 3, 3);
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