package com.example.nada.Exceptions.CustomException;

import com.example.nada.Exceptions.EntitiesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException  {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse>HandleEntitiesNotFoundException(EntitiesNotFoundException e){
        HttpStatus status=HttpStatus.BAD_REQUEST;
        CustomErrorResponse error= new CustomErrorResponse();
        error.setSms(e.getMessage());
        error.setStatus(status.toString());

        return ResponseEntity.status(status).body(error);
    }
}
