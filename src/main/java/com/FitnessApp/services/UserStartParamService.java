package com.FitnessApp.services;

import com.FitnessApp.model.UserStartParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserStartParamService {
    boolean saveUserStartParam(UserStartParam startParam);

    Optional<List<UserStartParam>> getAllStartParameters();

    boolean deleteUserStartParam(UserStartParam startParam);

    boolean updateStartParameter(Long id, UserStartParam startParam);

    boolean deleteUserStartParamById(Long id);

    UserStartParam getStartParamById(Long id);


}
