package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.RestrictionNotFoundException;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.model.State;
import com.FitnessApp.services.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestrictionServiceImpl implements RestrictionService {

    private GenericDAO dao;

    @Autowired
    public RestrictionServiceImpl(@Qualifier("genericDAOImpl") GenericDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean changeRestStatus(boolean status, Long id) throws RestrictionNotFoundException {
        Optional<Restriction> optionalRestriction = dao.getByID(Restriction.class, id);
        if (optionalRestriction.isPresent()) {
            if (status) {
                optionalRestriction.get().setState(State.ACTIVE);
                dao.update(optionalRestriction.get());
                return true;
            } else {
                optionalRestriction.get().setState(State.DISACTIVE);
                dao.update(optionalRestriction.get());
                return false;
            }
        } else {
            throw new RestrictionNotFoundException();
        }
    }

    @Override
    public Optional<List<Restriction>> getAllRestrictions() {
        return dao.getAll(Restriction.class);
    }

    @Override
    public boolean changeEvaluation(Long id, Restriction updateRest) throws RestrictionNotFoundException {
        Optional<Restriction> restriction = dao.getByID(Restriction.class, id);
        if (restriction.isPresent()) {
            restriction.get().setEvaluation(updateRest.getEvaluation());
            dao.update(restriction.get());
            return true;
        } else {
            throw new RestrictionNotFoundException();
        }
    }

    @Override
    public boolean deleteRestrictionByID(Long id) throws RestrictionNotFoundException {
        if (dao.getByID(Restriction.class, id).isPresent()) {
            dao.deleteById(Restriction.class, id);
            return true;
        } else {
            throw new RestrictionNotFoundException();
        }
    }
}
