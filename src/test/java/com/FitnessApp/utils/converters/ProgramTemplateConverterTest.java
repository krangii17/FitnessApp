package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Goal;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.User;
import com.FitnessApp.utils.dtos.GoalDto;
import com.FitnessApp.utils.dtos.programTemplate.CoachDTO;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateTableDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProgramTemplateConverterTest {

    private List<ProgramTemplate> programTemplates;
    private ProgramTemplateDto programTemplateDto;
    private ProgramTemplateConverter converter = new ProgramTemplateConverter();

    @Before
    public void setUp() {
        User user = new User();
        user.setId(9L);
        user.setUsername("user");

        Exercise exercise = new Exercise("Name", null, null);
        exercise.setExID(4L);
        programTemplates = new ArrayList<>();
        programTemplates.add(new ProgramTemplate(1L, 1,
                exercise, 90, 5, 10, "PrName",
                new Goal(2L, "Goal"),
                7, true, user));

        exercise = new Exercise("Name2", null, null);
        exercise.setExID(5L);

        programTemplates.add(new ProgramTemplate(2L, 2,
                exercise, 91, 6, 11, "PrName",
                new Goal(2L, "Goal"),
                7, true, user));

        programTemplateDto = new ProgramTemplateDto("PrName", new GoalDto(2L, "Goal"), 7,
                true, new CoachDTO(9L, "user"));

        programTemplateDto.addToList(new ProgramTemplateTableDTO(1L, 1, 4L,
                "Name", 90, 5, 10));

        programTemplateDto.addToList(new ProgramTemplateTableDTO(2L, 2, 5L,
                "Name2", 91, 6, 11));

    }

    @After
    public void tearDown() {
        programTemplateDto.getTableDTOList().clear();
        programTemplates.clear();
    }

    @Test
    public void convert_To_Entity_Test() {
        ProgramTemplateDto actual = converter.convertToDTO(programTemplates);
        assertEquals(programTemplateDto, actual);
    }

    @Test
    public void convert_To_DTO_Test() {
        List<ProgramTemplate> actual = converter.convertToEntity(programTemplateDto);
        assertEquals(programTemplates.size(), actual.size());
    }
}
