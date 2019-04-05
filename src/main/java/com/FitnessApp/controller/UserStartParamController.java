package com.FitnessApp.controller;

import com.FitnessApp.model.UserStartParam;
import com.FitnessApp.services.UserStartParamService;
import com.FitnessApp.utils.converters.UserStartParamConverter;
import com.FitnessApp.utils.dtos.UserStartParamDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserStartParamController {
    @Autowired
    private UserStartParamService service;

    private UserStartParamConverter converter = new UserStartParamConverter();

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get list of start parameters",
            nickname = "get list of start parameters",
            response = UserStartParamDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "basicAuth")})
    @Transactional(readOnly = true)
    @GetMapping(value = "/getAllStartParam")
    public ResponseEntity getAllUserStartParam() {
        Optional<List<UserStartParam>> userStartParam = service.getAllStartParameters();
        return ResponseEntity.of(Optional.of(converter.convertToUserParamListDTO(userStartParam)));
    }


    @ApiOperation(httpMethod = "POST",
            value = "Resource to add new object start parameter",
            nickname = "add start parameter",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Parameter has been added",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of UserStartParam is incorrect")})

    @PostMapping(value = "/addUserStartParameter")
    public ResponseEntity addUserStartParam(@RequestBody UserStartParamDTO userStartParamDTO) {
        UserStartParam startParam = converter.convertToEntity(userStartParamDTO);

        boolean result = service.saveUserStartParam(startParam);
        if (result) {
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to rename new object UserStartParam by ID",
            nickname = "rename UserStartParam by ID",
            authorizations = {@Authorization(value = "basicAuth")})

    @ApiResponses(value = {@ApiResponse(code = 400, message = "Json object of UserStartParam incorrect"),
            @ApiResponse(code = 200, message = "Object UserStartParam has been renamed", response = String.class)
    })
    @Transactional(readOnly = true)
    @PutMapping(value = "/changeInitWeight/{id}")
    public ResponseEntity changeInitWeight(@PathVariable Long id, @RequestBody UserStartParamDTO userStartParamDTO) {
        boolean result = service.updateStartParameter(id, converter.convertToEntity(userStartParamDTO));
        if (result) {
            return ResponseEntity.ok("initial weight was changed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete new object UserStartParam by ID",
            nickname = "delete UserStartParam by ID",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserStartParam has been deleted",
            response = String.class)})

    @DeleteMapping(value = "/startParam/{id}")
    public ResponseEntity deleteUserProgram(@PathVariable Long id) {
        service.deleteUserStartParamById(id);
        return ResponseEntity.ok("Deleted");
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get by ID object 'UserStartParam'",
            nickname = "get start parameter by id",
            response = UserStartParamDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping(value = "/start-parameter/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity getMusclesGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(converter
                .convertToDto(service.getStartParamById(id)));
    }
}
