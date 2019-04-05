package com.FitnessApp.controller;

import com.FitnessApp.configs.WebAppConfig;
import com.FitnessApp.controller.configuration.SpringSecurityWebAuxTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SpringSecurityWebAuxTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @WithUserDetails(value = "valid@gmail.com")
    @Test
    public void logIn_UserIsValid_Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

    }

    @WithUserDetails(value = "valid@gmail.com")
    @Test
    public void logIn_UserIsNotValid_Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .with(httpBasic("valid1@gmail.com", "123"))
                .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized());

    }


    @Test
    public void logIn_WithNOBasicAuth_Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "guestRole@gmail.com")
    @Test
    public void logIn_NotValidRole_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }
}
