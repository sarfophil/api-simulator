package com.sarfo.controller;

import com.sarfo.api.Api;
import com.sarfo.domain.Application;
import com.sarfo.dto.AccountDto;
import com.sarfo.dto.ApplicationDto;
import com.sarfo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController()
public class AccountController implements Api {
    private final AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("account")
    public ResponseEntity<Void> createAccount(@RequestBody AccountDto account){
        accountService.createAccount(account.getEmail(),account.getPassword());
        return ResponseEntity.created(URI.create("/v1/account")).build();
    }

    @PostMapping("apps")
    public ResponseEntity<String> createApp(@RequestBody ApplicationDto applicationDto,
                                            @AuthenticationPrincipal Principal principal){
        this.accountService.addApplication(principal.getName(), applicationDto);
        return new ResponseEntity<>("App Created", HttpStatus.CREATED);
    }

    @GetMapping("apps")
    public ResponseEntity<List<Application>> getApps(@AuthenticationPrincipal Principal principal){
        return new ResponseEntity<>(accountService.getApplications(principal.getName()),HttpStatus.OK);
    }
}
