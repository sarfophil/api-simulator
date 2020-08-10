package com.sarfo.service;

import com.sarfo.domain.Endpoint;
import com.sarfo.domain.MethodType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;


    @Test
    public void shouldCallGetEndpointsAndReturnList(){
        String DUMMY_ENDPOINTS = "5f308dbbf3fae51a1eefc9fc";
        List<Endpoint> endpoints = accountService.getEndpoints(DUMMY_ENDPOINTS);
        assertNotEquals(endpoints.size(),0);
    }

   @Test
   public void shouldCallGetEndpointByUrl(){
     String DUMMY_APP_ID = "5f308dbbf3fae51a1eefc9fc";
     String DUMMY_URL = "employees";
     String DUMMY_REQUEST_TYPE = MethodType.GET.name();
     log.info("Method Type: {}",DUMMY_REQUEST_TYPE);
     assertThrows("Throws No Such ElementException",NoSuchElementException.class,
             () -> accountService.getEndpointByUrl(DUMMY_APP_ID,DUMMY_URL,DUMMY_REQUEST_TYPE));
   }


}