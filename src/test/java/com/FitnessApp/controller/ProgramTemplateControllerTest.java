package com.FitnessApp.controller;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Goal;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.User;
import com.FitnessApp.security.UserDetailsImpl;
import com.FitnessApp.services.ExerciseService;
import com.FitnessApp.services.ProgramTemplateService;
import com.FitnessApp.services.impl.GoalServiceImpl;
import com.FitnessApp.services.impl.UserServiceImpl;
import com.FitnessApp.utils.converters.ProgramTemplateConverter;
import com.FitnessApp.utils.dtos.GoalDto;
import com.FitnessApp.utils.dtos.programTemplate.CoachDTO;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateTableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProgramTemplateControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    private final Long index = 0L;
    @InjectMocks
    private ProgramTemplateController controller;
    @Mock
    private ProgramTemplateConverter converter;
    @Mock
    private ProgramTemplateService service;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private GoalServiceImpl goalService;
    @Mock
    private ExerciseService exerciseService;

    private MockMvc mockMvc;
    private List<ProgramTemplate> list;
    private List<ProgramTemplateDto> listDTO;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        ProgramTemplateDto programTemplateDto;

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        list = new ArrayList<>();
        listDTO = new ArrayList<>();
        mapper = new ObjectMapper();

        User user = new User();
        user.setId(9L);
        user.setUsername("user");

        Exercise exercise = new Exercise("Name", null, null);
        exercise.setExID(4L);

        list.add(new ProgramTemplate(1L, 1,
                exercise, 90, 5, 10, "PrName",
                new Goal(2L, "Goal"),
                7, true, user));

        exercise = new Exercise("Name2", null, null);
        exercise.setExID(5L);

        list.add(new ProgramTemplate(2L, 2,
                exercise, 91, 6, 11, "PrName",
                new Goal(2L, "Goal"),
                7, true, user));

        programTemplateDto = new ProgramTemplateDto("PrName", new GoalDto(2L, "Goal"), 7,
                true, new CoachDTO(9L, "user"));

        programTemplateDto.addToList(new ProgramTemplateTableDTO(1L, 1, 4L,
                "Name", 90, 5, 10));

        programTemplateDto.addToList(new ProgramTemplateTableDTO(2L, 2, 5L,
                "Name2", 91, 6, 11));

        listDTO.add(programTemplateDto);
    }

    @After
    public void tearDown() {
        listDTO.get(0).getTableDTOList().clear();
        listDTO.clear();
        list.clear();
    }

    @Test
    public void get_ProgramTemplate_List_Test() throws Exception {

        String jsonInString = mapper.writeValueAsString(listDTO);

        when(service.getAll()).thenReturn(Optional.ofNullable(list));
        when(converter.convertToDTO(list)).thenReturn(listDTO.get(index.intValue()));

        this.mockMvc.perform(get("/program-templates"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_ProgramTemplate_By_Id_Test() throws Exception {
        list.remove(0);
        listDTO.get(index.intValue()).removeFromList(0);

        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        when(service.getById(list.get(index.intValue()).getId()))
                .thenReturn(list.get(index.intValue()));

        when(converter.convertToDTO(list))
                .thenReturn(listDTO.get(index.intValue()));

        this.mockMvc.perform(get("/program-templates/"
                + list.get(index.intValue()).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_ProgramTemplate_By_Name_Test() throws Exception {
        String name = "PrName";
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        when(service.getByName(name)).thenReturn(list);
        when(converter.convertToDTO(list)).thenReturn(listDTO.get(index.intValue()));

        this.mockMvc.perform(get("/program-templates/name"
                + "?programName=PrName"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));

    }


    @Test
    public void update_ProgramTemplate_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        ProgramTemplateDto dto = mapper.readValue(jsonInString, ProgramTemplateDto.class);

        when(converter.convertToEntity(dto))
                .thenReturn(list);

        when(service.updateProgramTemplate(anyObject(), anyObject()))
                .thenReturn(true);

        when(exerciseService.getExerciseById(anyObject()))
                .thenReturn(list.get(index.intValue()).getExercise());

        when(goalService.getGoalById(anyObject()))
                .thenReturn(list.get(index.intValue()).getGoal());
        when(userService.getUserById(anyObject()))
                .thenReturn(list.get(index.intValue()).getUser());

        this.mockMvc.perform(put("/program-templates/"
                + listDTO.get(index.intValue()).getTableDTOList().get(0).getIdProgramTemplate())
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Updated"));
    }

    @Test
    public void delete_ProgramTemplate_By_Id_Test() throws Exception {
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        when(service.deleteProgramTemplateById(index)).thenReturn(true);

        this.mockMvc.perform(delete("/program-templates/" + index))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }

    @Test
    public void delete_ProgramTemplate_By_Name_Test() throws Exception {
        String name = "PrName";
        String jsonInString = mapper.writeValueAsString(listDTO.get(index.intValue()));

        doNothing().when(service).deleteProgramTemplateByName(name);

        this.mockMvc.perform(delete("/program-templates?programName=PrName"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }

    @Test
    public void add_ProgramTemplate_Test() {
        User user = new User();
        user.setId(1L);
        user.setUsername("qwerty");
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "cred");

        String expected = "Added";

        when(converter.convertToEntity(listDTO.get(index.intValue())))
                .thenReturn(list);

        when(service.addProgramTemplate(anyObject()))
                .thenReturn(true);

        when(exerciseService.getExerciseById(anyObject()))
                .thenReturn(list.get(index.intValue()).getExercise());

        when(goalService.getGoalById(anyObject()))
                .thenReturn(list.get(index.intValue()).getGoal());
        when(userService.getUserById(anyObject()))
                .thenReturn(list.get(index.intValue()).getUser());


        String actual = controller.addProgramTemplate(authentication,
                listDTO.get(index.intValue())).getBody().toString();

        assertEquals(expected, actual);
    }
}
