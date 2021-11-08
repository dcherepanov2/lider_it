package com.cd.LiderIT_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;

@RestControllerAdvice
public class HttpExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        response.put("class error",e.getClass().getName());
        response.put("http status", String.valueOf(HttpStatus.NOT_FOUND));
        return response;
    }

    @ExceptionHandler({Exception.class,BookException.class,AuthorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, String> handleError(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("class error",ex.getClass().getName());
        response.put("http status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        return response;
    }

}
