package com.FitnessApp.services.impl;

import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.User;
import com.FitnessApp.services.*;
import com.FitnessApp.utils.converters.ProgramTemplateConverter;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ChooseProgramServiceImpl implements ChooseProgramService {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgramTemplateService programTemplateService;
    @Autowired
    private GoalService goalService;
    @Autowired
    ProgramTemplateConverter programTemplateConverter;

    public ChooseProgramServiceImpl() {
    }

    @Override
    public ProgramTemplateDto findProgramsTemplate(Long goalId, Long userId){

        User userById = userService.getUserById(userId);
        UserProfileDTO profileData = userProfileService.getProfileData(userById);
        Integer trainDayQuantity = profileData.getTrainDayQuantity();

        String goalTitle = goalService.getGoalById(goalId).getGoalTitle();
        List<ProgramTemplate> programList = programTemplateService.getAll().get();
        for (ProgramTemplate program : programList) {
            if ((program.getNumberOfDays() != trainDayQuantity) &&
                    !program.getGoal().getGoalTitle().equals(goalTitle)){
                programList.remove(program);
            }
        }
        return programTemplateConverter.convertToDTO(programList);
    }
}
