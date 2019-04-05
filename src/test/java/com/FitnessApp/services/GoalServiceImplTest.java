package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.model.Goal;
import com.FitnessApp.services.impl.GoalServiceImpl;
import com.FitnessApp.utils.converters.GoalConverter;
import com.FitnessApp.utils.dtos.GoalDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoalServiceImplTest {
    private final String COLUMN_NAME = "goalTitle";
    private long id = 1;
    private long incorrectId = 2;
    private String firstGoal = "first";
    private String secondGoal = "second";
    private String goalNotExist = "not exist";
    private GoalDto dto;
    private List<GoalDto> dtos = new ArrayList<>();
    private List<Goal> goals = new ArrayList<>();
    private GoalServiceImpl service;
    private Goal first;
    private GenericDAO dao = mock(GenericDAO.class);
    private GoalConverter converter = mock(GoalConverter.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new GoalServiceImpl(dao);
        dto = new GoalDto();
        first = new Goal(firstGoal);
        first.setId(id);
        Goal second = new Goal(secondGoal);
        goals.add(first);
        goals.add(second);
    }

    @After
    public void tearDown() throws Exception {
        dtos.clear();
        goals.clear();
    }

    @Test
    public void getAll() {
        when(dao.getAll(Goal.class)).thenReturn(Optional.of(goals));

        assertEquals(goals.size(), service.getAll().get().size());
    }

    @Test
    public void createGoalIsNew() {
        when(dao.getByParameter(Goal.class, COLUMN_NAME, goalNotExist))
                .thenReturn(Optional.of(new ArrayList<>()));

        assertTrue(service.createGoal(goalNotExist));
    }

    @Test
    public void createGoalIsPresent() {
        when(dao.getByParameter(Goal.class, COLUMN_NAME, firstGoal))
                .thenReturn(Optional.of(goals));

        assertFalse(service.createGoal(firstGoal));
    }

    @Test
    public void updateGoalIsPresent() {
        dto.setId(id);
        dto.setGoalTitle(firstGoal);
        when(dao.getByID(Goal.class, id))
                .thenReturn(Optional.of(first));
        when(converter.asGoal(dto)).thenReturn(first);

        assertTrue(service.updateGoal(dto, id));
    }

    @Test
    public void updateGoalIsNotPresent() {
        dto.setGoalTitle(goalNotExist);
        when(dao.getByID(Goal.class, incorrectId))
                .thenReturn(Optional.of(new Goal()));

        assertFalse(service.updateGoal(dto, incorrectId));
    }

    @Test
    public void deleteGoalIsPresent() {
        dto.setId(id);
        dto.setGoalTitle(firstGoal);
        when(dao.getByID(Goal.class, id))
                .thenReturn(Optional.of(first));

        assertTrue(service.deleteGoal(dto, id));
    }


    @Test
    public void deleteGoalIsNotPresent() {
        dto.setGoalTitle(goalNotExist);
        when(dao.getByID(Goal.class, incorrectId))
                .thenReturn(Optional.of(new Goal()));

        assertFalse(service.deleteGoal(dto, incorrectId));
    }
}
