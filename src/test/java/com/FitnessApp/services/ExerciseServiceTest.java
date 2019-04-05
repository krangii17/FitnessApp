package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAOImpl;
import com.FitnessApp.exceptions.ExerciseException;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.services.impl.ExerciseServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class ExerciseServiceTest {

    @Mock
    private GenericDAOImpl dao;
    @InjectMocks
    private ExerciseService service = new ExerciseServiceImpl();

    private List<Exercise> exercises;
    private Exercise exercise1;

    private Exercise exercise2;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        exercise1 = new Exercise("name1", "description1", 1);
        exercise2 = new Exercise("name2", "description2", 2);

        exercises = new ArrayList<>();
        exercises.add(exercise1);
        exercises.add(exercise2);

    }


    @Test
    public void shouldReturnAllExercises() {
        assertEquals(exercises.size(), 2);
    }

    @Test
    public void shouldReturnExerciseByName() throws ExerciseException {
        when(dao.getByParameter(Exercise.class, "exerciseName", "name1"))
                .thenReturn(Optional.ofNullable(exercises));

        Exercise exerciseFromDao = service.getExerciseByName("name1");

        assertEquals(exercise1, exerciseFromDao);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExerciseException() throws ExerciseException {
        when(dao.getByParameter(Exercise.class, "exerciseName", "name101"))
                .thenReturn(Optional.of(Collections.emptyList()));

        Exercise userFromDao = service.getExerciseByName("name101");

        assertNull(userFromDao);
        assertEquals(userFromDao.getExerciseName(), "name101");
    }


}
