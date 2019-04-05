package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ExerciseException;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.services.impl.ExerciseServiceImpl;
import org.junit.After;
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

public class ExerciseServiceImplTest {

    Exercise exercise1;
    Exercise exercise2;
    Exercise exerciseNotExist;
    private GenericDAO dao = mock(GenericDAO.class);
    private ExerciseServiceImpl service;
    private List<Exercise> exercises;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        service = new ExerciseServiceImpl(dao);
        exercises = new ArrayList<>();

        exercise1 = new Exercise("name1", "description1", 1);
        exercise2 = new Exercise("name2", "description2", 2);

        exerciseNotExist = new Exercise("not exist", "not exist", 3);

        exercises.add(exercise1);
        exercises.add(exercise2);
    }

    @After
    public void tearDown() throws Exception {
        exercises.clear();
    }


    @Test
    public void shouldReturnExerciseById() {
        int index = 0;
        Exercise expected = exercises.get(index);
        when(dao.getByID(Exercise.class, exercises.get(index).getExID()))
                .thenReturn(Optional.ofNullable(exercises.get(index)));

        Exercise actual = service.getExerciseById(exercises.get(index).getExID());

        assertEquals(expected, actual);
    }

    @Test(expected = ExerciseException.class)
    public void shouldThrowExerciseExceptionCauseEnterInvalidId() {
        int index = 0;
        when(dao.getByID(Exercise.class, exercises.get(1).getExID()))
                .thenReturn(Optional.empty());

        service.getExerciseById(exercises.get(index).getExID());
    }

    @Test
    public void shouldAddNewExercise() {

        when(dao.getByParameter(Exercise.class, "exerciseName", exerciseNotExist.getExerciseName()))
                .thenReturn((Optional.ofNullable(exercises)));

        assertFalse(service.addNewExercise(exerciseNotExist));
    }

    @Test
    public void shouldCanNotAddNewExerciseCauseExerciseExistInDB() {

        when(dao.getByParameter(Exercise.class, "exerciseName", exercise1.getExerciseName()))
                .thenReturn((Optional.of(Collections.emptyList())));

        assertTrue(service.addNewExercise(exercise1));
    }
}