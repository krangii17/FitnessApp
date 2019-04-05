package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.EmailNotFoundException;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.exceptions.UserNotFoundExeption;
import com.FitnessApp.model.Role;
import com.FitnessApp.model.State;
import com.FitnessApp.model.User;
import com.FitnessApp.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    private UserService userService;
    private GenericDAO dao = mock(GenericDAO.class);
    private User user;
    private Long id = 1L;
    private String email = "vasya@mail.com";
    private String secretAnswer = "answer";
    private String password = "password";
    private String parameterName = "email";
    private List<User> users;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(dao);

        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setId(id);
        user.setSecretAnswer(secretAnswer);
        user.setUsername("vasya");
        user.setRole(Role.ROLE_USER);
        user.setState(State.ACTIVE);
        users = new ArrayList<>();
        users.add(user);
    }

    @Test
    public void getUserByEmail() throws EmailNotFoundException {
        when(dao.getByParameter(User.class, parameterName, email))
                .thenReturn(java.util.Optional.ofNullable(users));

        User userFromDao = userService.getUserByEmail(email);

        assertEquals(user, userFromDao);
    }

    @Test(expected = EmailNotFoundException.class)
    public void getUserByEmailNotFound() throws EmailNotFoundException {
        when(dao.getByParameter(User.class, parameterName, email))
                .thenReturn(java.util.Optional.of(Collections.emptyList()));

        User userFromDao = userService.getUserByEmail(email);

        assertEquals(userFromDao, null);
    }

    @Test(expected = EmailNotFoundException.class)
    public void getUserByEmailNotExpected() throws EmailNotFoundException {
        when(dao.getByParameter(User.class, parameterName, email))
                .thenReturn(java.util.Optional.of(Collections.emptyList()));

        User userFromDao = userService.getUserByEmail(email);

        assertNotEquals(userFromDao, user);
    }

    @Test
    public void getUserByIdIsExist() {
        when(dao.getByID(User.class, user.getId())).thenReturn(Optional.of(user));

        assertEquals(userService.getUserById(user.getId()), user);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getUserByIdIsNotExist() {
        when(dao.getByID(User.class, user.getId())).thenReturn(Optional.empty());

        assertNotEquals(userService.getUserById(user.getId()), user);
    }

    @Test
    public void creteUserIsNotExist() {
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(new ArrayList<>()));

        assertTrue(userService.createUser(user));
    }

    @Test
    public void creteUserIsExist() {
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(users));

        assertFalse(userService.createUser(user));
    }

    @Test
    public void changePasswordIsExist() {
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(users));

        assertTrue(userService.changePassword(email, password));
    }

    @Test
    public void changePasswordIsNotExist() {
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(new ArrayList<>()));

        assertFalse(userService.changePassword(email, password));
    }

    @Test
    public void isSecretAnswerRightTrue() {
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(users));

        assertTrue(userService.isSecretAnswerRight(email, secretAnswer));
    }

    @Test
    public void isSecretAnswerRightFalse() {
        user = users.get(0);
        user.setSecretAnswer("new");
        users.add(0, user);
        when(dao.getByParameter(User.class, parameterName, user.getEmail()))
                .thenReturn(Optional.of(users));

        assertFalse(userService.isSecretAnswerRight(email, secretAnswer));
    }

    @Test
    public void updateIsExist() {
        User candidate = new User();
        candidate.setEmail("new");
        when(dao.getByID(User.class, id)).thenReturn(Optional.of(user));
        when(dao.getByParameter(User.class, parameterName, candidate.getEmail())).thenReturn(Optional.empty());

        assertTrue(userService.update(id, candidate));
    }

    @Test
    public void updateIsNewEmailExist() {
        User candidate = new User();
        candidate.setEmail("new");
        when(dao.getByID(User.class, id)).thenReturn(Optional.of(user));
        when(dao.getByParameter(User.class, parameterName, candidate.getEmail())).thenReturn(Optional.of(users));

        assertFalse(userService.update(id, candidate));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void updateIsNotExist() {
        when(dao.getByID(User.class, id)).thenReturn(Optional.empty());
        user.setUsername("new name example");

        assertTrue(userService.update(id, user));
    }

    @Test
    public void deleteIsExist() {
        when(dao.getByID(User.class, id)).thenReturn(Optional.of(user));

        assertTrue(userService.delete(id, user));
    }

    @Test(expected = UserNotFoundExeption.class)
    public void deleteIsNotExist() {
        when(dao.getByID(User.class, id)).thenReturn(Optional.empty());

        assertTrue(userService.delete(id, user));
    }
}
