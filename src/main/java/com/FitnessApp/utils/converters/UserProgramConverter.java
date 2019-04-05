package com.FitnessApp.utils.converters;

import com.FitnessApp.model.UserProgram;
import com.FitnessApp.utils.dtos.UserProgramDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProgramConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserProgramConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserProgramDTO convertToDto(UserProgram userProgram) {
        return modelMapper.map(userProgram, UserProgramDTO.class);
    }

    public UserProgram convertToEntity(UserProgramDTO userProgramDTO) {

        return modelMapper.map(userProgramDTO, UserProgram.class);
    }

}
