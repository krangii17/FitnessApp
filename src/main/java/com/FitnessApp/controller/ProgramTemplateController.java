package com.FitnessApp.controller;

import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.security.UserDetailsImpl;
import com.FitnessApp.services.ExerciseService;
import com.FitnessApp.services.GoalService;
import com.FitnessApp.services.ProgramTemplateService;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.converters.ProgramTemplateConverter;
import com.FitnessApp.utils.dtos.programTemplate.ProgramTemplateDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(description = "This controller is used for training programs")
@RestController
public class ProgramTemplateController {


    private final ProgramTemplateService programTemplateService;

    private final ProgramTemplateConverter converter;

    private final UserService userService;

    private final GoalService goalService;

    private final ExerciseService exerciseService;

    @Autowired
    public ProgramTemplateController(@Qualifier("programTemplateServiceImpl") ProgramTemplateService programTemplateService,
                                     ProgramTemplateConverter converter, UserService userService,
                                     GoalService goalService, ExerciseService exerciseService) {

        this.programTemplateService = programTemplateService;
        this.converter = converter;
        this.userService = userService;
        this.goalService = goalService;
        this.exerciseService = exerciseService;
    }


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of all Program Templates",
            nickname = "getAll",
            response = ProgramTemplateDto.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "basicAuth")})

    @GetMapping(value = "/program-templates", produces = "application/json")
    public ResponseEntity getProgramTemplateList() {

        List<ProgramTemplate> oneProgramTemplate;
        List<ProgramTemplateDto> programTemplateDtos = new ArrayList<>();
        List<ProgramTemplate> programTemplates = programTemplateService.getAll()
                .orElse(new ArrayList<>());

        Set<String> programNameSet = programTemplates.stream()
                .map(ProgramTemplate::getProgramName)
                .collect(Collectors.toSet());

        for (String s : programNameSet) {
            oneProgramTemplate = programTemplates.stream()
                    .filter(programTemplate -> programTemplate.getProgramName()
                            .equals(s))
                    .collect(Collectors.toList());

            programTemplateDtos.add(converter.convertToDTO(oneProgramTemplate));
        }

        return ResponseEntity.ok(programTemplateDtos);
    }


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a program by id",
            nickname = "getById",
            response = ProgramTemplateDto.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @GetMapping(value = "/program-templates/{id}", produces = "application/json")
    public ResponseEntity getProgramTemplateById(@PathVariable Long id) {
        List<ProgramTemplate> programTemplates = new ArrayList<>();
        programTemplates.add(programTemplateService.getById(id));

        return ResponseEntity.ok(converter
                .convertToDTO(programTemplates));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a program by programe name",
            nickname = "getByProgramTemplateName",
            response = ProgramTemplateDto.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @GetMapping(value = "/program-templates/name", produces = "application/json")
    public ResponseEntity getProgramTemplateByName(String programName) {
        List<ProgramTemplate> programTemplates = programTemplateService.getByName(programName);

        return ResponseEntity.ok(converter
                .convertToDTO(programTemplates));
    }


    @ApiOperation(httpMethod = "POST",
            value = "Creates a new program template",
            nickname = "create",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object ProgramTemplate has been added",
                    response = String.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @PostMapping(value = "/program-templates")
    public ResponseEntity addProgramTemplate(Authentication authentication,
                                             @RequestBody ProgramTemplateDto programTemplateDto) {

        boolean allAdded = true;
        StringBuilder message = new StringBuilder();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<ProgramTemplate> programTemplates = converter.convertToEntity(programTemplateDto);
        for (ProgramTemplate prTemplate : programTemplates) {
            if (prTemplate.getUser().getId() == 0) {
                prTemplate.setUser(userService.getUserById(userDetails.getUser().getId()));
            } else {
                prTemplate.setUser(userService.getUserById(prTemplate.getUser().getId()));
            }

            prTemplate.setExercise(exerciseService.getExerciseById(prTemplate.getExercise().getExID()));

            prTemplate.setGoal(goalService.getGoalById(prTemplate.getGoal().getId()));

            boolean addProgramTemplate = programTemplateService.addProgramTemplate(prTemplate);

            if (!addProgramTemplate) {
                allAdded = false;
                message.append("Program template by id = ").append(prTemplate.getId()).append(" name is empty/n");
            }
        }

        if (allAdded) {
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(400).body(message.toString());
        }
    }


    @ApiOperation(httpMethod = "PUT",
            value = "Updates object 'ProgramTemplate' by id",
            nickname = "update",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Program updated",
                    response = String.class),
            @ApiResponse(code = 400, message = "Json object of ProgramTemplate incorrect")})
    @PutMapping(value = "/program-templates/{id}")
    public ResponseEntity updateProgramTemplate(@PathVariable Long id,
                                                @RequestBody ProgramTemplateDto programTemplateDTO) {

        boolean allUpdated = true;
        StringBuilder message = new StringBuilder();
        boolean isUpdateProgramTemplate;

        List<ProgramTemplate> programTemplates = converter.convertToEntity(programTemplateDTO);
        for (ProgramTemplate prTemplate : programTemplates) {
            prTemplate.setExercise(exerciseService.getExerciseById(prTemplate.getExercise().getExID()));
            prTemplate.setGoal(goalService.getGoalById(prTemplate.getGoal().getId()));
            prTemplate.setUser(userService.getUserById(prTemplate.getUser().getId()));

            isUpdateProgramTemplate = programTemplateService.updateProgramTemplate(id,
                    prTemplate);

            if (!isUpdateProgramTemplate) {
                allUpdated = false;
                message.append("Program template by id = ").append(prTemplate.getId()).append(" name is empty/n");
            }
        }
        if (allUpdated) {
            return ResponseEntity.ok("Updated");
        } else {
            return ResponseEntity.status(400).body(message.toString());
        }
    }


    @ApiOperation(httpMethod = "DELETE",
            value = "Delete program by id",
            nickname = "delete by id",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted",
                    response = String.class),
            @ApiResponse(code = 400, message = "Bad")})
    @DeleteMapping(value = "/program-templates/{id}")
    public ResponseEntity deleteProgramTemplateById(@PathVariable Long id) {

        boolean isDeleteProgramTemplate = programTemplateService.deleteProgramTemplateById(id);
        if (isDeleteProgramTemplate) {
            return ResponseEntity.ok("Deleted");
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @ApiOperation(httpMethod = "DELETE",
            value = "Delete program by program name",
            nickname = "delete by programName",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted",
                    response = String.class),
            @ApiResponse(code = 400, message = "Bad")})
    @DeleteMapping(value = "/program-templates")
    public ResponseEntity deleteProgramTemplateByName(String programName) {

        programTemplateService.deleteProgramTemplateByName(programName);

        return ResponseEntity.ok("Deleted");
    }

}
