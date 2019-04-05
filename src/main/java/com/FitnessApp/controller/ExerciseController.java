package com.FitnessApp.controller;

import com.FitnessApp.model.Exercise;
import com.FitnessApp.services.ExerciseService;
import com.FitnessApp.utils.converters.ExerciseConverter;
import com.FitnessApp.utils.dtos.ControllerMessage;
import com.FitnessApp.utils.dtos.ExerciseDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(consumes = "application/json")
@RestController
public class ExerciseController {
    private static final String MODEL = "exercise";
    @Qualifier("exerciseServiceImpl")
    @Autowired
    ExerciseService exerciseService;

    private ExerciseConverter converter = new ExerciseConverter();

    @ApiOperation(httpMethod = "POST",
            value = "Resource to add new exercise",
            nickname = "add exercise",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Object Exercise has been created",
            response = ExerciseDto.class),
            @ApiResponse(code = 409, message = "Json object of Exercise incorrect")})

    @RequestMapping(path = "/exercise", method = RequestMethod.POST)
    public ResponseEntity<?> addExercise(@RequestBody ExerciseDto dto) {
        Exercise exercise = converter.exerciseDtoConvertToExercise(dto);
        if (exerciseService.addNewExercise(exercise)) {
            return ResponseEntity.status(201).build();
        } else
            return ResponseEntity.status(409).build();

    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list exercises",
            nickname = "getExercises",
            response = ExerciseDto.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of exercises was returned")})
    @GetMapping("/exercises")
    public ResponseEntity<?> getExercises(int first, int max) {
        List<Exercise> exerciseList = exerciseService.getAll(first, max);
        if (exerciseList.size() != 0 && exerciseList.size() <= max) {
            return ResponseEntity.of(Optional.of(converter.asListExerciseDto(exerciseList)));
        }
        return ResponseEntity.status(404).body(ControllerMessage.STATUS_404.build(MODEL));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a exercise",
            nickname = "getExercise",
            response = ExerciseDto.class,
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "List of exercises was returned")})
    @RequestMapping(path = "/exercise", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getExercise(String exerciseName) {
        Exercise exercise = exerciseService.getExerciseByName(exerciseName);
        if (exercise == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(converter
                .exerciseConvertToExerciseDto(exercise));
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete object Exercise",
            nickname = "delete exercise",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object exercise has been deleted",
            response = String.class)})

    @RequestMapping(path = "/exercise", method = RequestMethod.DELETE)
    public ResponseEntity deleteExercise(@RequestBody ExerciseDto dto) {
        Exercise exercise = converter.exerciseDtoConvertToExercise(dto);
        if (exerciseService.deleteExercise(exercise)) {
            return ResponseEntity.status(201).build();
        } else
            return ResponseEntity.status(409).build();
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to change object Exercise",
            nickname = "change exercise",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object Exercise has been changed",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of Exercise incorrect",
                    response = String.class)})

    @RequestMapping(path = "/exercise/{id}", method = RequestMethod.PUT)
    public ResponseEntity renameMusclesGroup(@PathVariable Long id,
                                             @RequestBody ExerciseDto musclesGroupDTO) {

        boolean res = exerciseService.updateExercise(id,
                converter.exerciseDtoConvertToExercise(musclesGroupDTO));
        if (res) {
            return ResponseEntity.ok("Renamed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
