package com.FitnessApp.controller;

import com.FitnessApp.services.GoalService;
import com.FitnessApp.utils.dtos.GoalDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GoalControllerTest {
    private Long id = 1L;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private String formBody = mapper.writeValueAsString(new GoalDto());

    @Mock
    private GoalService service;

    @InjectMocks
    private GoalController goalController;

    public GoalControllerTest() throws JsonProcessingException {
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(goalController).build();
    }

    @Test
    public void createGoalIsNotExist() throws Exception {
        when(service.createGoal("fds")).thenReturn(true);

        mockMvc.perform(
                post("/admin/goals/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("goalTitle", "fds"))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    public void createGoalIsExist() throws Exception {
        when(service.createGoal("fds")).thenReturn(false);

        mockMvc.perform(
                post("/admin/goals/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("goalTitle", "fds"))
                .andExpect(status().is(409))
                .andDo(print());
    }

    @Test
    public void getAllGoals() throws Exception {
        when(service.getAll()).thenReturn(Optional.of(new ArrayList<>()));

        mockMvc.perform(
                get("/admin/goals"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateGoalIsExist() throws Exception {
        when(service.updateGoal(anyObject(), anyLong())).thenReturn(true);

        mockMvc.perform(
                put("/admin/goals/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formBody))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateGoalIsNotExist() throws Exception {
        when(service.updateGoal(anyObject(), anyLong())).thenReturn(false);

        mockMvc.perform(
                put("/admin/goals/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formBody))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteGoalIsExist() throws Exception {
        when(service.deleteGoal(anyObject(), anyLong())).thenReturn(true);

        mockMvc.perform(
                delete("/admin/goals/delete/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formBody))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteGoalIsNotExist() throws Exception {
        when(service.deleteGoal(anyObject(), anyLong())).thenReturn(false);

        mockMvc.perform(
                delete("/admin/goals/delete/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(formBody))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
