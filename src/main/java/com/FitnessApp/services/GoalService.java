package com.FitnessApp.services;

import com.FitnessApp.model.Goal;
import com.FitnessApp.utils.dtos.GoalDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GoalService {
    /**
     * Gets all goals
     *
     * @return list of GoalDto
     */
    Optional<List<GoalDto>> getAll();

    Goal getGoalById(Long id);

    /**
     * Adds new goal
     *
     * @param goalTitle title of goal for creating and writing to DB
     * @return if not exist - true, if exist - false
     */
    boolean createGoal(String goalTitle);

    /**
     * Updates exist goal
     *
     * @param dto current goal title
     * @param id new goal title
     * @return if goal is exist - return true, another case - false
     */
    boolean updateGoal(GoalDto dto, Long id);

    /**
     * Deletes goal
     *
     * @param dto goal transfer object
     * @param id id of goal for deleting
     * @return if goal is exist - return true, another case - false
     */
    boolean deleteGoal(GoalDto dto, Long id);
}
