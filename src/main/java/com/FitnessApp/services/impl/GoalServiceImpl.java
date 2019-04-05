package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.Goal;
import com.FitnessApp.services.GoalService;
import com.FitnessApp.utils.converters.GoalConverter;
import com.FitnessApp.utils.dtos.GoalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class GoalServiceImpl implements GoalService {
    /**
     * Name of column for finding Goal by goalTitle
     */
    private final String COLUMN_NAME = "goalTitle";

    @Qualifier(value = "genericDAOImpl")
    @Autowired
    private GenericDAO dao;

    private GoalConverter converter = new GoalConverter();

    /**
     * Default constructor
     */
    public GoalServiceImpl() {
    }

    /**
     * Constructor with parameter
     *
     * @param dao accepts GenericDAO
     */
    public GoalServiceImpl(GenericDAO dao) {
        this.dao = dao;
    }

    @Override
    public Optional<List<GoalDto>> getAll() {
        return Optional.of(converter.asListGoalDto(dao.getAll(Goal.class).get()));
    }

    @Override
    public Goal getGoalById(Long id) throws ObjectNotFoundException {
        Optional<Goal> optionalGoal =
                dao.getByID(Goal.class, id);

        if (optionalGoal.isPresent()) {
            return optionalGoal.get();
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }

    @Override
    public boolean createGoal(String goalTitle) {
        if (!isPresent(goalTitle)) {
            dao.save(new Goal(goalTitle));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateGoal(GoalDto dto, Long id) {
        if (dao.getByID(Goal.class, id).orElse(new Goal()).getId() != null) {
            dao.update(converter.asGoal(dto));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteGoal(GoalDto dto, Long id) {
        Goal goal = dao.getByID(Goal.class, id).orElse(new Goal());
        if (goal.getId() != null) {
            dao.delete(goal);
            return true;
        }
        return false;
    }

    /**
     * Checks goals is present in DB
     *
     * @param title goalTitle
     * @return if goal is found - return true, another case - false
     */
    private boolean isPresent(String title) throws ObjectNotFoundException {
        if (dao.getByParameter(Goal.class, COLUMN_NAME, title).get().size() > 0) {
            return true;
        }
        return false;
    }
}
