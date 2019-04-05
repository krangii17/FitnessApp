package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Gender;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserProfileConverter {

    private UserParameters getEmptyUserParameters() {
        UserParameters userParameters = new UserParameters();
        userParameters.setID(0L);
        userParameters.setFirstName("");
        userParameters.setLastName("");
        userParameters.setPhone("");
        userParameters.setBirthDate(LocalDate.now());
        userParameters.setGender(Gender.UNKNOWN);
        userParameters.setWeight(0);
        userParameters.setHeight(0);
        userParameters.setTrainDayQuantity(0);

        return userParameters;
    }


    public UserProfileDTO convertToProfileDTO(User user) {
        UserParameters userParameters;

        if (user.getUserParameters()!= null) {
            userParameters = user.getUserParameters();
        } else {
            userParameters = getEmptyUserParameters();
        }

        return new UserProfileDTO(
                userParameters.getID(),
                userParameters.getFirstName(),
                userParameters.getLastName(),
                user.getUsername(),
                user.getEmail(),
                userParameters.getPhone(),
                java.sql.Date.valueOf(userParameters.getBirthDate()),
                userParameters.getGender(),
                userParameters.getWeight(),
                userParameters.getHeight(),
                userParameters.getTrainDayQuantity()
        );
    }

    public User convertToUser(UserProfileDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        return user;
    }

    public UserParameters convertToUserParameters(UserProfileDTO dto) {
        UserParameters userParameters = new UserParameters();
        userParameters.setID(dto.getId());
        userParameters.setFirstName(dto.getFirstName());
        userParameters.setLastName(dto.getLastName());
        userParameters.setPhone(dto.getTelephone());
        userParameters.setBirthDate(
                new java.sql.Date(dto.getBirthDate().getTime()).toLocalDate());
        userParameters.setGender(dto.getGender());
        userParameters.setWeight(dto.getWeight());
        userParameters.setHeight(dto.getHeight());
        userParameters.setTrainDayQuantity(dto.getTrainDayQuantity());
        return userParameters;
    }

}
