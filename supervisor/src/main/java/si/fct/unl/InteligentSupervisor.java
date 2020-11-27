/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.fct.unl;

/**
 *
 * @author bruno and henrique
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rapidoid.http.HTTP;
import org.rapidoid.http.Req;
import org.rapidoid.http.ReqHandler;
import org.rapidoid.setup.My;
import org.rapidoid.setup.On;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.lang.StringBuilder;


/*
This class is based on a Java thread, which runs permanently and will take care of planning,  monitoring, failures detection, diagnosis and recover.
*/

public class InteligentSupervisor extends Thread{
    
    private Warehouse warehouse;
    private WebServer webserver;

    private boolean interrupted = false;

    public InteligentSupervisor(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.warehouse.initializeHardwarePorts();
    }

    public void setInterrupted(boolean value) {
        this.interrupted = value;
    }
    
    public void startWebServer() {
        On.port(8082);        
        On.get("/").html((req, resp) -> "Inteligent supervision server");
        
        On.get("/x-move-right").serve(req -> {
            warehouse.moveXRight();
            req.response().plain("OK");
            return req;
        });

        On.get("/x-move-left").serve(req -> {
            warehouse.moveXLeft();
            req.response().plain("OK");
            return req;
        });

        On.get("/x-stop").serve(req -> {
            warehouse.stopX();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/y-move-inside").serve(req -> {
            warehouse.moveYInside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/y-move-outside").serve(req -> {
            warehouse.moveYOutside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/y-stop").serve(req -> {
            warehouse.stopY();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/z-move-up").serve(req -> {
            warehouse.moveZUp();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/z-move-down").serve(req -> {
            warehouse.moveZDown();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/z-stop").serve(req -> {
            warehouse.stopZ();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/left-station-inside").serve(req -> 
        {
            warehouse.moveLeftStationInside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/left-station-outside").serve(req -> 
        {
            warehouse.moveLeftStationOutside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/left-station-stop").serve(req -> 
        {
            warehouse.stopLeftLtation();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/right-station-inside").serve(req -> 
        {
            warehouse.moveRightStationInside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/right-station-outside").serve(req -> 
        {
            warehouse.moveRightStationOutside();
            req.response().plain("OK");
            return req;
        });
        
        On.get("/right-station-stop").serve(req -> 
        {
            warehouse.stopRightStation();
            req.response().plain("OK");
            return req; 
        });
        
        On.get("/add-part-left-station").serve(req ->
        {
            warehouse.moveLeftStationInside();
            while(!(warehouse.isPartOnLeftStation())){
            } 
            warehouse.stopLeftLtation();
            
            req.response().plain("OK");
            return req;
        });
        On.get("/add-part-right-station").serve(req ->
        {
            warehouse.moveRightStationInside();
            while(!(warehouse.isPartOnRightStation())){
            } 
            warehouse.stopRightStation();
            
            req.response().plain("OK");
            return req;
        });
          
        On.get("/execute_remote_query").serve(new ReqHandler() {
            @Override
            public Object execute(Req req) throws Exception {
                String the_query = "execute_remote_query?query=" + URLEncoder.encode(req.param("query"), StandardCharsets.UTF_8);
                //String result = HTTP.get("http://localhost:8083/execute_remote_query?query="+the_query).execute().result();
                String result = InteligentSupervisor.this.executePrologQuery(the_query);
                req.response().plain(result);
                return req;
            }
        });
        
        My.errorHandler((req,resp,error) -> 
        {
            return resp.code(200).result("Error:" + error.getMessage());
        });
        
        On.get("/read_sensor_values").serve( req -> {
            JSONObject jsonObj = obtainSensorInformation();
            req.response().plain(jsonObj.toString());
            return req;
        });
        
        // forward the request from html to prolog
        // receive throught port 8082
        On.get("/query_warehouse_states").serve(req -> {
            // Send it to port 8083 - which is where prolog is
            String result = this.executePrologQuery("query_warehouse_states");
            req.response().plain(result);
            return req;
        });
        
        On.get("/query_generate_plan").serve(req -> {
            String the_query = "query_generate_plan?" + "si=" + URLEncoder.encode(req.param("si"),StandardCharsets.UTF_8) + "&"
                                                      + "sf=" + URLEncoder.encode(req.param("sf"),StandardCharsets.UTF_8);
            String result = this.executePrologQuery(the_query);
            req.response().plain(result);
            return req;
        });
        
        On.get("/query_execute_plan").serve(req-> {
            String the_query = "query_execute_plan?" + "plan=" + URLEncoder.encode(req.param("plan"),StandardCharsets.UTF_8) + "&"
                                                   + "states=" + URLEncoder.encode(req.param("states"),StandardCharsets.UTF_8);
            String result = this.executePrologQuery(the_query);
            req.response().plain(result);
            return req;
        });    
        
    }
    
    synchronized String executePrologQuery(String query)
    {
        String result = HTTP.get("http://localhost:8083/"+ query).execute().result();
        try
        {
            Thread.sleep(2);
            
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(InteligentSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public void updateKnowledgeBase()
    {
        int position, state;
        StringBuilder queryStates = new StringBuilder("true");
        
        position = warehouse.getXPosition();
        
        if(position != -1 ){
            queryStates.append(String.format(",assert_once(x_is_at(%d))",position));
        }
        else{
            queryStates.append(",retractall(x_is_at(_))");
        }
        
        
        position = warehouse.getYPosition();
        
        if(position != -1 ){
            queryStates.append(String.format(",assert_once(y_is_at(%d))",position));
        }
        else{
            queryStates.append(",retractall(y_is_at(_))");
        } 
        
        position = warehouse.getZPosition();
        
        if(position != -1 ){
            queryStates.append(String.format(",assert_once(z_is_at(%d))",position));
        }
        else{
            queryStates.append(",retractall(z_is_at(_))");
        }
        
        queryStates.append(String.format(",assert_once(x_moving(%d))",warehouse.getXMoving()));
        queryStates.append(String.format(",assert_once(y_moving(%d))",warehouse.getYMoving()));
        queryStates.append(String.format(",assert_once(z_moving(%d))",warehouse.getZMoving()));
        
        // completar aqui o resto dos sensores
        // left_station_moving, right_station_moving, is_at_z_up, is_at_z_down, is_part_at_left_station, is_part_at_right_station
        
        if(warehouse.isPartOnLeftStation()) 
            queryStates.append(",assert_once(is_part_at_left_station)");
            else
                queryStates.append(",retractall(is_part_at_left_station)");
        
        if(warehouse.isPartOnRightStation())
            queryStates.append(",assert_once(is_part_at_right_station)");
            else
                queryStates.append(",retractall(is_part_at_right_station)");
        
        if(warehouse.isPartInCage())
            queryStates.append(",assert_once(cage_has_part)");
            else
                queryStates.append(",retractall(cage_has_part)");
        
        if(warehouse.isAtZUp())
            queryStates.append(",assert_once(is_at_z_up)");
            else
                queryStates.append(",retractall(is_at_z_up)");
        
        if(warehouse.isAtZDown())
            queryStates.append(",assert_once(is_at_z_down)");
            else
                queryStates.append(",retractall(is_at_z_down)");
        /*
        queryStates.append(String.format(",assert_once(left_station_moving(%d)",warehouse.getLeftStationMoving()));
        
        queryStates.append(String.format(",assert_once(right_station_moving(%d)",warehouse.getRightStationMoving()));
        */
        //System.out.println("query=" + queryState.toString()); //user this to test if ok
        String encodedStates = URLEncoder.encode(queryStates.toString(), StandardCharsets.UTF_8);
        
        String result = this.executePrologQuery("execute_remote_query?query=" + encodedStates);
        //System.out.println(result);
    }
        
    public void run() {
        while (!interrupted) {
            try{
                executePrologQuery("query_forward");
                updateKnowledgeBase();
                invokeDispatcher();
                Thread.yield();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        // REMAINING CODE HEHE (next slides)
    }
    
    
    public void invokeDispatcher()
    {
        String result = this.executePrologQuery("query_dispatcher_json");
        if(!result.equalsIgnoreCase("[]")){
            JSONArray jsonArray = new JSONArray(result);
            
            for( int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String action = jsonObject.getString("action_name");
                executeAction(action);
            }
        }
    }
    
    public void executeAction(String action)
    {
        
        if(action.equalsIgnoreCase("move_x_right")){
            warehouse.moveXRight();
        }
        else if(action.equalsIgnoreCase("move_x_left")){
            warehouse.moveXLeft();
        }
        else if(action.equalsIgnoreCase("stop_x")){
            warehouse.stopX();
        }
        else if(action.equalsIgnoreCase("move_z_up")) {
            warehouse.moveZUp();
        }
        else if(action.equalsIgnoreCase("move_z_down")) {
            warehouse.moveZDown();
        }
        else if(action.equalsIgnoreCase("stop_z")){
            warehouse.stopZ();
        }
        else if(action.equalsIgnoreCase("move_y_inside")){
            warehouse.moveYInside();
        }
        else if(action.equalsIgnoreCase("move_y_outside")){
            warehouse.moveYOutside();
        }
        else if(action.equalsIgnoreCase("stop_y")) {
            warehouse.stopY();
        }
        else if(action.equalsIgnoreCase("move_left_station_inside")){
            warehouse.moveLeftStationInside();
        }
        else if(action.equalsIgnoreCase("move_left_station_outside")){
            warehouse.moveLeftStationOutside();
        }
        else if(action.equalsIgnoreCase("stop_left_station")){
            warehouse.stopLeftLtation();
        }
        else if(action.equalsIgnoreCase("move_right_station_inside")){
            warehouse.moveRightStationInside();
        }
        else if(action.equalsIgnoreCase("move_right_station_outside")){
            warehouse.moveRightStationOutside();
        }
        else if(action.equalsIgnoreCase("stop_right_station")){
            warehouse.stopRightStation();
        }
        else{
            System.out.printf("Dispatcher: Action %s means nothing to me. Go away! \n", action);
            return;
        }
        
        System.out.println("Executed action: " + action);
    }
    
    public synchronized JSONObject obtainSensorInformation(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("x", warehouse.getXPosition());
        jsonObj.put("y", warehouse.getYPosition());
        jsonObj.put("z", warehouse.getZPosition());
        
        jsonObj.put("x_moving", warehouse.getXMoving());
        jsonObj.put("y_moving", warehouse.getYMoving());
        jsonObj.put("z_moving", warehouse.getZMoving());
        jsonObj.put("cage", warehouse.isPartInCage());
        
        // complete for the remaining sensors
        jsonObj.put("left_station_moving", warehouse.getLeftStationMoving());
        jsonObj.put("right_station_moving", warehouse.getRightStationMoving());
        jsonObj.put("is_at_z_up", warehouse.isAtZUp());
        jsonObj.put("is_at_z_down", warehouse.isAtZDown());
        jsonObj.put("is_part_at_left_station", warehouse.isPartOnLeftStation());
        jsonObj.put("is_part_at_right_station", warehouse.isPartOnRightStation());
        jsonObj.put("cage_has_part", warehouse.isPartInCage());
        
        return jsonObj;
    }

}
