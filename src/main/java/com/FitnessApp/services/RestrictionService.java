package com.FitnessApp.services;

import com.FitnessApp.exceptions.RestrictionNotFoundException;
import com.FitnessApp.model.Restriction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RestrictionService {
    boolean changeRestStatus(boolean status, Long id) throws RestrictionNotFoundException;

    Optional<List<Restriction>> getAllRestrictions();

    boolean changeEvaluation(Long id, Restriction updateRest) throws RestrictionNotFoundException;

    boolean deleteRestrictionByID(Long id) throws RestrictionNotFoundException;

}
