package com.FitnessApp.controller;

import com.FitnessApp.model.User;
import com.FitnessApp.security.UserDetailsImpl;
import com.FitnessApp.services.UserProfileService;
import com.FitnessApp.services.UserService;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(description = "This controller is used for edit 'User parameters'", consumes = "application/json")
@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;

    private final UserService userService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get object 'UserParameters' by User_ID",
            nickname = "get UserParameters by user id",
            response = UserProfileDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/profiles/users/{idUser}", method = RequestMethod.GET,
            produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getUserParametersById(@PathVariable Long idUser) {
        User user = userService.getUserById(idUser);

        return ResponseEntity.ok(userProfileService.getProfileData(user));
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get object 'UserParameters' by authenticated user",
            nickname = "get UserParameters by Auth user",
            response = UserProfileDTO.class,
            authorizations = {@Authorization(value = "basicAuth")})

    @RequestMapping(path = "/profiles/auth-user", method = RequestMethod.GET,
            produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity getUserParametersByAuthUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return getUserParametersById(userDetails.getUser().getId());
    }


    @ApiOperation(httpMethod = "POST",
            value = "Resource to add object UserParameters by User ID",
            nickname = "add UserParameters by userId",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserParameters has been added",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of UserParameters is incorrect")})

    @RequestMapping(path = "/profiles/users/{idUser}", method = RequestMethod.POST)
    public ResponseEntity addUserProgramByUserID(@PathVariable Long idUser,
                                                 @RequestBody UserProfileDTO userProfileDTO) {
        User user = userService.getUserById(idUser);
        boolean res = userProfileService.changeProfileData(userProfileDTO, user);
        if (res) {
            return ResponseEntity.ok("Added");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @ApiOperation(httpMethod = "POST",
            value = "Resource to add object UserParameters by authenticated user",
            nickname = "add UserParameters by auth user",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserParameters has been added",
            response = String.class),
            @ApiResponse(code = 400, message = "Json object of UserParameters is incorrect")})

    @RequestMapping(path = "/profiles/auth-user", method = RequestMethod.POST)
    public ResponseEntity addUserProgramByAuthUser(Authentication authentication,
                                                 @RequestBody UserProfileDTO userProfileDTO) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return addUserProgramByUserID(userDetails.getUser().getId(), userProfileDTO);
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete new object UserParameters by ID",
            nickname = "delete UserParameters by ID",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Object UserParameters has been deleted",
            response = String.class)})

    @RequestMapping(path = "/profiles/{idParameters}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserProgram(@PathVariable Long idParameters) {
        userProfileService.deleteById(idParameters);
        return ResponseEntity.ok("Deleted");
    }

}
