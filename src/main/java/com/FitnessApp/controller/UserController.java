package com.FitnessApp.controller;

import com.FitnessApp.model.Role;
import com.FitnessApp.model.State;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.converters.UserConverter;
import com.FitnessApp.utils.dtos.ControllerMessage;
import com.FitnessApp.utils.dtos.UserDto;
import com.FitnessApp.utils.validators.UserCandidateValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(consumes = "application/json")
@RestController
@RequestMapping("/admin/users")
public class UserController {
    private static final String MODEL = "user";
    private static final String USERS = "users";
    private static final String ROLES = "roles";
    private static final String STATES = "states";

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter converter;
    @Autowired
    private UserCandidateValidator validator;

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get user by email",
            nickname = "create",
            response = Optional.class,
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam int first, @RequestParam int max) {
        Map<String, List> responce = new HashMap<>();
        responce.put(USERS, converter.asListUserDto(userService.getAllUser(first, max)));
        responce.put(ROLES, Role.getAllRoles());
        responce.put(STATES, Arrays.asList(State.values()));
        if (responce.get(USERS).size() != 0) {
            return ResponseEntity.of(Optional.of(responce));
        }
        return ResponseEntity.status(404).body(ControllerMessage.STATUS_404.build(USERS));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get user by ID",
            nickname = "get user by id",
            response = Optional.class,
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        return ResponseEntity.of(Optional.of(converter.asUserDto(userService.getUserById(id))));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get user by email",
            nickname = "create",
            response = Optional.class,
            authorizations = {@Authorization(value = "basicAuth")})
    @GetMapping("/by_email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.of(Optional.of(converter.asUserDto(userService.getUserByEmail(email))));
    }

    @ApiOperation(httpMethod = "POST",
            value = "Resource to add a new user",
            nickname = "add user",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object has been added",
            response = String.class),
            @ApiResponse(code = 409, message = "User already exist"),
            @ApiResponse(code = 400, message = "Json object of user incorrect")})

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
        System.out.println(dto);
        if (validator.isCandidate(dto)) {
            if (userService.createUser(converter.asUser(dto))) {
                return ResponseEntity.status(201).body(ControllerMessage.STATUS_201.build(MODEL));
            } else {
                return ResponseEntity.status(409).body(ControllerMessage.STATUS_409.build(MODEL));
            }
        }
        return ResponseEntity.status(400).body(ControllerMessage.STATUS_400.build(MODEL));
    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to update object User",
            nickname = "update user",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object has been updated",
            response = String.class),
            @ApiResponse(code = 404, message = "Not found",
                    response = Map.class)})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto dto) {
        if (userService.update(id, converter.asUser(dto))) {
            return ResponseEntity.status(200).body(ControllerMessage.STATUS_200.build(MODEL));
        }
        return ResponseEntity.status(404).body(ControllerMessage.STATUS_404.build(MODEL));
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete the object User",
            nickname = "delete user",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object has been deleted",
            response = Optional.class)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @RequestBody UserDto dto) {
        if (userService.delete(id, converter.asUser(dto))) {
            return ResponseEntity.status(200).body(MODEL);
        }
        return ResponseEntity.status(404).body(ControllerMessage.STATUS_404.build(MODEL));
    }
}
