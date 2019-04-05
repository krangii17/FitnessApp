package com.FitnessApp.controller;

import com.FitnessApp.model.Gender;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.services.UserProfileService;
import com.FitnessApp.services.impl.UserServiceImpl;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserProfileControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @InjectMocks
    private UserProfileController controller;

    @Mock
    private UserProfileService service;

    @Mock
    private UserServiceImpl userService;
    private MockMvc mockMvc;

    private User user;
    private UserParameters userParameters;
    private UserProfileDTO userParametersDTO;

    private ObjectMapper mapper;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mapper = new ObjectMapper();

        userParameters = new UserParameters();
        userParameters.setID(1L);
        userParameters.setFirstName("John");
        userParameters.setLastName("Smith");
        userParameters.setPhone("1234567");
        userParameters.setBirthDate(LocalDate.now());
        userParameters.setGender(Gender.MALE);
        userParameters.setWeight(90);
        userParameters.setHeight(190);
        userParameters.setTrainDayQuantity(5);

        user = new User();
        user.setId(2L);
        user.setUsername("Neo");
        user.setEmail("neo@gmail.com");
        user.setUserParameters(userParameters);

        userParametersDTO = new UserProfileDTO(
                1L, "John", "Smith", "Neo", "neo@gmail.com",
                "1234567", java.sql.Date.valueOf(LocalDate.now()),
                Gender.MALE, 90, 190, 5);
    }


    @Test
    public void getUserParametersById_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(userParametersDTO);

        when(userService.getUserById(user.getId()))
                .thenReturn(user);

        when(service.getProfileData(user))
                .thenReturn(userParametersDTO);

        this.mockMvc.perform(get("/profiles/users/" + user.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }


    @Test
    public void addUserProgramByUserID_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(userParametersDTO);

        UserProfileDTO addedUserParameters = mapper.readValue(jsonInString, UserProfileDTO.class);

        when(userService.getUserById(user.getId()))
                .thenReturn(user);

        when(service.changeProfileData(addedUserParameters, user)).
                thenReturn(true);


        this.mockMvc.perform(post("/profiles/users/" + user.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Added"));
    }

    @Test
    public void addUserProgramByUserID_Not_Saved_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(userParametersDTO);

        UserProfileDTO addedUserParameters = mapper.readValue(jsonInString, UserProfileDTO.class);

        when(userService.getUserById(user.getId()))
                .thenReturn(user);

        when(service.changeProfileData(addedUserParameters, user)).
                thenReturn(false);


        this.mockMvc.perform(post("/profiles/users/" + user.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }


    @Test
    public void deleteUserProgram_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(userParametersDTO);

        doNothing().when(service).deleteById(userParametersDTO.getId());

        this.mockMvc.perform(delete("/profiles/" + userParametersDTO.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
