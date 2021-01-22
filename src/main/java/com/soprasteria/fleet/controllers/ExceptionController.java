package com.soprasteria.fleet.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public final ResponseEntity handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof MethodArgumentTypeMismatchException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            MethodArgumentTypeMismatchException except = (MethodArgumentTypeMismatchException) ex;
            System.out.println(status + " - Caused by wrong parameter");
            return itemNotFoundException(except, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            System.out.println(status);
            return handleExceptionInternal(ex, headers, status, request);
        }
    }

    protected ResponseEntity itemNotFoundException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, headers, status, request);
    }

    protected ResponseEntity handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(headers, status);
    }
}
