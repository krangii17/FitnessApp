package com.FitnessApp.services;

import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import org.springframework.stereotype.Service;

@Service
public interface ChooseProgramService {

    ProgramTemplateDto findProgramsTemplate(Long goalId, Long userId);
}
