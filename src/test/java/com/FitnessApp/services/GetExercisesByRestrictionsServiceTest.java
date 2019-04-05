package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAOImpl;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.impl.GetExercisesByRestrictionsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GetExercisesByRestrictionsServiceTest {

    @Mock
    private GenericDAOImpl dao;

    @InjectMocks
    private GetExercisesByRestrictionsService service;

    private Optional<List<Exercise>> exercises;

    private Set<Restriction> restrictions;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<Exercise> ex = new ArrayList<>();
        Exercise first = new Exercise("Ex name", "desc", 5);
        first.setExID(1L);
        ex.add(first);
        Exercise second = new Exercise("2 Ex name", "2 desc", 4);
        second.setExID(2L);
        ex.add(second);
        exercises = Optional.of(ex);
        restrictions = new HashSet<>();
        Restriction rest = new Restriction();
        rest.setRestID(1L);
        restrictions.add(rest);
    }

    @Test
    public void getExercisesByRestrictions() {
        Mockito.when(dao.getAll(Exercise.class)).thenReturn(exercises);
        Set<Exercise> exercises = service.getExercisesByRestrictions(restrictions);
        assertEquals(1, exercises.size());
    }
}