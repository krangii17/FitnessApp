package com.FitnessApp.services.impl;


import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ExerciseException;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.Exercise;
import com.FitnessApp.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private static final String NAME = "exerciseName";

    @Qualifier("genericDAOImpl")
    @Autowired
    private GenericDAO dao;

    public ExerciseServiceImpl() {
    }

    public ExerciseServiceImpl(GenericDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Exercise> getAll(int firstRes, int maxRes) {
        return dao.getAllPagination(Exercise.class, firstRes, maxRes).get();
    }

    @Override
    public Exercise getExerciseByName(String name) {
        List<Exercise> list = dao.getByParameter(Exercise.class, NAME, name).orElse(new ArrayList<>());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Exercise getExerciseById(Long id) throws ObjectNotFoundException {
        Optional<Exercise> exercise =
                dao.getByID(Exercise.class, id);

        if (exercise.isPresent()) {
            return exercise.get();
        } else {
            throw new ExerciseException("Invalid ID");
        }
    }

    @Override
    public boolean updateExercise(Long id, Exercise exercise) {
        if (exercise.getDescription().isEmpty()) {
            return false;
        } else {
            Optional<Exercise> optionalExercise = dao.getByID(Exercise.class, id);
            if (optionalExercise.isPresent()) {
                optionalExercise.get().setDescription(exercise.getDescription());
                optionalExercise.get().setExerciseName(exercise.getExerciseName());
                dao.update(optionalExercise.get());
                return true;
            } else {
                throw new ObjectNotFoundException(id.intValue());
            }
        }
    }

    @Override
    public boolean addNewExercise(Exercise exercise) {

        if (!isPresent(exercise.getExerciseName())) {
            exercise.setExID(null);
            dao.save(exercise);
            return true;
        }
        return false;
    }

    private boolean isPresent(String exerciseName) {
        if (dao.getByParameter(Exercise.class, NAME, exerciseName).get().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExercise(Exercise exercise) {
        if (!isPresent(exercise.getExerciseName())) {
            dao.delete(exercise);
            return true;
        }
        return false;
    }


}
