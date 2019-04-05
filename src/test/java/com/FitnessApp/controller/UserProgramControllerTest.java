package com.FitnessApp.controller;

import com.FitnessApp.model.State;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserProgram;
import com.FitnessApp.services.impl.UserProgramService;
import com.FitnessApp.services.impl.UserServiceImpl;
import com.FitnessApp.utils.converters.UserProgramConverter;
import com.FitnessApp.utils.dtos.UserProgramDTO;
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

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserProgramControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    private final Long index = 0L;
    @InjectMocks
    private UserProgramController controller;
    @Mock
    private UserProgramConverter converter;
    @Mock
    private UserProgramService service;
    @Mock
    private UserServiceImpl userService;
    private MockMvc mockMvc;
    private List<UserProgram> list;
    private List<UserProgramDTO> listDTO;
    private User user;

    private UserProgram setUserProgramID(Long id, UserProgram userProgram) {
        userProgram.setId(id);
        return userProgram;
    }

    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        user = new User();
        user.setId(1L);
        user.setEmail("qwerty@gmail.com");
        user.setState(State.ACTIVE);
        user.setPassword("pass");
        user.setSecretAnswer("answer");
        user.setSecretQuestion("question");
        user.setUsername("name");

        list = new ArrayList<>();
        list.add(setUserProgramID(1L,
                new UserProgram(user, "Base_1",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"), false)));
        list.add(setUserProgramID(2L,
                new UserProgram(user, "Base_2",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-15"), false)));
        list.add(setUserProgramID(3L,
                new UserProgram(user, "Base_3",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-15"), true)));

        listDTO = new ArrayList<>();
        listDTO.add(new UserProgramDTO(1L, "Base_1",
                new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"), false));
        listDTO.add(new UserProgramDTO(2L, "Base_2",
                new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-15"), false));
        listDTO.add(new UserProgramDTO(3L, "Base_3",
                new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-15"), true));
    }

    @After
    public void tearDown() {
        list.clear();
        listDTO.clear();
    }

    @Test
    public void get_UserProgram_By_Id_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        when(service.getByID(index)).thenReturn(list.get(index.intValue()));
        when(converter.convertToDto(list.get(index.intValue())))
                .thenReturn(listDTO.get(index.intValue()));


        this.mockMvc.perform(get("/user-programs/" + index))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_UserProgram_By_User_Id_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO);

        when(service.getByParameter("user.id", user.getId().toString()))
                .thenReturn(list);

        for (int i = 0; i < listDTO.size(); i++) {
            when(converter.convertToDto(list.get(i)))
                    .thenReturn(listDTO.get(i));
        }

        this.mockMvc.perform(get("/user-programs/users/" + user.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_Active_UserProgram_By_User_Id_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<UserProgram> activeProgramList = new ArrayList<>();
        List<UserProgramDTO> activeProgramListDTO = new ArrayList<>();

        for (UserProgram userProgram : list) {
            if (!userProgram.isProgramOver()) {
                activeProgramList.add(userProgram);
            }
        }

        for (UserProgramDTO userProgramDTO : listDTO) {
            if (!userProgramDTO.isProgramOver()) {
                activeProgramListDTO.add(userProgramDTO);
            }
        }

        String jsonInString = mapper.writeValueAsString(activeProgramListDTO.get(index.intValue()));

        when(service.getByParameter("user.id", user.getId().toString()))
                .thenReturn(activeProgramList);

        for (int i = 0; i < activeProgramList.size(); i++) {
            when(converter.convertToDto(activeProgramList.get(i)))
                    .thenReturn(activeProgramListDTO.get(i));
        }

        this.mockMvc.perform(get("/user-programs/active/users/" + user.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_Active_UserProgram_By_UserId_Return_Empty_Test() throws Exception {

        when(service.getByParameter("user.id", user.getId().toString()))
                .thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/user-programs/active/users/" + user.getId()))
                .andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void add_UserProgram_By_User_ID_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        UserProgramDTO userProgramDTO = mapper.readValue(jsonInString, UserProgramDTO.class);

        when(userService.getUserById(user.getId())).thenReturn(user);

        when(converter.convertToEntity(userProgramDTO))
                .thenReturn(list.get(index.intValue()));

        when(service.save(list.get(index.intValue())))
                .thenReturn(true);

        this.mockMvc.perform(post("/user-programs/users/" + user.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Added"));
    }

    @Test
    public void add_UserProgram_By_User_ID_Bad_Request_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        UserProgramDTO userProgramDTO = mapper.readValue(jsonInString, UserProgramDTO.class);

        when(userService.getUserById(user.getId())).thenReturn(user);

        when(converter.convertToEntity(userProgramDTO))
                .thenReturn(list.get(index.intValue()));

        when(service.save(list.get(index.intValue())))
                .thenReturn(false);

        this.mockMvc.perform(post("/user-programs/users/" + user.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }


    @Test
    public void add_UserProgram_By_User_ID_Not_Valid_DTO_Test() throws Exception {
        UserProgramDTO userProgramDTO = listDTO.get(index.intValue());
        userProgramDTO.setProgramName("");

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userProgramDTO);

        when(userService.getUserById(user.getId())).thenReturn(user);

        this.mockMvc.perform(post("/user-programs/users/" + user.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void rename_UserProgram_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        UserProgramDTO userProgramDTO = mapper.readValue(jsonInString, UserProgramDTO.class);

        when(converter.convertToEntity(userProgramDTO))
                .thenReturn(list.get(index.intValue()));

        when(service.update(index, list.get(index.intValue())))
                .thenReturn(true);

        this.mockMvc.perform(put("/user-programs/" + index)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Renamed"));
    }


    @Test
    public void rename_UserProgram_Bad_Request_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        when(converter.convertToEntity(listDTO.get(index.intValue())))
                .thenReturn(list.get(index.intValue()));

        when(service.update(index, list.get(index.intValue())))
                .thenReturn(false);

        this.mockMvc.perform(put("/user-programs/" + index)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }


    @Test
    public void rename_UserProgram_Not_Valid_DTO_Test() throws Exception {
        UserProgramDTO userProgramDTO = listDTO.get(index.intValue());
        userProgramDTO.setProgramName("");
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userProgramDTO);

        this.mockMvc.perform(put("/user-programs/" + index)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void delete_UserProgram_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        doNothing().when(service).deleteById(index);

        this.mockMvc.perform(delete("/user-programs/" + index))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
