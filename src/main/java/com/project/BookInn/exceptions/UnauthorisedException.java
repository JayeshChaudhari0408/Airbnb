package com.project.BookInn.exceptions;

public class UnauthorisedException extends RuntimeException{

    public UnauthorisedException(String message) {
        super(message);
    }
}
