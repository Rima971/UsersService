package com.swiggy.authenticator.controllers;

import com.swiggy.authenticator.dtos.GenericHttpResponse;
import com.swiggy.authenticator.exceptions.CustomerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.authenticator.constants.ErrorMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericHttpResponse> invalidRequest(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, INVALID_REQUEST, errors);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<GenericHttpResponse> invalidCustomerId(CustomerNotFoundException e, HttpServletRequest request) {
        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, CUSTOMER_NOT_FOUND, null);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericHttpResponse> duplicateRestaurantName(Exception e){
        return GenericHttpResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }

}