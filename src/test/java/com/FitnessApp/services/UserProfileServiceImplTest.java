package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.Gender;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.services.impl.UserProfileServiceImpl;
import com.FitnessApp.utils.converters.UserProfileConverter;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserProfileServiceImplTest {

    @Mock
    private GenericDAO dao;

    @Mock
    private UserProfileConverter converter;

    @InjectMocks
    private UserProfileServiceImpl service;


    private User user;
    private UserParameters userParameters;
    private UserProfileDTO userParametersDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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
    public void get_UserParameters_By_Id_Test() {
        when(dao.getByID(UserParameters.class, userParameters.getID()))
                .thenReturn(Optional.ofNullable(userParameters));

        assertEquals(userParameters,
                service.getUserParametersById(userParameters.getID()).get());
    }

    @Test
    public void get_ProfileData_Test() {
        when(converter.convertToProfileDTO(user))
                .thenReturn(userParametersDTO);

        assertEquals(userParametersDTO, service.getProfileData(user));
    }

    @Test
    public void change_ProfileData_Test() {
        when(converter.convertToUserParameters(userParametersDTO))
                .thenReturn(userParameters);

        user.setUserParameters(null);
        when(converter.convertToUser(userParametersDTO))
                .thenReturn(user);

        when(dao.update(user)).thenReturn(user);
        doNothing().when(dao).save(userParameters);
        when(dao.update(userParameters)).thenReturn(userParameters);

        assertTrue(service.changeProfileData(userParametersDTO, user));
    }

    @Test
    public void delete_ById_Test() {
        when(dao.getByID(UserParameters.class, userParameters.getID()))
                .thenReturn(Optional.ofNullable(userParameters));

        doNothing().when(dao).deleteById(UserParameters.class, userParameters.getID());

        service.deleteById(userParameters.getID());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void delete_ById_Not_Found_ID_Test() {
        when(dao.getByID(UserParameters.class, userParameters.getID()))
                .thenReturn(Optional.ofNullable(null));

        service.deleteById(userParameters.getID());
    }


}
