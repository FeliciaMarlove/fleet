package com.soprasteria.fleet.errors;

public class FleetGenericException extends RuntimeException {
    public FleetGenericException(){
        super("An unwatched server inernal error occurred on the server");
        // TODO log (super. ...)
    }

    public FleetGenericException(String message) {
        super(message);
        // TODO log (super. ...)
    }

}
