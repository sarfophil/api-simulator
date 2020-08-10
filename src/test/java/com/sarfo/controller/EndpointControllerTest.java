package com.sarfo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EndpointControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final String DUMMY_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIi" +
            "OiJrYXkuc2FyZm9AZ21haWwuY29tIiwicm9sZXMiOlsiVVNFUiJdLCJpc3MiOiJzYXJmby1hcHAiLCJleHAiOjE1OTc4ODA" +
            "2NDEsImlhdCI6MTU5NzAxNjY0MX0.TA4araS6pnapPuS-e2UEoickPqnsDnjnhfi1dwBPr7SnJBnK7ynLI7sjnYJ9" +
            "hgcCRDxc5SchsuD6ayoPB68yPQ";

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void shouldCallGetEndpointsAndReturnOk() throws Exception {
        String DUMMY_APPID = "5f308dbbf3fae51a1eefc9fc";
        mockMvc.perform(get("/v1/app/"+ DUMMY_APPID +"/endpoints")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",DUMMY_TOKEN)).andExpect(status().isOk());
    }
}