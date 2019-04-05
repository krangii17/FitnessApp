package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.utils.dtos.ExerciseDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExerciseConverterTest {


    private Exercise exercise;
    private ExerciseDto dto;
    private ExerciseConverter converter;

    @Before
    public void setUp() throws Exception {

        converter = new ExerciseConverter();

        exercise = new Exercise();
        exercise.setExID(1L);
        exercise.setExerciseName("Bench");
        exercise.setDescription("You push the bar");
        exercise.setExerciseEffectiveness(5);


        dto = new ExerciseDto(1L, "Bench",
                "You push the bar", 5);
    }

    @Test
    public void shouldConvertExerciseToExerciseDto() {


        ExerciseDto expected = dto;
        ExerciseDto actual = converter.exerciseConvertToExerciseDto(exercise);
        assertEquals(expected.getExID(), actual.getExID());
        assertEquals(expected.getExerciseName(), actual.getExerciseName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getExerciseEffectiveness(), actual.getExerciseEffectiveness());


    }

    @Test
    public void shouldConvertExerciseDtoToExercise() {
        Exercise expected = exercise;
        Exercise actual = converter.exerciseDtoConvertToExercise(dto);
        assertEquals(expected.getExID(), actual.getExID());
        assertEquals(expected.getExerciseName(), actual.getExerciseName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getExerciseEffectiveness(), actual.getExerciseEffectiveness());
    }
}