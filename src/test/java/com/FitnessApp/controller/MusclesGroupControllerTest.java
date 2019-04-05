package com.FitnessApp.controller;

import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.services.impl.MusclesGroupService;
import com.FitnessApp.utils.converters.MusclesGroupConverter;
import com.FitnessApp.utils.dtos.MusclesGroupDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MusclesGroupControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    private final Long index = 0L;
    @Mock
    MusclesGroupService service;
    @InjectMocks
    private MusclesGroupController controller;
    @Mock
    private MusclesGroupConverter converter;
    private MockMvc mockMvc;
    private List<MusclesGroup> musclesGroupList;
    private List<MusclesGroupDTO> musclesGroupDTOList;

    private MusclesGroup getMusclesGroupObject(Long id, String groupName) {
        MusclesGroup musclesGroup = new MusclesGroup();
        musclesGroup.setID(id);
        musclesGroup.setGroupName(groupName);
        return musclesGroup;
    }

    private MusclesGroupDTO getMusclesGroupDTOObject(Long id, String groupName) {
        MusclesGroupDTO musclesGroupDTO = new MusclesGroupDTO();
        musclesGroupDTO.setID(id);
        musclesGroupDTO.setGroupName(groupName);
        return musclesGroupDTO;
    }


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        musclesGroupList = new ArrayList<>();
        musclesGroupList.add(getMusclesGroupObject(1L, "arm"));
        musclesGroupList.add(getMusclesGroupObject(2L, "leg"));
        musclesGroupList.add(getMusclesGroupObject(3L, "back"));

        musclesGroupDTOList = new ArrayList<>();
        musclesGroupDTOList.add(getMusclesGroupDTOObject(1L, "arm"));
        musclesGroupDTOList.add(getMusclesGroupDTOObject(2L, "leg"));
        musclesGroupDTOList.add(getMusclesGroupDTOObject(3L, "back"));

    }

    @After
    public void tearDown() {
        musclesGroupList.clear();
    }

    @Test
    public void get_All_MusclesGroup_Test() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList);

        when(service.getAll()).thenReturn(Optional.ofNullable(musclesGroupList));
        for (int i = 0; i < musclesGroupDTOList.size(); i++) {
            when(converter.asMusclesGroupDTO(musclesGroupList.get(i))).thenReturn(musclesGroupDTOList.get(i));
        }

        this.mockMvc.perform(get("/muscles-groups"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void get_All_MusclesGroup_NULL_Test() throws Exception {

        when(service.getAll()).thenReturn(Optional.ofNullable(null));

        this.mockMvc.perform(get("/muscles-groups"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    @Test
    public void get_MusclesGroup_By_Id_Test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        when(service.getByID(index)).thenReturn(musclesGroupList.get(index.intValue()));
        when(converter.asMusclesGroupDTO(musclesGroupList.get(index.intValue())))
                .thenReturn(musclesGroupDTOList.get(index.intValue()));


        this.mockMvc.perform(get("/muscles-groups/" + index))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(jsonInString));
    }

    @Test
    public void add_MusclesGroup_Test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        when(converter.asMusclesGroup(musclesGroupDTOList.get(index.intValue())))
                .thenReturn(musclesGroupList.get(index.intValue()));

        when(service.save(musclesGroupList.get(index.intValue())))
                .thenReturn(true);

        this.mockMvc.perform(post("/muscles-groups")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Added"));

    }

    @Test
    public void add_MusclesGroup_Bad_Request_Test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        when(converter.asMusclesGroup(musclesGroupDTOList.get(index.intValue())))
                .thenReturn(musclesGroupList.get(index.intValue()));

        when(service.save(musclesGroupList.get(index.intValue())))
                .thenReturn(false);

        this.mockMvc.perform(post("/muscles-groups")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));

    }


    @Test
    public void rename_MusclesGroup_Test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        when(converter.asMusclesGroup(musclesGroupDTOList.get(index.intValue())))
                .thenReturn(musclesGroupList.get(index.intValue()));

        when(service.update(index, musclesGroupList.get(index.intValue())))
                .thenReturn(true);

        this.mockMvc.perform(put("/muscles-groups/" + index)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Renamed"));
    }

    @Test
    public void rename_MusclesGroup_Bad_Request_Test() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        when(converter.asMusclesGroup(musclesGroupDTOList.get(index.intValue())))
                .thenReturn(musclesGroupList.get(index.intValue()));

        when(service.update(index, musclesGroupList.get(index.intValue())))
                .thenReturn(false);

        this.mockMvc.perform(put("/muscles-groups/" + index)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void deleteMusclesGroup() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(musclesGroupDTOList.get(index.intValue()));

        doNothing().when(service).deleteById(index);

        this.mockMvc.perform(delete("/muscles-groups/" + index))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
