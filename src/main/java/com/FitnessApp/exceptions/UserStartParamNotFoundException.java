package com.FitnessApp.exceptions;

public class UserStartParamNotFoundException extends RuntimeException {
    public UserStartParamNotFoundException(Integer id) {
        super("Could not find object by id = " + id);
    }

    public UserStartParamNotFoundException(String message) {
        super(message);
    }
}
