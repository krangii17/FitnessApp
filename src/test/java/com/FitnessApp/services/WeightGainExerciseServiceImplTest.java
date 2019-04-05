package com.FitnessApp.services;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.impl.GetExercisesByRestrictionsService;
import com.FitnessApp.services.impl.WeightGainExerciseServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class WeightGainExerciseServiceImplTest {

    @Mock
    private GetExercisesByRestrictionsService getExercisesByRestrictionsService;

    @InjectMocks
    private WeightGainExerciseServiceImpl weightGainExerciseService;

    private Set<Restriction> restrictions;

    private List<Exercise> exercises;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        exercises = new ArrayList<>();
        Exercise first = new Exercise("Exercise", "desc", 5);
        first.setExID(1L);
        first.setExerciseEffectiveness(3);
        exercises.add(first);
        Exercise second = new Exercise("Exercise", "2 desc", 4);
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
        List<Exercise> actualExercises = weightGainExerciseService.getExercises(restrictions);
        assertEquals(actualExercises.size(), 2);
    }
}