package com.sarfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarfo.api.Api;
import com.sarfo.domain.MethodType;
import com.sarfo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * exposed endpoints to make client requests
 */
@RestController()
@RequestMapping("/simulation/")
public class AppController implements Api {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    public AppController(AccountService accountService,ObjectMapper objectMapper){
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("{appId}/{endpointName}")
    public @ResponseBody String doGet(@PathVariable("endpointName") String endpointName,
                   @PathVariable("appId") String appId)  {
        return accountService.getEndpointByUrl(appId,endpointName, MethodType.GET.name()).getResponse();
    }

    @PostMapping("{appId}/{endpointName}")
    public ResponseEntity<String> doPost(@PathVariable("endpointName") String endpointName,
                                    @PathVariable("appId") String appId){
        String body = accountService
                .getEndpointByUrl(appId,endpointName, MethodType.POST.name()).getResponse();
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }

    @PutMapping("{appId}/{endpointName}")
    public ResponseEntity<String> doPut(@PathVariable("endpointName") String endpointName,
                                        @PathVariable("appId") String appId){
        String body = accountService
                .getEndpointByUrl(appId,endpointName,MethodType.PUT.name()).getResponse();
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

    @DeleteMapping("{appId}/{endpointName}")
    public ResponseEntity<String> doDelete(@PathVariable("endpointName") String endpointName,
                                           @PathVariable("appId") String appId){
        String body = accountService
                .getEndpointByUrl(appId,endpointName,MethodType.DELETE.name()).getResponse();
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

}
