package com.sarfo.dto;

import com.sarfo.domain.Application;
import com.sarfo.domain.Endpoint;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationDto {
    private String name;
    private String description;
    private String accountId;
    private List<Endpoint> endpoints = new ArrayList<>();

    public Application createApplication(){
        Application application = new Application();
        application.setName(name);
        application.setDescription(description);
        application.setEndpoint(endpoints);
        application.setAccountId(accountId);
        return application;
    }
}
