package com.example.Security.Salon.Exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String Message) {
        super(Message);
    }
}
