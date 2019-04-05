package com.FitnessApp.exceptions;

public class IllegalWeightException extends RuntimeException {
    private static final long serialVersionUID = 622641970965481020L;

    public IllegalWeightException() {
        super();
    }

    public IllegalWeightException(String s) {
        super(s);
    }
}
