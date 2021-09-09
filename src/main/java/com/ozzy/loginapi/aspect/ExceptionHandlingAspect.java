package com.ozzy.loginapi.aspect;

import com.ozzy.loginapi.exceptions.DataNotCreatedException;
import com.ozzy.loginapi.exceptions.DataNotFoundException;
import com.ozzy.loginapi.exceptions.DataNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingAspect {
    
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDataNotFoundException(){}
    
    @ExceptionHandler(DataNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleDataNotValidException(){}
    
    @ExceptionHandler(DataNotCreatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleDataNotCreatedException(){}
}
