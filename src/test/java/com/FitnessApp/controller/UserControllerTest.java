package com.FitnessApp.controller;

import com.FitnessApp.model.Role;
import com.FitnessApp.model.State;
import com.FitnessApp.model.User;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.converters.UserConverter;
import com.FitnessApp.utils.dtos.UserDto;
import com.FitnessApp.utils.validators.UserCandidateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private long id = 1;
    private int firstRes = 0;
    private int maxRes = 20;
    private MockMvc mvc;
    private ObjectMapper mapper;
    private UserDto dto;
    @Mock
    private UserCandidateValidator validator;
    @Mock
    private UserConverter converter;
    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
        dto = new UserDto("as", "as", "as", "as", "as");
        dto.setId(id);
        dto.setRole(Role.ROLE_ADMIN.name());
        dto.setState(State.ACTIVE);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto());
        when(service.getAllUser(firstRes, maxRes)).thenReturn(new ArrayList<>());
        when(converter.asListUserDto(anyObject())).thenReturn(users);

        mvc.perform(get("/admin/users")
                .param("first", String.valueOf(firstRes))
                .param("max", String.valueOf(maxRes))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllUsersNotFound() throws Exception {
        when(service.getAllUser(firstRes, maxRes)).thenReturn(new ArrayList<>());

        mvc.perform(get("/admin/users")
                .param("first", String.valueOf(firstRes))
                .param("max", String.valueOf(maxRes))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void getUserById() throws Exception {
        when(converter.asUserDto(anyObject())).thenReturn(new UserDto());

        mvc.perform(get("/admin/users/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getUserByEmail() throws Exception {
        when(converter.asUserDto(anyObject())).thenReturn(new UserDto());

        mvc.perform(get("/admin/users/by_email/ads@ca"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createUser() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(validator.isCandidate(anyObject())).thenReturn(true);
        when(service.createUser(anyObject())).thenReturn(true);


        mvc.perform(post("/admin/users/create")
                .contentType(MediaType.APPLICATION_JSON).content(jsonInString))
                .andExpect(status().is(201));
    }

    @Test
    public void createUserIsExist() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(validator.isCandidate(anyObject())).thenReturn(true);
        when(service.createUser(anyObject())).thenReturn(false);


        mvc.perform(post("/admin/users/create")
                .contentType(MediaType.APPLICATION_JSON).content(jsonInString))
                .andExpect(status().is(409));
    }

    @Test
    public void createUserIsNotExist() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(validator.isCandidate(anyObject())).thenReturn(false);


        mvc.perform(post("/admin/users/create")
                .contentType(MediaType.APPLICATION_JSON).content(jsonInString))
                .andExpect(status().is(400));
    }

    @Test
    public void update() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(converter.asUser(anyObject())).thenReturn(new User());
        when(service.update(anyLong(), anyObject())).thenReturn(true);

        mvc.perform(put("/admin/users/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString))
                .andExpect(status().isOk());
    }

    @Test
    public void updateIsNotFound() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(converter.asUser(anyObject())).thenReturn(new User());
        when(service.update(anyLong(), anyObject())).thenReturn(false);

        mvc.perform(put("/admin/users/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteIsExist() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(converter.asUser(anyObject())).thenReturn(new User());
        when(service.delete(anyLong(), anyObject())).thenReturn(true);

        mvc.perform(delete("/admin/users/delete/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteIsNotFound() throws Exception {
        String jsonInString = mapper.writeValueAsString(dto);
        when(converter.asUser(anyObject())).thenReturn(new User());
        when(service.delete(anyLong(), anyObject())).thenReturn(true);

        mvc.perform(delete("/admin/users/delete/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInString))
                .andExpect(status().isOk());
    }
}
