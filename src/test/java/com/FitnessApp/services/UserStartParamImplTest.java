package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAOImpl;
import com.FitnessApp.exceptions.UserStartParamNotFoundException;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.model.UserStartParam;
import com.FitnessApp.services.impl.UserStartParamImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserStartParamImplTest {
    int firstElement;
    long idOfCheckingElement;
    private UserStartParam userStartParam;
    private Exercise exercise;
    private ProgramTemplate template;
    private List<UserStartParam> paramList;
    @Mock
    private UserStartParamService startParamService;
    @Mock
    private GenericDAOImpl dao;

    @Before
    public void setUP() {
        MockitoAnnotations.initMocks(this);
        startParamService = new UserStartParamImpl(dao);

        userStartParam = new UserStartParam();
        exercise = new Exercise();
        template = new ProgramTemplate();
        userStartParam.setID(1L);
        userStartParam.setInitialWeight(10);
        exercise.setExID(1L);
        template.setId(1L);

        paramList = new ArrayList<>();
        paramList.add(userStartParam);
        firstElement = 0;
        idOfCheckingElement = 1L;
    }

    @After
    public void tearDown() {
        paramList.clear();
    }

    @Test
    public void getAllStartParameters() {
        List<UserStartParam> expected = paramList;
        when(dao.getAll(UserStartParam.class)).thenReturn(Optional.ofNullable(paramList));

        Optional<List<UserStartParam>> actual = startParamService.getAllStartParameters();

        assertEquals(expected, actual.get());
    }

    @Test
    public void updateStartParameter() {
        UserStartParam userStartParam = paramList.get(firstElement);
        userStartParam.setInitialWeight(7);

        when(dao.getByID(UserStartParam.class, idOfCheckingElement))
                .thenReturn(Optional.ofNullable(userStartParam));

        when(dao.update(userStartParam)).thenReturn(userStartParam);

        boolean actual = startParamService.updateStartParameter(idOfCheckingElement, userStartParam);
        assertTrue(actual);
    }

    @Test(expected = UserStartParamNotFoundException.class)
    public void getExceptionOfUpdate() {
        UserStartParam userStartParam = paramList.get(firstElement);
        userStartParam.setInitialWeight(7);

        when(dao.getByID(UserStartParam.class, idOfCheckingElement))
                .thenReturn(Optional.empty());

        when(dao.update(userStartParam)).thenReturn(userStartParam);

        boolean actual = startParamService.updateStartParameter(idOfCheckingElement, userStartParam);
        assertTrue(actual);
    }

    @Test
    public void deleteUserStartParamById() {
        when(dao.getByID(UserStartParam.class, idOfCheckingElement))
                .thenReturn(Optional.ofNullable(paramList.get(firstElement)));

        doNothing().when(dao).deleteById(UserStartParam.class, idOfCheckingElement);

        startParamService.deleteUserStartParamById(idOfCheckingElement);
    }

    @Test(expected = UserStartParamNotFoundException.class)
    public void getExceptionOfDeletion() {
        when(dao.getByID(UserStartParam.class, idOfCheckingElement))
                .thenReturn(Optional.empty());

        doNothing().when(dao).deleteById(UserStartParam.class, idOfCheckingElement);

        startParamService.deleteUserStartParamById(idOfCheckingElement);
    }

    @Test
    public void getUserStartParamByIdTest() {
        when(dao.getByID(UserStartParam.class, userStartParam.getID()))
                .thenReturn(Optional.ofNullable(userStartParam));

        assertEquals(userStartParam,
                startParamService.getStartParamById(userStartParam.getID()));
    }

    @Test(expected = UserStartParamNotFoundException.class)
    public void getExceptionWhileGetting() {
        when(dao.getByID(UserStartParam.class, userStartParam.getID()))
                .thenReturn(Optional.empty());

        assertEquals(userStartParam,
                startParamService.getStartParamById(userStartParam.getID()));
    }
}