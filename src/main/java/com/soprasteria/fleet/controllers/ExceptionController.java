package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.services.utilServices.AzureBlobLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @Autowired
    private AzureBlobLoggingService azureBlobLoggingService;

    @ExceptionHandler({NoSuchElementException.class})
    protected ResponseEntity noSuchElement(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, headers, status, request);
    }

    @ExceptionHandler({FleetItemNotFoundException.class})
    protected ResponseEntity fleetItemNotFound(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, headers, status, request);
    }

    @ExceptionHandler({FleetGenericException.class})
    protected ResponseEntity fleetGeneric(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, headers, status, request);
    }

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity nullPointerException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, headers, status, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public final ResponseEntity MethodArgumentTypeMismatch(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, headers, status, request);
    }

    @ExceptionHandler
    protected ResponseEntity handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        azureBlobLoggingService.writeToLoggingFile("Error caught in Exception controller " + ex.getLocalizedMessage());
        return new ResponseEntity<>(headers, status);
    }
}
