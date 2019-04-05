package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Role;
import com.FitnessApp.model.User;
import com.FitnessApp.utils.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private static final String PASSWORD_MASK = "******";

    @Autowired
    private PasswordEncoder encoder;

    public User asUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        if (!userDto.getPassword().equals(PASSWORD_MASK)) {
            user.setPassword(encoder.encode(userDto.getPassword()));
        }
        user.setSecretQuestion(userDto.getSecretQuestion());
        user.setSecretAnswer(userDto.getSecretAnswer());
        if (userDto.getRole() != null) {
            user.setRole(Role.valueOf(userDto.getRole()));
        }
        user.setState(userDto.getState());
        return user;
    }

    public UserDto asUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(PASSWORD_MASK);
        dto.setRole(user.getRole().name());
        dto.setState(user.getState());
        return dto;
    }

    public List<User> asListUser(List<UserDto> userDtos) {
        return userDtos.stream().map(this::asUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> asListUserDto(List<User> users) {
        return users.stream().map(this::asUserDto)
                .collect(Collectors.toList());
    }
}
