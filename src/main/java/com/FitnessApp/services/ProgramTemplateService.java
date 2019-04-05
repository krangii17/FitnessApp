package com.FitnessApp.services;

import com.FitnessApp.model.ProgramTemplate;

import java.util.List;
import java.util.Optional;

public interface ProgramTemplateService {

    Optional<List<ProgramTemplate>> getAll();

    List<ProgramTemplate> getByName(String programName);

    ProgramTemplate getById(Long id);

    boolean addProgramTemplate(ProgramTemplate programTemplate);

    boolean updateProgramTemplate(Long id, ProgramTemplate programTemplate);

    boolean deleteProgramTemplateById(Long id);

    void deleteProgramTemplateByName(String programName);
}
