package com.FitnessApp.controller;

import com.FitnessApp.services.impl.SignUpService;
import com.FitnessApp.utils.validators.UserCandidateValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignUpControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private SignUpController controller;
    @Mock
    private SignUpService service;
    @Mock
    private UserCandidateValidator validator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void signUpIsNotExist() throws Exception {
        when(validator.isCandidate(anyObject())).thenReturn(true);
        when(service.creteUser(anyObject())).thenReturn(true);

        this.mockMvc.perform(post("/sign_up")).andExpect(status().is(201));
    }

    @Test
    public void signUpIsExist() throws Exception {
        when(validator.isCandidate(anyObject())).thenReturn(true);
        when(service.creteUser(anyObject())).thenReturn(false);

        this.mockMvc.perform(post("/sign_up")).andExpect(status().is(409));
    }

    @Test
    public void signUpIsBadValuesOfRequest() throws Exception {
        when(validator.isCandidate(anyObject())).thenReturn(false);
        when(service.creteUser(anyObject())).thenReturn(true);

        this.mockMvc.perform(post("/sign_up")).andExpect(status().is(400));
    }
}
