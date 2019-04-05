package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Goal;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.User;
import com.FitnessApp.utils.dtos.GoalDto;
import com.FitnessApp.utils.dtos.programTemplate.CoachDTO;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateTableDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProgramTemplateConverter {

    private Goal convertToGoal(GoalDto goalDTO) {
        return new Goal(goalDTO.getId(), goalDTO.getGoalTitle());
    }

    private GoalDto convertToGoalDTO(Goal goal) {
        return new GoalDto(goal.getId(), goal.getGoalTitle());
    }

    private Exercise getExercise(ProgramTemplateTableDTO tableDTO) {
        Exercise exercise = new Exercise();
        exercise.setExID(tableDTO.getExerciseId());
        exercise.setExerciseName(tableDTO.getExerciseName());
        return exercise;
    }

    private User convertToUser(CoachDTO coachDTO) {
        User user = new User();
        user.setId(coachDTO.getCoachId());
        user.setUsername(coachDTO.getCoachName());
        return user;
    }

    private CoachDTO convertToCoachDTO(User user) {
        return new CoachDTO(user.getId(), user.getUsername());
    }

    private ProgramTemplateTableDTO convertToTableDTO(ProgramTemplate programTemplate) {
        return new ProgramTemplateTableDTO(programTemplate.getId(), programTemplate.getNumOfTrain(),
                programTemplate.getExercise().getExID(), programTemplate.getExercise().getExerciseName(),
                programTemplate.getPercentOfMaximum(), programTemplate.getSets(), programTemplate.getReps());
    }


    public List<ProgramTemplate> convertToEntity(ProgramTemplateDto dto) {
        List<ProgramTemplate> programTemplates = new ArrayList<>();

        for (ProgramTemplateTableDTO tableDTO : dto.getTableDTOList()) {
            ProgramTemplate programTemplate = new ProgramTemplate(tableDTO.getIdProgramTemplate(),
                    tableDTO.getNumOfTrain(), getExercise(tableDTO), tableDTO.getPercentOfMaximum(),
                    tableDTO.getSets(), tableDTO.getReps(),
                    dto.getProgramName(), convertToGoal(dto.getGoalDTO()), dto.getNumberOfDays(),
                    dto.getBaseProgram(), convertToUser(dto.getCoachDTO()));

            programTemplates.add(programTemplate);
        }

        return programTemplates;
    }


    public ProgramTemplateDto convertToDTO(List<ProgramTemplate> programTemplates) {
        ProgramTemplateDto programTemplateDto = null;

        if (programTemplates.size() > 0) {
            ProgramTemplate pt = programTemplates.get(0);
            programTemplateDto = new ProgramTemplateDto(pt.getProgramName(),
                    convertToGoalDTO(pt.getGoal()), pt.getNumberOfDays(), pt.getBaseProgram(),
                    convertToCoachDTO(pt.getUser()));
            for (ProgramTemplate programTemplate : programTemplates) {
                programTemplateDto.addToList(convertToTableDTO(programTemplate));
            }
        }
        return Optional.ofNullable(programTemplateDto).get();
    }
}
