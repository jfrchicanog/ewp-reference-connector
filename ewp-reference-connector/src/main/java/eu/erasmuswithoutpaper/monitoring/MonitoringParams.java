/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.monitoring;

import javax.ws.rs.FormParam;

/**
 *
 * @author Moritz Baader
 */
public class MonitoringParams {
    
    @FormParam("server_hei_id")
    private String serverHeiId;

    @FormParam("api_name")
    private String apiName;

    @FormParam("endpoint_name")
    private String endpointName;

    @FormParam("http_code")
    private String httpCode;

    @FormParam("server_message")
    private String serverMessage;

    @FormParam("client_message")
    private String clientMessage;

    public String getServerHeiId() {
        return serverHeiId;
    }

    public void setServerHeiId(String serverHeiId) {
        this.serverHeiId = serverHeiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }
    
    
    
}
