package com.example.blogging.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
