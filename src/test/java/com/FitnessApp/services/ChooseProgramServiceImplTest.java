package com.FitnessApp.services;

import com.FitnessApp.model.Goal;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.User;
import com.FitnessApp.services.impl.ChooseProgramServiceImpl;
import com.FitnessApp.services.impl.UserServiceImpl;
import com.FitnessApp.utils.converters.ProgramTemplateConverter;
import com.FitnessApp.utils.dtos.GoalDto;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import com.FitnessApp.utils.dtos.programTemplate.CoachDTO;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateTableDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class ChooseProgramServiceImplTest {

    @Mock
    private UserProfileService userProfileService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private ProgramTemplateService programTemplateService;
    @Mock
    private GoalService goalService;
    @Mock
    private ProgramTemplateConverter programTemplateConverter;

    @InjectMocks
    private ChooseProgramServiceImpl service;

    private ProgramTemplateDto expectedProgramTemplateDto;
    private List<ProgramTemplate> programTemplates;

    private ProgramTemplate programTemplate1;
    private ProgramTemplate programTemplate2;
    private ProgramTemplate programTemplate3;
    private User user;
    private UserProfileDTO userProfileDTO;
    private Goal goal;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(1L);

        userProfileDTO = new UserProfileDTO();
        userProfileDTO.setTrainDayQuantity(3);
        goal = new Goal();
        goal.setGoalTitle("силовые");

        programTemplate1 = new ProgramTemplate();
        programTemplate1.setNumberOfDays(3);
        programTemplate1.setGoal(goal);
        programTemplate1.setProgramName("program 1");

        programTemplate2 = new ProgramTemplate();
        programTemplate2.setNumberOfDays(3);
        programTemplate2.setGoal(goal);
        programTemplate2.setProgramName("program 2");

        programTemplate3 = new ProgramTemplate();
        programTemplate3.setNumberOfDays(2);
        programTemplate3.setGoal(goal);
        programTemplate3.setProgramName("program 3");

        programTemplates = new ArrayList<>();
        programTemplates.add(programTemplate1);
        programTemplates.add(programTemplate2);
        programTemplates.add(programTemplate3);

        expectedProgramTemplateDto = new ProgramTemplateDto("PrName", new GoalDto(2L, "Goal"), 7,
                true, new CoachDTO(9L, "user"));

        expectedProgramTemplateDto.addToList(new ProgramTemplateTableDTO(1L, 1, 4L,
                "Name", 90, 5, 10));

        expectedProgramTemplateDto.addToList(new ProgramTemplateTableDTO(2L, 2, 5L,
                "Name2", 91, 6, 11));
    }

    @Test
    public void findProgramsTemplatePositive() {
        //GIVEN
        List<ProgramTemplate> expectedProgramTemplates = new ArrayList<>();
        expectedProgramTemplates.add(programTemplate1);
        expectedProgramTemplates.add(programTemplate2);
        expectedProgramTemplates.add(programTemplate3);
        Long userId = 1L;
        Long goalId = 3L;
        //WHEN
        when(programTemplateConverter.convertToDTO(expectedProgramTemplates)).thenReturn(expectedProgramTemplateDto);
        when(programTemplateService.getAll()).thenReturn(Optional.ofNullable(programTemplates));
        when(goalService.getGoalById(goalId)).thenReturn(goal);
        when(userProfileService.getProfileData(anyObject())).thenReturn(userProfileDTO);
        when(userService.getUserById(userId)).thenReturn(user);
        //THEN
        assertEquals(expectedProgramTemplateDto, service.findProgramsTemplate(goalId, userId));
    }
}
