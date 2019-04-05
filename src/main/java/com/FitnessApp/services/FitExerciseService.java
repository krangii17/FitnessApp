package com.FitnessApp.services;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;

import java.util.List;
import java.util.Set;

public interface FitExerciseService {
    List<Exercise> getExercises(Set<Restriction> restrictions);
}
