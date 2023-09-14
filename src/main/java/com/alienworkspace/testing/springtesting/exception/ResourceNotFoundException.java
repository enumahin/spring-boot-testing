package com.alienworkspace.testing.springtesting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String entity, String field, String value) {
        super(String.format("%s with %s of %s not found", entity, field, value));
    }
}
