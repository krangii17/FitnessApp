package com.FitnessApp.controller;


import com.FitnessApp.model.User;
import com.FitnessApp.model.UserProgram;
import com.FitnessApp.security.UserDetailsImpl;
import com.FitnessApp.services.IEntityService;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.converters.UserProgramConverter;
import com.FitnessApp.utils.dtos.UserProgramDTO;
import com.FitnessApp.utils.validators.UserProgramValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@Api(description = "This controller is used for edit 'User programs'", consumes = "application/json")
@RestController
public class UserProgramController {
    private final UserProgramConverter userProgramConverter;

    private final IEntityService<UserProgram> userProgramService;

    private final UserService userService;

    @Autowired
    public UserProgramController(UserProgramConverter userProgramConverter,
                                 IEntityService<UserProgram> userProgramService,
                                 UserService userService) {

        this.userProgramConverter = userProgramConverter;
        this.userProgramService = userProgramService;
        this.userService = userService;
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get object 'UserProgram' by ID",
            nickname = "get UserProgram by id",
            response = UserProgramDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/user-programs/{idProgram}", method = RequestMethod.GET, produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getUserProgramById(@PathVariable Long idProgram) {
        return ResponseEntity.ok(userProgramConverter
                .convertToDto(userProgramService.getByID(idProgram)));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get List of objects 'UserProgram' by ID User ",
            nickname = "get list of UserProgram by idUser",
            response = UserProgramDTO.class,
            responseContainer="List",
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/user-programs/users/{idUser}", method = RequestMethod.GET, produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getUserProgramByUserId(@PathVariable Long idUser) {
        List<UserProgram> userProgramList = userProgramService.getByParameter("user.id",
                idUser.toString());
        return ResponseEntity.ok(userProgramList.stream()
                .map(userProgramConverter::convertToDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get list of objects 'UserProgram' by Authenticated user ",
            nickname = "get list of UserProgram by Auth user",
            response = UserProgramDTO.class,
            responseContainer="List",
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/user-programs/auth-user", method = RequestMethod.GET, produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getUserProgramByAuthUser(@ApiIgnore Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getUserProgramByUserId(userDetails.getUser().getId());
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get object 'UserProgram' with field 'Program_over' = false by Authenticated User",
            nickname = "get active UserProgram by Auth user",
            response = UserProgramDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/user-programs/active/auth-user", method = RequestMethod.GET, produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getActiveUserProgramByAuthUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getActiveUserProgramByUserId(userDetails.getUser().getId());
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get object 'UserProgram' with field 'Program_over' = false by ID User ",
            nickname = "get active UserProgram by idUser",
            response = UserProgramDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/user-programs/active/users/{idUser}", method = RequestMethod.GET,
            produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getActiveUserProgramByUserId(@PathVariable Long idUser) {

        List<UserProgram> userProgramList = userProgramService.getByParameter("user.id",
                idUser.toString());

        userProgramList = userProgramList
                .stream()
                .filter(userProgram -> !userProgram.isProgramOver())
                .collect(Collectors.toList());

        if (userProgramList.size() > 0) {
            return ResponseEntity.ok(userProgramConverter.convertToDto(userProgramList.get(0)));
        } else {
            return ResponseEntity.ok().build();
        }
    }


    @ApiOperation(httpMethod = "POST",
            value = "Resource to add authenticated user a new object UserProgram",
            nickname = "add auth user a new UserProgram",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserProgram has been added",
            response = String.class)})

    @RequestMapping(path = "/user-programs/auth-user", method = RequestMethod.POST)
    public ResponseEntity addUserProgramByAuthUser(Authentication authentication,
                                                   @RequestBody UserProgramDTO userProgramDTO) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return addUserProgram(userProgramDTO, userDetails.getUser().getId());
    }

    @ApiOperation(httpMethod = "POST",
            value = "Resource to add new object UserProgram by User ID",
            nickname = "add new UserProgram by userId",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserProgram has been added",
            response = String.class)})

    @RequestMapping(path = "/user-programs/users/{idUser}", method = RequestMethod.POST)
    public ResponseEntity addUserProgramByUserID(@PathVariable Long idUser, @RequestBody UserProgramDTO userProgramDTO) {

        return addUserProgram(userProgramDTO, idUser);
    }

    private ResponseEntity addUserProgram(UserProgramDTO userProgramDTO, Long idUser) {
        User user = userService.getUserById(idUser);
        boolean res = UserProgramValidator.validateDTO(userProgramDTO);
        if (res) {
            UserProgram userProgram = userProgramConverter.convertToEntity(userProgramDTO);
            userProgram.setUser(user);
            res = userProgramService.save(userProgram);
        }
        if (res) {
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @ApiOperation(httpMethod = "PUT",
            value = "Resource to update object UserProgram by ID",
            nickname = "rename UserProgram by ID",
            authorizations = {@Authorization(value = "basicAuth")})

    @ApiResponses(value = {@ApiResponse(code = 400, message = "Json object of UserProgram incorrect"),
            @ApiResponse(code = 200, message = "Object UserProgram has been renamed", response = String.class)
    })

    @RequestMapping(path = "/user-programs/{idProgram}", method = RequestMethod.PUT)
    public ResponseEntity renameUserProgram(@PathVariable Long idProgram,
                                            @RequestBody UserProgramDTO userProgramDTO) {

        boolean res = UserProgramValidator.validateDTO(userProgramDTO);
        if (res) {
            res = userProgramService.update(idProgram,
                    userProgramConverter.convertToEntity(userProgramDTO));
        }

        if (res) {
            return ResponseEntity.ok("Renamed");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete new object UserProgram by ID",
            nickname = "delete UserProgram by ID",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserProgram has been deleted",
            response = String.class)})

    @RequestMapping(path = "/user-programs/{idProgram}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserProgram(@PathVariable Long idProgram) {
        userProgramService.deleteById(idProgram);
        return ResponseEntity.ok("Deleted");
    }

}
