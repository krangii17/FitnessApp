package com.FitnessApp.services.impl;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.services.WeightGainExerciseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WeightGainExerciseServiceImpl implements WeightGainExerciseService {
    @Autowired
    private GetExercisesByRestrictionsService service;

    private Set<MusclesGroup> groups;

    public WeightGainExerciseServiceImpl() {
        groups = new HashSet<>();
    }

    @Override
    public List<Exercise> getExercises(Set<Restriction> restrictions) {
        List<Exercise> ex = new ArrayList<>(service.getExercisesByRestrictions(restrictions));
        for (Exercise loop : ex) {
            if ((loop.getExerciseEffectiveness() <= 5) && (loop.getExerciseEffectiveness() >= 3)) {
                List<MusclesGroup> loopMuscles = loop.getMusclesGroup();
                for (MusclesGroup group : loopMuscles) {
                    if (!isExistMuscleGroupInOurExercise(group)) {
                        setMusclesGroup(loopMuscles);
                    } else {
                        ex.remove(loop);
                    }
                }
            } else {
                ex.remove(loop);
            }
        }
        return ex;
    }

    private void setMusclesGroup(List<MusclesGroup> muscles) {
        groups.addAll(muscles);
    }

    private boolean isExistMuscleGroupInOurExercise(MusclesGroup group) {
        return groups.contains(group);
    }
}
