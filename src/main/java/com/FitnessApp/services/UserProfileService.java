package com.FitnessApp.services;

import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserProfileService {

    Optional<UserParameters> getUserParametersById(Long id);

    UserProfileDTO getProfileData(User user);

    boolean changeProfileData(UserProfileDTO dto, User user);

    void deleteById(Long id);
}
