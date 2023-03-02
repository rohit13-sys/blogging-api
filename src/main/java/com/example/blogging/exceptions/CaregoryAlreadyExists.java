package com.example.blogging.exceptions;

public class CaregoryAlreadyExists extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CaregoryAlreadyExists(String message) {
        super(message);
    }
}
