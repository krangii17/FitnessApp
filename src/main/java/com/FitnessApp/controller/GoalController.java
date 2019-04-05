package com.FitnessApp.controller;

import com.FitnessApp.services.GoalService;
import com.FitnessApp.utils.dtos.GoalDto;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(description = "Controller for management goals of trainings. Accessed for Admin and Coach roles")
@RestController
@RequestMapping("/admin/goals")
public class GoalController {
    private static final String MESSAGE_TITLE = "message";
    private static final JSONPObject SUCCESS_GOAL_CREATE_MESSAGE = new JSONPObject(MESSAGE_TITLE, "Goal is created");
    private static final JSONPObject FAIL_GOAL_CREATE_MESSAGE = new JSONPObject(MESSAGE_TITLE, "Goal already exist");
    private static final JSONPObject SUCCESS_GOAL_UPDATE_MESSAGE = new JSONPObject(MESSAGE_TITLE, "Goal is updated");
    private static final JSONPObject SUCCESS_GOAL_DELETE_MESSAGE = new JSONPObject(MESSAGE_TITLE, "Goal is deleted");
    private static final JSONPObject NOT_GOAL_EXIST_MESSAGE = new JSONPObject(MESSAGE_TITLE, "Goal isn't found");

    @Qualifier("goalServiceImpl")
    @Autowired
    private GoalService service;

    @ApiOperation(httpMethod = "POST",
            value = "Creates a new goal",
            nickname = "create")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Goal is created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Goal already exist")})
    @PostMapping("/create")
    public ResponseEntity<?> createGoal(String goalTitle) {
        if (goalTitle == null || goalTitle.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        if (service.createGoal(goalTitle)) {
            return ResponseEntity.status(201)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(SUCCESS_GOAL_CREATE_MESSAGE);
        }
        return ResponseEntity.status(409)
                .contentType(MediaType.APPLICATION_JSON)
                .body(FAIL_GOAL_CREATE_MESSAGE);
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of GoalDto",
            nickname = "goal")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List goals is returned")})
    @GetMapping
    public ResponseEntity<?> getAllGoals() {
        return ResponseEntity.of(service.getAll());
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Updates the goal",
            nickname = "update")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Goal is updated"),
            @ApiResponse(code = 412, message = "Goal isn't exist")})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable("id") Long id, @RequestBody GoalDto dto) {
        if (service.updateGoal(dto, id)) {
            return ResponseEntity.status(200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(SUCCESS_GOAL_UPDATE_MESSAGE);
        }
        return ResponseEntity.status(412)
                .contentType(MediaType.APPLICATION_JSON)
                .body(NOT_GOAL_EXIST_MESSAGE);
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Deletes the goal",
            nickname = "deletes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Goal is deleted"),
            @ApiResponse(code = 412, message = "Goal isn't exist")})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable("id") Long id, @RequestBody GoalDto dto) {
        if (service.deleteGoal(dto, id)) {
            return ResponseEntity.status(200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(SUCCESS_GOAL_DELETE_MESSAGE);
        }
        return ResponseEntity.status(412)
                .contentType(MediaType.APPLICATION_JSON)
                .body(NOT_GOAL_EXIST_MESSAGE);
    }
}
