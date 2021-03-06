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
import javafx.stage.WindowEvent;
import javafx.application.Platform;

/**
 * JavaFX App
 */
public class App extends Application {
    
    Warehouse warehouse = new Warehouse();
    final InteligentSupervisor supervisor  = new InteligentSupervisor(warehouse);
    
    /*
    public class InteligentSupervisor extends Thread {
        private Warehouse warehouse;
        private boolean interrupted = false;

        public InteligentSupervisor(Warehouse warehouse) {
            this.warehouse = warehouse;
        }
    }
    */
    
    @Override
    public void start(Stage primaryStage) {
            
        new Thread() {
            public void run() {
            warehouse.initializeHardwarePorts();
            }
        }.start();

        
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
        Button buttonZUp = new Button("z-up");
        Button buttonZDown = new Button("z-down");
        Button buttonZStop = new Button("z-stop");
        buttonZUp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonZDown.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonZStop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // left station
        Button buttonLeftStationInside = new Button("left-station-inside");
        Button buttonLeftStationOutside = new Button("left-station-outside");
        Button buttonStopLeftStation = new Button("left-station-stop");
        buttonLeftStationInside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonLeftStationOutside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonStopLeftStation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // right station
        Button buttonRightStationInside = new Button("right-station-inside");
        Button buttonRightStationOutside = new Button("right-station-outside");
        Button buttonStopRightStation = new Button("right-station-stop");
        buttonRightStationInside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonRightStationOutside.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonStopRightStation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // other buttons
        Button buttonLaunchProlog = new Button("Launch Prolog");
        Button buttonSupervisionUI = new Button("Launch SI-UI");
        Button buttonTestFunction = new Button("Test function");
        Button startSupervisorButton = new Button("Start Supervisor");
        buttonLaunchProlog.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        buttonSupervisionUI.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        startSupervisorButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
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
        buttonZUp.setOnAction(event -> {
            warehouse.moveZUp();
            System.out.println("y moving inside");
        });
        
        buttonZDown.setOnAction(event -> {
            warehouse.moveZDown();
            System.out.println("y moving outside");
        });
        
        buttonZStop.setOnAction(event -> {
            warehouse.stopZ();
            System.out.println("y stopped moving");
        });
        
        // left station
        buttonLeftStationInside.setOnAction(event -> {
            warehouse.moveLeftStationInside();
            System.out.println("left station moving inside");
        });
        
        buttonLeftStationOutside.setOnAction(event -> {
            warehouse.moveLeftStationOutside();
            System.out.println("left station moving outside");
        });
        
        buttonStopLeftStation.setOnAction(event-> {
            warehouse.stopLeftLtation();
            System.out.println("left station stoped moving");
        });
        
        //right station
        buttonRightStationInside.setOnAction(event -> {
            warehouse.moveRightStationInside();
            System.out.println("right station moving inside");
        });
        
        buttonRightStationOutside.setOnAction(event -> {
            warehouse.moveRightStationOutside();
            System.out.println("right station moving outside");
        });
        
        buttonStopRightStation.setOnAction(event-> {
            warehouse.stopRightStation();
            System.out.println("right station stoped moving");
        });
        
        // other buttons events
        buttonSupervisionUI.setOnAction(event->{
            buttonSupervisionUI.setDisable(true);
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
                buttonLaunchProlog.setDisable(true);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }                
        });
        
        // button to test functions from warehouse.c
        buttonTestFunction.setOnAction(event -> {
            warehouse.moveLeftStationInside();
            System.out.println("\nTesting function: ");
        });
        
        startSupervisorButton.setOnAction(event-> {
            startSupervisorButton.setDisable(true);
            supervisor.start();
        });
        
        GridPane root = new GridPane();
        root.add(buttonXRight, 1, 1);
        root.add(buttonXLeft, 2, 1);
        root.add(buttonXStop, 3, 1);
        
        root.add(buttonYInside, 1, 2);
        root.add(buttonYOutside, 2, 2);
        root.add(buttonYStop, 3, 2);
        
        root.add(buttonZUp, 1,3);
        root.add(buttonZDown, 2, 3);
        root.add(buttonZStop,3,3);
        
        root.add(buttonLeftStationInside,1,4);
        root.add(buttonLeftStationOutside,2,4);
        root.add(buttonStopLeftStation,3,4);
        
        root.add(buttonRightStationInside,1,5);
        root.add(buttonRightStationOutside,2,5);
        root.add(buttonStopRightStation,3,5);
        
        
        root.add(buttonLaunchProlog, 1, 7);
        root.add(buttonSupervisionUI, 2, 7);
        root.add(startSupervisorButton, 3, 7);
        
        root.setHgap(10);
        root.setHgap(10);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("JavaFX APP");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private void closeWindowEvent(WindowEvent event)
    {
        System.out.println("Window close request...");
        supervisor.setInterrupted(true);
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

}
    
