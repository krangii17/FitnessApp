package com.FitnessApp.exceptions;

public class UserNotFoundExeption extends RuntimeException {
    private static final long serialVersionUID = -7626485318292455436L;
    private static final String USER_NOT_FOUND = ", user not found";

    public UserNotFoundExeption() {
        super();
    }

    public UserNotFoundExeption(String message) {
        super(message + USER_NOT_FOUND);
    }
}
