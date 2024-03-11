package com.swiggy.authenticator.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class GenericHttpResponse {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;

    private GenericHttpResponse(HttpStatus status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
        this.statusCode = status.value();
    }

    public static ResponseEntity<GenericHttpResponse> create(HttpStatus status, String message, Object data){
        return new ResponseEntity<>(new GenericHttpResponse(status, message, data), status);
    }
}
