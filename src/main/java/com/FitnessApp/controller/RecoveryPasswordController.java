package com.FitnessApp.controller;

import com.FitnessApp.utils.converters.UserRecoveryConverter;
import com.FitnessApp.utils.dtos.UserRecoveryDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Api(description = "This controller is used for recovery password", consumes = "application/json")
@RestController
public class RecoveryPasswordController {

    @Autowired
    UserRecoveryConverter converter;

    @Autowired
    BCryptPasswordEncoder encoder;

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a secret question",
            nickname = "getSecret")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Secret key was returned"),
            @ApiResponse(code = 404, message = "Page not found"),
            @ApiResponse(code = 409, message = "Email not found")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "User's email", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping(value = "/getSecret")
    public ResponseEntity recovery(String email) {
        UserRecoveryDTO dto = converter.getRecoveryEmail(email);
        if (dto.getSecretQuestion()!=null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @ApiOperation(httpMethod = "POST",
            value = "Resource to change password",
            nickname = "changePassword")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Password has been changed"),
            @ApiResponse(code = 404, message = "Page not found"),
            @ApiResponse(code = 409, message = "Secret Answer or password not right")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "User's email", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "answer", value = "User's answer", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "User's password", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/changePassword")
    public ResponseEntity changePassword(String secretAnswer,
                                         String password,
                                         String email) {
        if (converter.changePasswordBySecretAnswer(email, secretAnswer, encoder.encode(password))) {
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(409).build();
    }

}
