package com.FitnessApp.controller;

import com.FitnessApp.exceptions.EmailNotFoundException;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.exceptions.UserNotFoundExeption;
import com.FitnessApp.utils.dtos.ControllerMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ObjectNotFoundAdviceController {
    @ResponseBody
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String objectNotFoundHandler(ObjectNotFoundException ex) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundExeption ex) {
        return ControllerMessage.STATUS_404.name();
    }

    @ResponseBody
    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String emailNotFoundHandler(EmailNotFoundException ex) {
        return ex.getMessage();
    }
}

