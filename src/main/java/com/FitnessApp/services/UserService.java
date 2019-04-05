package com.FitnessApp.services;

import com.FitnessApp.exceptions.EmailNotFoundException;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    boolean createUser(User user);

    User getUserByEmail(String email) throws EmailNotFoundException;

    User getUserById(Long id) throws ObjectNotFoundException;

    boolean changePassword(String email, String password);

    boolean isSecretAnswerRight(String email, String answer);

    boolean update(Long id, User candidate);

    boolean delete(Long id, User candidate);

    List<User> getAllUser(int firsRes, int maxRes);
}
