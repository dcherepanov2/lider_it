package com.cd.LiderIT_test.exception;

public class BookException extends Exception {

    private final String message;

    public BookException(String error) {
        this.message = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
