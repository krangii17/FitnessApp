package com.FitnessApp.services.impl;

import com.FitnessApp.dao.IUserProgramDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.UserProgram;
import com.FitnessApp.services.IEntityService;
import com.FitnessApp.utils.validators.UserProgramValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserProgramService implements IEntityService<UserProgram> {

    private final IUserProgramDAO dao;

    @Autowired
    public UserProgramService(@Qualifier("userProgramDAOImpl") IUserProgramDAO dao) {
        this.dao = dao;
    }

    @Override
    public Optional<List<UserProgram>> getAll() {
        return dao.getAll(UserProgram.class);
    }

    @Override
    public List<UserProgram> getByParameter(String parameterName, String parameterValue)
            throws ObjectNotFoundException {

        List<UserProgram> list;

        if (parameterName.trim().equals("user.id")) {
            list = dao.getByUserId(Long.valueOf(parameterValue))
                    .orElse(new ArrayList<>());
        } else if (parameterName.trim().equals("user.email")) {
            list = dao.getByUserLogin(parameterValue)
                    .orElse(new ArrayList<>());
        } else {
            list = dao.getByParameter(UserProgram.class, parameterName.trim(), parameterValue.trim())
                    .orElse(new ArrayList<>());
        }

        if (list.size() > 0) {
            return list;
        } else {
            throw new ObjectNotFoundException("Can't find object by parameterName: " + parameterName
                    + " parameterValue: " + parameterValue);
        }
    }

    @Override
    public UserProgram getByID(Long id) throws ObjectNotFoundException {
        Optional<UserProgram> optionalUserProgram =
                dao.getByID(UserProgram.class, id);

        if (optionalUserProgram.isPresent()) {
            return optionalUserProgram.get();
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }

    @Override
    public boolean save(UserProgram savedObj) {
        if (!UserProgramValidator.validateEntity(savedObj)) {
            return false;
        } else {
            savedObj.setId(null);
            dao.save(savedObj);
            return true;
        }
    }

    @Override
    public boolean update(Long id, UserProgram updatedObj) throws ObjectNotFoundException {

        Optional<UserProgram> userProgram = dao.getByID(UserProgram.class, id);
        if (userProgram.isPresent()) {
            updatedObj.setId(userProgram.get().getId());
            updatedObj.setUser(userProgram.get().getUser());
            if (UserProgramValidator.validateEntity(updatedObj)) {
                dao.update(updatedObj);
                return true;
            } else {
                return false;
            }
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }

    @Override
    public void delete(UserProgram deletedObj) throws ObjectNotFoundException {
        if (deletedObj.getId() == null) {
            throw new ObjectNotFoundException("Not found id for user program:" + deletedObj.getProgramName());
        } else {
            dao.delete(deletedObj);
        }
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        if (dao.getByID(UserProgram.class, id).isPresent()) {
            dao.deleteById(UserProgram.class, id);
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }


}
