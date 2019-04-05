package com.FitnessApp.controller;

import com.FitnessApp.services.ChooseProgramService;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChooseProgramController {


    private final ChooseProgramService programService;

    @Autowired
    public ChooseProgramController(@Qualifier("chooseProgramServiceImpl") ChooseProgramService programService) {
        this.programService = programService;
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of program template by goal and days ",
            nickname = "chooseProgram",
            response = ProgramTemplateDto.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/program-users/users/{userId}/goals/{goalId}", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity getUserProgramById(@PathVariable Long userId, @PathVariable Long goalId) {
        return ResponseEntity.ok(programService.findProgramsTemplate(goalId, userId));
    }

}
