package com.ozzy.loginapi.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String msn) {
        super(msn);
    }
}
