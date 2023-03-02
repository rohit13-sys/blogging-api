package com.example.blogging.exceptions;

public class CategoryNotFound extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CategoryNotFound(String message) {
        super(message);
    }
}
