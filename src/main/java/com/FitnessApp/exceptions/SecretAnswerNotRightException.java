package com.FitnessApp.exceptions;

public class SecretAnswerNotRightException extends RuntimeException {
    private static final long serialVersionUID = 5348545846498021652L;

    public SecretAnswerNotRightException() {
        super();
    }

    public SecretAnswerNotRightException(String s) {
        super(s);
    }
}
