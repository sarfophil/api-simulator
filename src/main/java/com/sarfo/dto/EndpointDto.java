package com.sarfo.dto;

import com.sarfo.domain.Endpoint;
import com.sarfo.domain.MethodType;
import lombok.Data;

@Data
public class EndpointDto {
    private String name;
    private String description;
    private String url;
    private MethodType methodType;
    private String parameters;
    private String body;
    private String response;
    private String requestType;
    private String responseType;

    public Endpoint createEndpoint(){
        Endpoint endpoint = new Endpoint();
        endpoint.setName(name);
        endpoint.setDescription(description);
        endpoint.setUrl(url);
        endpoint.setMethodType(methodType);
        endpoint.setParameters(parameters);
        endpoint.setBody(body);
        endpoint.setResponse(response);
        endpoint.setRequestType(requestType);
        endpoint.setResponseType(responseType);
        return endpoint;
    }
}
