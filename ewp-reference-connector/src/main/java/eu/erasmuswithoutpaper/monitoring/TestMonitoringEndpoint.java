/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.monitoring;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Moritz Baader
 */
@Stateless
@Path("testMonitoring")
public class TestMonitoringEndpoint {
    
    @GET
    public Response testMonitoring() {
        MonitoringParams mp = new MonitoringParams();
        mp.setServerHeiId("test.uma.es");
        mp.setApiName("omobility-las");
        mp.setEndpointName("get");
        mp.setHttpCode("500");
        mp.setServerMessage("Mensaje error Server");
        
        return Response.ok(mp).build();
    }
}
