package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Gender;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UserProfileConverterTest {

    private User user;
    private UserParameters userParameters;
    private UserProfileDTO userParametersDTO;
    private UserProfileConverter converter;

    @Before
    public void setUp() {
        converter = new UserProfileConverter();
        userParameters = new UserParameters();
        userParameters.setID(1L);
        userParameters.setFirstName("John");
        userParameters.setLastName("Smith");
        userParameters.setPhone("1234567");
        userParameters.setBirthDate(LocalDate.now());
        userParameters.setGender(Gender.MALE);
        userParameters.setWeight(90);
        userParameters.setHeight(190);
        userParameters.setTrainDayQuantity(5);

        user = new User();
        user.setUsername("Neo");
        user.setEmail("neo@gmail.com");
        user.setUserParameters(userParameters);

        userParametersDTO = new UserProfileDTO(
                1L, "John", "Smith", "Neo", "neo@gmail.com",
                "1234567", java.sql.Date.valueOf(LocalDate.now()),
                Gender.MALE, 90, 190, 5);
    }

    @Test
    public void convert_To_Profile_DTO_Test() {
        assertEquals(userParametersDTO, converter.convertToProfileDTO(user));
    }

    @Test
    public void convert_To_Profile_Empty_User_DTO() {
        User emptyUser = new User();
        emptyUser.setEmail("");
        emptyUser.setUsername("");
        UserProfileDTO emptyUserProfileDTO = new UserProfileDTO(
                0L, "", "", "", "", "", java.sql.Date.valueOf(LocalDate.now()),
                Gender.UNKNOWN, 0, 0, 0);

        assertEquals(emptyUserProfileDTO, converter.convertToProfileDTO(emptyUser));
    }


    @Test
    public void convertToUser() {
        user.setUserParameters(null);
        assertEquals(user, converter.convertToUser(userParametersDTO));
    }

    @Test
    public void convertToUserParameters() {
        assertEquals(userParameters, converter.convertToUserParameters(userParametersDTO));
    }
}
