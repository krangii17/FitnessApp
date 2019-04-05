package com.FitnessApp.utils.validators;

import com.FitnessApp.model.User;
import com.FitnessApp.utils.dtos.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserCandidateValidator {
    public boolean isCandidate(UserDto userDto) {
        return (userDto.getEmail() != null
                && userDto.getUsername() != null
                && userDto.getPassword() != null
                && userDto.getSecretQuestion() != null
                && userDto.getSecretAnswer() != null)
                &&
                (!userDto.getEmail().isEmpty()
                        && !userDto.getEmail().isEmpty()
                        && !userDto.getPassword().isEmpty()
                        && !userDto.getSecretQuestion().isEmpty()
                        && !userDto.getSecretAnswer().isEmpty());
    }

//    public User updateFields(User candidate, User exist) {
//        candidate.getId();
//        if (candidate.getEmail() == null) {
//            candidate.setEmail(exist.getEmail());
//        }
//        if (candidate.getUsername())
//        candidate.getUsername();
//        candidate.getPassword();
//        candidate.getSecretQuestion();
//        candidate.getSecretAnswer();
//        candidate.getRole();
//        candidate.getState();
//        candidate.getUserParameters();
//    }
}
