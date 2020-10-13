/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.fct.unl;

import org.rapidoid.setup.App;
import org.rapidoid.setup.On;
import org.rapidoid.setup.Setup;

/**
 *
 * @author bruno
 */
public class WebServer {
    
    public static void startServer()
    {
        On.port(8082);
        On.get("/").html((req, resp) -> "Hello again!");     
    }
}
