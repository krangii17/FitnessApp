package com.FitnessApp.utils.converters;

import com.FitnessApp.exceptions.EmailNotFoundException;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.dtos.UserRecoveryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRecoveryConverter {

    @Autowired
    UserService service;

    public UserRecoveryDTO getRecoveryEmail(String email){
        UserRecoveryDTO dto = new UserRecoveryDTO();
        try {
            dto.setSecretQuestion(service.getUserByEmail(email).getSecretQuestion());
            return dto;
        } catch (EmailNotFoundException e) {
            return dto;
        }
    }

    public boolean changePasswordBySecretAnswer(String email, String answer, String password){
        if(service.isSecretAnswerRight(email, answer)) {
            service.changePassword(email, password);
            return true;
        }
        return false;
    }
}
