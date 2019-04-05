package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.UserStartParam;
import com.FitnessApp.utils.dtos.UserStartParamDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserStartParamConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserStartParamConverter() {
        this.modelMapper = new ModelMapper();
    }

    public UserStartParamDTO convertToDto(UserStartParam userStartParam) {

        return modelMapper.map(userStartParam, UserStartParamDTO.class);
    }

    public UserStartParam convertToEntity(UserStartParamDTO userStartParamDTO) {
        UserStartParam userStartParam = modelMapper.map(userStartParamDTO, UserStartParam.class);
        Exercise exercise = new Exercise();
        exercise.setExID(userStartParamDTO.getExerciseID());
        userStartParam.setExercise(exercise);

        ProgramTemplate programTemplate = new ProgramTemplate();
        programTemplate.setId(userStartParamDTO.getProgramTemplateID());
        userStartParam.setProgramTemplate(programTemplate);

        return userStartParam;
    }

    public List<UserStartParamDTO> convertToUserParamListDTO(Optional<List<UserStartParam>> startParamList) {
        return startParamList.get().
                stream()
                .map(UserStartParam -> convertToDto(UserStartParam))
                .collect(Collectors.toList());
    }
}
