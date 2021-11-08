package com.cd.LiderIT_test.exception;

public class AuthorException extends Exception{

    private final String message;

    public AuthorException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
