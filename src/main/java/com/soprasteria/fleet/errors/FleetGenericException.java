package com.soprasteria.fleet.errors;

import com.soprasteria.fleet.services.utilServices.AzureBlobLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

public class FleetGenericException extends RuntimeException {
    @Autowired
    private AzureBlobLoggingService azureLogService;

    public FleetGenericException() {
        super();
    }

    public FleetGenericException(String message) {
        azureLogService.writeToLoggingFile(message);
    }

}
