package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.exceptions.ProgramTemplateException;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.services.ProgramTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProgramTemplateServiceImpl implements ProgramTemplateService {

    private final GenericDAO dao;

    @Autowired
    public ProgramTemplateServiceImpl(@Qualifier("programTemplateDAOImpl") GenericDAO dao) {
        this.dao = dao;
    }


    @Override
    public Optional<List<ProgramTemplate>> getAll() {
        return dao.getAll(ProgramTemplate.class);
    }

    @Override
    public List<ProgramTemplate> getByName(String programName) throws ObjectNotFoundException {
        List<ProgramTemplate> programTemplates;

        programTemplates = dao.getByParameter(ProgramTemplate.class,
                "programName", programName)
                .orElse(new ArrayList<>());

        if (programTemplates.size() > 0) {
            return programTemplates;
        } else {
            throw new ObjectNotFoundException("Can't find object by parameterName: programName"
                    + " parameterValue: " + programName);
        }
    }

    @Override
    public ProgramTemplate getById(Long id) {

        Optional<ProgramTemplate> programTemplate =
                dao.getByID(ProgramTemplate.class, id);

        if (programTemplate.isPresent()) {
            return programTemplate.get();
        } else {
            throw new ProgramTemplateException();
        }
    }

    @Override
    public boolean addProgramTemplate(ProgramTemplate programTemplate) {

        if (programTemplate.getProgramName().isEmpty()) {
            return false;
        } else {
            programTemplate.setId(null);
            dao.save(programTemplate);
            return true;
        }
    }

    @Override
    public boolean updateProgramTemplate(Long id, ProgramTemplate programTemplate) {

        if (programTemplate.getProgramName().isEmpty()) {
            return false;
        } else {
            Optional<ProgramTemplate> optionalProgramTemplate = dao.getByID(ProgramTemplate.class, id);
            if (optionalProgramTemplate.isPresent()) {
                programTemplate.setId(id);
                dao.update(programTemplate);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean deleteProgramTemplateById(Long id) {
        if (dao.getByID(ProgramTemplate.class, id).isPresent()) {
            dao.deleteById(ProgramTemplate.class, id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteProgramTemplateByName(String programName) {
        List<ProgramTemplate> programTemplates = getByName(programName);

        for (ProgramTemplate programTemplate : programTemplates) {
            dao.delete(programTemplate);
        }
    }
}
