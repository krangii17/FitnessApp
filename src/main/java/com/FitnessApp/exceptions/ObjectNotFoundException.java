package com.FitnessApp.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Integer id) {
        super("Could not find object by id = " + id);
    }

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
