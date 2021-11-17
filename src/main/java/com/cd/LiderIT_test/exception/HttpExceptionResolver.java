package com.cd.LiderIT_test.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler({Exception.class, ConversionNotSupportedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, String> handleError(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("class error",ex.getClass().getName());
        response.put("http status", "500");
        return response;
    }

    @ExceptionHandler({
             BookException.class,
             AuthorException.class,
             HttpMessageNotReadableException.class,
             MethodArgumentTypeMismatchException.class,
             HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> badRequest(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("class error",ex.getClass().getName());
        response.put("http status", "400");
        return response;
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public HashMap<String, String> methodNotAllowed(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", ex.getMessage());
        response.put("class error",ex.getClass().getName());
        response.put("http status", "405");
        return response;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static HashMap<String, String> bindException(BindException ex) throws Exception {
        HashMap<String, String> response = new HashMap<>();
        if (ex.getMessage().contains("birthday"))
            response.put("message", "Bad request. The birthday field must have the Date data type.");
        if (ex.getMessage().contains("availability"))
            response.put("message", "Bad request. The availability field must have the Boolean data type.");
        if (ex.getMessage().contains("number"))
            response.put("message", "Bad request. The number field must have the Integer data type.");
        if (ex.getMessage().contains("author"))
            response.put("message", "Bad request. The author field must have the Integer data type.");
        response.put("status", "fail");
        response.put("class error", ex.getClass().getName());
        response.put("http status", "400");
        return response;
    }

}

