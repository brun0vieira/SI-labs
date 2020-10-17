/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.fct.unl;

/**
 *
 * @author bruno
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

/*
This class is based on a Java thread, which runs permanently and will take care of planning,  monitoring, failures detection, diagnosis and recover.
*/

public class InteligentSupervisor extends Thread{
    
    private Warehouse warehouse;
    private WebServer webserver;

    private boolean interrupted = false;

    public InteligentSupervisor() {
        this.warehouse = new Warehouse();
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
        
        On.get("/execute_remote_query").serve(new ReqHandler() {
            @Override
            public Object execute(Req req) throws Exception {
                String the_query = URLEncoder.encode(req.param("query"), StandardCharsets.UTF_8);
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
        
    public void run() {
        while (!interrupted) {
            Thread.yield();
        }
        
        // REMAINING CODE HEHE (next slides)
    }

}
