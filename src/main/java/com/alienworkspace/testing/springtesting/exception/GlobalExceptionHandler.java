package com.alienworkspace.testing.springtesting.exception;

import com.alienworkspace.testing.springtesting.model.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetail> emailNotFoundExceptionHandler(
            EmailAlreadyExistsException exception,
            WebRequest request
    ){
        return new ResponseEntity<>(
                new ExceptionDetail(
                        LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false),
                        "ALREADY_EXIST"
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetail> resourceNotFoundExceptionHandler(
            ResourceNotFoundException exception,
            WebRequest request
    ){
        return new ResponseEntity<>(
                new ExceptionDetail(
                        LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false),
                        "NOT_FOUND"
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
