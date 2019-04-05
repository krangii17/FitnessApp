package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.utils.dtos.ExerciseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class ExerciseConverter {


    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseConverter() {
        modelMapper = new ModelMapper();
    }


    public ExerciseDto exerciseConvertToExerciseDto(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDto.class);
    }

    public Exercise exerciseDtoConvertToExercise(ExerciseDto exerciseDto) {

        return modelMapper.map(exerciseDto, Exercise.class);
    }

    public List<ExerciseDto> asListExerciseDto(List<Exercise> exercises) {
        return exercises.stream()
                .map(Exercise -> exerciseConvertToExerciseDto(Exercise))
                .collect(Collectors.toList());
    }
}
