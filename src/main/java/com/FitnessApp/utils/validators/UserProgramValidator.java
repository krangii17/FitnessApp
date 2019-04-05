package com.FitnessApp.utils.validators;

import com.FitnessApp.model.UserProgram;
import com.FitnessApp.utils.dtos.UserProgramDTO;

public class UserProgramValidator {
    public static boolean validateDTO(UserProgramDTO userProgramDTO) {
        return (userProgramDTO.getProgramName() != null
                && userProgramDTO.getBeginDate() != null
                &&
                (!userProgramDTO.getProgramName().isEmpty()));
    }

    public static boolean validateEntity(UserProgram userProgram) {
        return (userProgram.getProgramName() != null
                && userProgram.getUser() != null
                && userProgram.getBeginDate() != null
                &&
                (!userProgram.getProgramName().isEmpty()
                        && !userProgram.getBeginDate().toString().isEmpty()));
    }
}
