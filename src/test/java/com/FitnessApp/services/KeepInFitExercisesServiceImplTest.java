package com.FitnessApp.services;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.impl.GetExercisesByRestrictionsService;
import com.FitnessApp.services.impl.KeepInFitExercisesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;

public class KeepInFitExercisesServiceImplTest {

    @Mock
    private GetExercisesByRestrictionsService getExercisesByRestrictionsService;

    @InjectMocks
    private KeepInFitExercisesServiceImpl keepInFitExercisesService;

    private Set<Restriction> restrictions;

    private List<Exercise> exercises;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        exercises = new ArrayList<>();
        Exercise first = new Exercise("Exer", "desc", 5);
        first.setExID(1L);
        first.setExerciseEffectiveness(3);
        exercises.add(first);
        Exercise second = new Exercise("Exer 2", "2 desc", 4);
        second.setExID(2L);
        second.setExerciseEffectiveness(5);
        exercises.add(second);
        restrictions = new HashSet<>();
        Restriction rest = new Restriction();
        rest.setRestID(1L);
        restrictions.add(rest);
    }

    @Test
    public void getExercises() {
        Set<Exercise> tempExercises = new HashSet<>(exercises);
        Mockito.when(getExercisesByRestrictionsService
                .getExercisesByRestrictions(restrictions))
                .thenReturn(tempExercises);
        List<Exercise> actualExercises = keepInFitExercisesService.getExercises(restrictions);
        assertEquals(actualExercises.size(), 1);
    }
}