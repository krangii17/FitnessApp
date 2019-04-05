package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.EmailNotFoundException;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.exceptions.UserNotFoundExeption;
import com.FitnessApp.model.Role;
import com.FitnessApp.model.State;
import com.FitnessApp.model.User;
import com.FitnessApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserServiceImpl implements UserService {
    /**
     * Name of User field.
     * Unique.
     * Required for registration
     */
    private static final String EMAIL = "email";

    @Qualifier("genericDAOImpl")
    @Autowired
    private GenericDAO dao;

    public UserServiceImpl() {
    }

    public UserServiceImpl(GenericDAO dao) {
        this.dao = dao;
    }

    /**
     * Calls DAO for getting User
     *
     * @param email unique field of User
     * @return User if exist. EmailNotFoundException if doesn't exist
     */
    @Override
    public User getUserByEmail(String email) throws EmailNotFoundException {
        List<User> list = dao.getByParameter(User.class, EMAIL, email).orElse(new ArrayList<>());
        if (list.size() > 0) {
            return list.get(0);
        }
        throw new EmailNotFoundException(email);
    }

    @Override
    public User getUserById(Long id) throws ObjectNotFoundException {
        Optional<User> user = dao.getByID(User.class, id);
        if (!user.isPresent()) {
            throw new ObjectNotFoundException(id.intValue());
        }

        return user.get();
    }


    @Override
    public boolean createUser(User user) {
        if (user.getId() != null) {
            user.setId(null);
        }
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
            user.setState(State.ACTIVE);
        }
        if (!isPresentByEmail(user.getEmail())) {
            dao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String email, String password) {
        try {
            User user = getUserByEmail(email);
            user.setPassword(password);
            dao.update(user);
            return true;
        } catch (EmailNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean isSecretAnswerRight(String email, String answer) {
        try {
            User user = getUserByEmail(email);
            return user.getSecretAnswer().equals(answer);
        } catch (EmailNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, User candidate) {
        User exist = getUserById(id);
        if (!candidate.getEmail().equals(exist.getEmail()) && isPresentByEmail(candidate.getEmail())) {
            return false;
        }
        User user = prepareToUpdate(candidate, exist);
        dao.update(user);
        return true;
    }

    @Override
    public boolean delete(Long id, User user) {
        if (isPresentById(id)) {
            User candidate = getUserById(id);
            dao.delete(candidate);
            return true;
        }
        throw new UserNotFoundExeption(user.getId().toString());
    }

    @Override
    public List<User> getAllUser(int firsRes, int maxRes) {
        return dao.getAllPagination(User.class, firsRes, maxRes).get();
    }

    /**
     * Checks user is exist by email
     *
     * @param email email of user field
     * @return is present condition
     */
    private boolean isPresentByEmail(String email) {
        try {
            getUserByEmail(email);
            return true;
        } catch (EmailNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks user is exist by id
     *
     * @param id User id
     * @return is present conditions
     */
    private boolean isPresentById(Long id) {
        try {
            getUserById(id);
            return true;
        } catch (ObjectNotFoundException e) {
            return false;
        }
    }

    private User prepareToUpdate(User candidate, User exist) {
        if (candidate.getSecretQuestion() == null) {
            candidate.setSecretQuestion(exist.getSecretQuestion());
        }
        if (candidate.getSecretAnswer() == null) {
            candidate.setSecretAnswer(exist.getSecretAnswer());
        }
        if (candidate.getPassword() == null) {
            candidate.setPassword(exist.getPassword());
        }
        return candidate;
    }
}
