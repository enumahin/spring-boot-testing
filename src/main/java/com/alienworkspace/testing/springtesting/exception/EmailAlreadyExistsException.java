package com.alienworkspace.testing.springtesting.exception;


public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String entity,String email) {
        super(String.format("%s with email %s already exist.", entity, email));
    }
}
