package com.FitnessApp.services.impl;

import com.FitnessApp.model.User;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.converters.UserConverter;
import com.FitnessApp.utils.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter converter;

    public SignUpService() {
    }

    /**
     * Calls the UserService
     *
     * @return condition of creating user
     */
    public boolean creteUser(UserDto userDto) {
        User user = converter.asUser(userDto);
        return userService.createUser(user);
    }
}
