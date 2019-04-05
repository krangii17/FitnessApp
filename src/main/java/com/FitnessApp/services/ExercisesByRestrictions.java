package com.FitnessApp.services;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;

import java.util.Set;

public interface ExercisesByRestrictions {
    Set<Exercise> getExercisesByRestrictions(Set<Restriction> restrictions);
}
