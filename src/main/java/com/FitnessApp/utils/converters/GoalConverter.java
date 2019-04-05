package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Goal;
import com.FitnessApp.utils.dtos.GoalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


public class GoalConverter {

    public Goal asGoal(GoalDto dto) {
        return new Goal(dto.getId(), dto.getGoalTitle());
    }

    public GoalDto asGoalDto(Goal goal) {
        return new GoalDto(goal.getId(), goal.getGoalTitle());
    }

    public List<GoalDto> asListGoalDto(List<Goal> goals) {
        return goals.stream()
                .map(Goal -> asGoalDto(Goal))
                .collect(Collectors.toList());
    }
}
