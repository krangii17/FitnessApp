package com.FitnessApp.services;

import com.FitnessApp.exceptions.ExerciseException;
import com.FitnessApp.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseService {

    List<Exercise> getAll(int firstRes, int maxRes);

    Exercise getExerciseByName(String name) throws ExerciseException;

    boolean addNewExercise(Exercise exercise);

    boolean deleteExercise(Exercise exercise);

    Exercise getExerciseById(Long id);

    boolean updateExercise(Long id, Exercise exercise);
}
