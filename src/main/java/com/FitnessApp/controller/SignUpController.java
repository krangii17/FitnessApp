package com.FitnessApp.controller;

import com.FitnessApp.services.impl.SignUpService;
import com.FitnessApp.utils.dtos.ControllerMessage;
import com.FitnessApp.utils.dtos.UserDto;
import com.FitnessApp.utils.validators.UserCandidateValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "This controller is used for sign up new user", consumes = "application/json")
@RestController
public class SignUpController {
    private static final String MODEL = "user";

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserCandidateValidator userCandidateValidator;

    @Autowired
    BCryptPasswordEncoder encoder;

    @ApiOperation(httpMethod = "POST",
            value = "Resource to sign up new user",
            nickname = "sign_up")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "user: is created"),
            @ApiResponse(code = 409, message = "user: already exist")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "User's nickname", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "User's email", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "User's password", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secretQuestion", value = "User's secret question", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secretAnswer", value = "User's answer", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/sign_up")
    public ResponseEntity<?> signUp(String username,
                                    String email,
                                    String password,
                                    String secretQuestion,
                                    String secretAnswer) {
        UserDto userDto = new UserDto(username, email, password, secretQuestion, secretAnswer);
        if (userCandidateValidator.isCandidate(userDto)) {
            if (signUpService.creteUser(userDto)) {
                return ResponseEntity.status(201)
                        .body(ControllerMessage.STATUS_201.build(MODEL));
            } else {
                return ResponseEntity.status(409)
                        .body(ControllerMessage.STATUS_409.build(MODEL));
            }
        }
        return ResponseEntity.status(400)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ControllerMessage.STATUS_400.build(MODEL));
    }
}
