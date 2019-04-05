package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.services.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MusclesGroupService implements IEntityService<MusclesGroup> {

    private final GenericDAO dao;

    @Autowired
    public MusclesGroupService(@Qualifier("genericDAOImpl") GenericDAO dao) {
        this.dao = dao;
    }

    @Override
    public Optional<List<MusclesGroup>> getAll() {
        return dao.getAll(MusclesGroup.class);
    }

    @Override
    public List<MusclesGroup> getByParameter(String parameterName, String parameterValue)
            throws ObjectNotFoundException {

        List<MusclesGroup> list = dao.getByParameter(MusclesGroup.class, parameterName.trim(), parameterValue.trim())
                .orElse(new ArrayList<>());
        if (list.size() > 0) {
            return list;
        } else {
            throw new ObjectNotFoundException("Can't find object by parameterName: " + parameterName
                    + " parameterValue: " + parameterValue);
        }
    }

    @Override
    public MusclesGroup getByID(Long id) throws ObjectNotFoundException {
        Optional<MusclesGroup> optionalMusclesGroup =
                dao.getByID(MusclesGroup.class, id);

        if (optionalMusclesGroup.isPresent()) {
            return optionalMusclesGroup.get();
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }

    @Override
    public boolean save(MusclesGroup savedObj) {
        if (savedObj.getGroupName().isEmpty()) {
            return false;
        } else {
            savedObj.setID(null);
            dao.save(savedObj);
            return true;
        }
    }

    @Override
    public boolean update(Long id, MusclesGroup updatedObj) throws ObjectNotFoundException {
        if (updatedObj.getGroupName().isEmpty()) {
            return false;
        } else {
            Optional<MusclesGroup> musclesGroup = dao.getByID(MusclesGroup.class, id);
            if (musclesGroup.isPresent()) {
                musclesGroup.get().setGroupName(updatedObj.getGroupName());
                dao.update(musclesGroup.get());
                return true;
            } else {
                throw new ObjectNotFoundException(id.intValue());
            }
        }
    }

    @Override
    public void delete(MusclesGroup deletedObj) throws ObjectNotFoundException {
        if (deletedObj.getID() == null) {
            throw new ObjectNotFoundException("Not found id for muscles group:" + deletedObj.getGroupName());
        } else {
            dao.delete(deletedObj);
        }
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        if (dao.getByID(MusclesGroup.class, id).isPresent()) {
            dao.deleteById(MusclesGroup.class, id);
        } else {
            throw new ObjectNotFoundException(id.intValue());
        }
    }
}
