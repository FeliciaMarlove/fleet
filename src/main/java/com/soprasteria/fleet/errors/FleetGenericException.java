package com.soprasteria.fleet.errors;

/**
 * Represent an unqualified exception
 */
public class FleetGenericException extends RuntimeException {

    public FleetGenericException() {

    }

    public FleetGenericException(String message) {
        super(message);
    }

}
