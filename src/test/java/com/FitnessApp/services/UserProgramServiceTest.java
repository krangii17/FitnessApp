package com.FitnessApp.services;

import com.FitnessApp.dao.IUserProgramDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserProgram;
import com.FitnessApp.services.impl.UserProgramService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserProgramServiceTest {

    @Mock
    IUserProgramDAO dao;

    @InjectMocks
    UserProgramService service;

    private List<UserProgram> list;
    private User user;

    private Long index;

    private UserProgram setUserProgramID(Long id, @NotNull UserProgram userProgram) {
        userProgram.setId(id);
        return userProgram;
    }

    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);

        index = 0L;

        user = new User();
        user.setId(1L);
        user.setEmail("qwerty@gmail.com");

        list = new ArrayList<>();
        list.add(setUserProgramID(1L,
                new UserProgram(user, "Base_1",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"), false)));
        list.add(setUserProgramID(2L,
                new UserProgram(user, "Base_2",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-15"), true)));
        list.add(setUserProgramID(3L,
                new UserProgram(user, "Base_3",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-15"), true)));

    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test
    public void getAll_Test() {
        List<UserProgram> expected = list;
        when(dao.getAll(UserProgram.class)).thenReturn(Optional.ofNullable(list));

        Optional<List<UserProgram>> actual = service.getAll();

        assertEquals(expected, actual.get());
    }

    @Test
    public void getByParameter_String_Test() throws ObjectNotFoundException {
        UserProgram expected = list.get(index.intValue());
        when(dao.getByParameter(UserProgram.class, "programName",
                list.get(index.intValue()).getProgramName()))
                .thenReturn(Optional.ofNullable(list));

        UserProgram actual = service.getByParameter("programName",
                list.get(index.intValue()).getProgramName()).get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void getByParameter_UserId_Test() throws ObjectNotFoundException {
        UserProgram expected = list.get(index.intValue());
        when(dao.getByUserId(user.getId()))
                .thenReturn(Optional.ofNullable(list));

        UserProgram actual = service.getByParameter("user.id",
                list.get(index.intValue()).getUser().getId().toString()).get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void getByParameter_UserEmail_Test() throws ObjectNotFoundException {
        UserProgram expected = list.get(index.intValue());
        when(dao.getByUserLogin(user.getEmail()))
                .thenReturn(Optional.ofNullable(list));

        UserProgram actual = service.getByParameter("user.email",
                list.get(index.intValue()).getUser().getEmail()).get(0);

        assertEquals(expected, actual);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getByParameter_Not_Found_Test() {

        when(dao.getByParameter(UserProgram.class, "programName",
                list.get(index.intValue()).getProgramName()))
                .thenReturn(Optional.ofNullable(null));

        UserProgram actual = service.getByParameter("programName",
                list.get(index.intValue()).getProgramName()).get(0);
    }

    @Test
    public void getByID_Test() {
        UserProgram expected = list.get(index.intValue());
        when(dao.getByID(UserProgram.class, list.get(index.intValue()).getId()))
                .thenReturn(Optional.ofNullable(list.get(index.intValue())));

        UserProgram actual = service.getByID(list.get(index.intValue()).getId());

        assertEquals(expected, actual);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getByID_Not_Found_Test() {

        when(dao.getByID(UserProgram.class, list.get(index.intValue()).getId()))
                .thenReturn(Optional.ofNullable(null));

        service.getByID(list.get(index.intValue()).getId());
    }


    @Test
    public void save_Test() {

        UserProgram savedObj = list.get(index.intValue());

        doNothing().when(dao).save(savedObj);

        boolean actual = service.save(savedObj);
        assertTrue(actual);

        savedObj.setProgramName("");
        actual = service.save(savedObj);
        assertFalse(actual);
    }

    @Test
    public void update_Test() {

        UserProgram updatedObj = list.get(index.intValue());

        when(dao.getByID(UserProgram.class, index))
                .thenReturn(Optional.ofNullable(updatedObj));

        when(dao.update(updatedObj)).thenReturn(updatedObj);

        boolean actual = service.update(index, updatedObj);
        assertTrue(actual);

        updatedObj.setProgramName("");
        actual = service.update(index, updatedObj);
        assertFalse(actual);

    }

    @Test(expected = ObjectNotFoundException.class)
    public void update_Not_Found_Test() {

        when(dao.getByID(UserProgram.class, index))
                .thenReturn(Optional.ofNullable(null));

        service.update(index, list.get(index.intValue()));
    }


    @Test
    public void delete_Test() {

        doNothing().when(dao).delete(list.get(index.intValue()));

        service.delete(list.get(index.intValue()));

    }


    @Test(expected = ObjectNotFoundException.class)
    public void delete_Not_Found_Test() {
        UserProgram userProgram = new UserProgram();
        userProgram.setProgramName("Another");

        service.delete(userProgram);
    }

    @Test
    public void deleteById_Test() {

        when(dao.getByID(UserProgram.class, index))
                .thenReturn(Optional.ofNullable(list.get(index.intValue())));

        doNothing().when(dao).deleteById(UserProgram.class, index);

        service.deleteById(index);

    }

    @Test(expected = ObjectNotFoundException.class)
    public void deleteById_Not_Found_Test() {

        when(dao.getByID(UserProgram.class, index))
                .thenReturn(Optional.ofNullable(null));

        service.deleteById(index);

    }
}
