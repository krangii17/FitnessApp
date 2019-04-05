package com.FitnessApp.exceptions;

public class EmailNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1870238276004291565L;
    private static final String USER_BY_EMAIL = "User by email:";
    private static final String NOT_FOUND = "\tnot found";

    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String s) {
        super(USER_BY_EMAIL + s + NOT_FOUND);
    }
}
