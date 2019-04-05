package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.UserStartParamNotFoundException;
import com.FitnessApp.model.UserStartParam;
import com.FitnessApp.services.ExerciseService;
import com.FitnessApp.services.ProgramTemplateService;
import com.FitnessApp.services.UserStartParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserStartParamImpl implements UserStartParamService {
    private GenericDAO dao;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ProgramTemplateService programTemplateService;

    @Autowired
    public UserStartParamImpl(@Qualifier("genericDAOImpl") GenericDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean saveUserStartParam(UserStartParam startParam) {
        if ((startParam.getInitialWeight() == 0)) {
            return false;
        } else {
            startParam.setExercise(exerciseService.
                    getExerciseById(startParam.getExercise().getExID()));
            startParam.setProgramTemplate(programTemplateService.
                    getById(startParam.getProgramTemplate().getId()));
            startParam.setID(null);
            dao.save(startParam);
            return true;
        }
    }

    @Override
    public Optional<List<UserStartParam>> getAllStartParameters() {
        return dao.getAll(UserStartParam.class);
    }

    @Override
    public boolean deleteUserStartParam(UserStartParam startParam) {
        if (startParam.getID() == 0) {
            throw new UserStartParamNotFoundException("Not found id of start " +
                    "parameters" + startParam.getInitialWeight());
        } else {
            dao.delete(startParam);
            return true;
        }
    }

    @Override
    public boolean updateStartParameter(Long id, UserStartParam updateParam) throws UserStartParamNotFoundException {
        Optional<UserStartParam> startParam = dao.getByID(UserStartParam.class, id);
        if (startParam.isPresent()) {
            updateParam.setID(startParam.get().getID());
            updateParam.setInitialWeight(startParam.get().getInitialWeight());
            dao.update(updateParam);
            return true;
        } else {
            throw new UserStartParamNotFoundException(id.intValue());
        }
    }

    @Override
    public boolean deleteUserStartParamById(Long id) throws UserStartParamNotFoundException {
        if (dao.getByID(UserStartParam.class, id).isPresent()) {
            dao.deleteById(UserStartParam.class, id);
            return true;
        } else {
            throw new UserStartParamNotFoundException(id.intValue());
        }
    }

    @Override
    public UserStartParam getStartParamById(Long id) {
        Optional<UserStartParam> optionalUserStartParam =
                dao.getByID(UserStartParam.class, id);

        if (optionalUserStartParam.isPresent()) {
            return optionalUserStartParam.get();
        } else {
            throw new UserStartParamNotFoundException(id.intValue());
        }
    }
}
