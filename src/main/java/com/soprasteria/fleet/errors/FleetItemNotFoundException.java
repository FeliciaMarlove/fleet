package com.soprasteria.fleet.errors;

public class FleetItemNotFoundException extends FleetGenericException {
    public FleetItemNotFoundException() {
        super("Unwatched not found item");
        // TODO log (super. ...)
    }

    public FleetItemNotFoundException(String message) {
        super(message);
        // TODO log (super. ...)
    }
}
