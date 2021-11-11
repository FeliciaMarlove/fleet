package com.soprasteria.fleet.errors;

/**
 * Represent an exception caused by an item not found
 */
public final class FleetItemNotFoundException extends FleetGenericException {

    public FleetItemNotFoundException() {

    }

    public FleetItemNotFoundException(String message) {
        super(message);
    }
}
