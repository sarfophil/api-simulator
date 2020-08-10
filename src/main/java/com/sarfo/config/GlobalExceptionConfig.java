package com.sarfo.config;

import com.sarfo.dto.ExceptionResponse;
import com.sarfo.exception.AccountExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionConfig {
    @ExceptionHandler({NoSuchElementException.class, AccountExistException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleNoSuchException(final Throwable throwable){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(throwable.getMessage());
        exceptionResponse.setStatus(HttpStatus.OK.getReasonPhrase());
        if(throwable instanceof NoSuchElementException){
            exceptionResponse.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exceptionResponse,HttpStatus.OK);
    }
}
