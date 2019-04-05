package com.FitnessApp.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "This controller is used for user login", consumes = "application/json")
@RestController
public class LoginController {

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a user login",
            nickname = "login",
            authorizations = {@Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User login was returned"),
            @ApiResponse(code = 404, message = "Page not found"),
            @ApiResponse(code = 409, message = "User with this login not found")})

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> logIn() {
        return ResponseEntity.status(200).body(new JSONPObject("message", "login success!!"));
    }
}
