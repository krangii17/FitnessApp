package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.ExercisesByRestrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GetExercisesByRestrictionsService implements ExercisesByRestrictions {

    @Autowired
    @Qualifier("genericDAOImpl")
    private GenericDAO dao;

    private List<Exercise> exercises;

    private Set<Long> getExercisesIDFromDAO() {
        Optional<List<Exercise>> opt = dao.getAll(Exercise.class);
        Set<Long> returnedExercises = new HashSet<>();
        if (opt.isPresent()) {
            exercises = opt.get();
            for (Exercise exercise : exercises) {
                returnedExercises.add(exercise.getExID());
            }
        }
        return returnedExercises;
    }

    @Override
    public Set<Exercise> getExercisesByRestrictions(Set<Restriction> restrictions) {
        Set<Long> exerciseID = getExercisesIDFromDAO();
        Set<Exercise> returnedSet = new HashSet<>(exercises);
        if (!exerciseID.isEmpty()) {
            for (Restriction rest : restrictions) {
                if (exerciseID.contains(rest.getRestID())) {
                    returnedSet.remove(exercises.get((int) (rest.getRestID() - 1)));
                }
            }
        }
        return returnedSet;
    }
}
