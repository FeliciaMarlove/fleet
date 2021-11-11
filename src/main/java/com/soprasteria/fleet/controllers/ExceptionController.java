package com.soprasteria.fleet.controllers;

import com.soprasteria.fleet.errors.FleetGenericException;
import com.soprasteria.fleet.errors.FleetItemNotFoundException;
import com.soprasteria.fleet.services.utilServices.AzureBlobLoggingServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

/**
 * Catch errors in the code caused by a request
 * Return a controlled message with the cause of the error to the client
 */
@ControllerAdvice
public final class ExceptionController {
    private final AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl;

    public ExceptionController(AzureBlobLoggingServiceImpl azureBlobLoggingServiceImpl) {
        this.azureBlobLoggingServiceImpl = azureBlobLoggingServiceImpl;
    }

    @ExceptionHandler({NoSuchElementException.class})
    protected ResponseEntity noSuchElement(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handException(ex, status);
    }

    @ExceptionHandler({FleetItemNotFoundException.class})
    protected ResponseEntity fleetItemNotFound(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handException(ex, status);
    }

    @ExceptionHandler({FleetGenericException.class})
    protected ResponseEntity fleetGeneric(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handException(ex, status);
    }

    @ExceptionHandler({NullPointerException.class})
    protected ResponseEntity nullPointerException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handException(ex, status);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public final ResponseEntity MethodArgumentTypeMismatch(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handException(ex, status);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public final ResponseEntity DataIntegrityViolation(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handException(ex, status);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public final ResponseEntity HttpRequestMethodNotSupported(Exception ex) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return handException(ex, status);
    }

    /**
     * Handle exception to return a response entity
     * Write it in the log
     * @param ex the caught exception
     * @param status the http status
     * @return the exception message if the cause if a FleetGenericException and it has a message, the generic http status response otherwise
     */
    @ExceptionHandler
    protected ResponseEntity handException(Exception ex, HttpStatus status) {
        azureBlobLoggingServiceImpl.writeToLoggingFile(status.value() + " " + status.getReasonPhrase()
                + "| Error caught in Exception controller: " +  " " + ex.getMessage());
        return new ResponseEntity<>(ex.getClass().equals(FleetGenericException.class) && (ex.getMessage() != null) ? ex.getMessage() : status.getReasonPhrase(), status);
    }
}
