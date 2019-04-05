package com.FitnessApp.services;

import com.FitnessApp.model.User;
import com.FitnessApp.services.impl.SignUpService;
import com.FitnessApp.utils.converters.UserConverter;
import com.FitnessApp.utils.dtos.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SignUpServiceTest {
    @InjectMocks
    private SignUpService service;
    @Mock
    private UserConverter converter;
    @Mock
    private UserService userService;
    private User user;
    private UserDto userDto;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User();
        userDto = new UserDto();
    }

    @Test
    public void creteUserIsNotExist() {
        when(converter.asUser(userDto)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(true);

        assertTrue(service.creteUser(userDto));
    }

    @Test
    public void creteUserIsExist() {
        when(converter.asUser(userDto)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(false);

        assertFalse(service.creteUser(userDto));
    }
}
