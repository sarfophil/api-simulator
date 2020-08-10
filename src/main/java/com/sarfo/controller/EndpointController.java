package com.sarfo.controller;

import com.sarfo.api.Api;
import com.sarfo.domain.Endpoint;
import com.sarfo.dto.EndpointDto;
import com.sarfo.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EndpointController implements Api {
    private final AccountService accountService;
    public EndpointController(AccountService accountService){
        this.accountService = accountService;
    }
    // add api endpoints
    @PostMapping("app/{appId}/endpoints")
    public ResponseEntity<String> addEndpoint(@RequestBody List<EndpointDto> endpointDtos,
                                              @PathVariable(name = "appId") String appId){
        accountService.addEndpoints(endpointDtos,appId);
        return ResponseEntity.accepted().build();
    }

    // load api endpoints
    @GetMapping("app/{appId}/endpoints")
    public ResponseEntity<List<Endpoint>> getEndpoints(@PathVariable(name = "appId") String appId){
        return ResponseEntity.ok(accountService.getEndpoints(appId));
    }
}
