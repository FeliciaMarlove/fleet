package com.soprasteria.fleet.errors;

import com.soprasteria.fleet.services.utilServices.AzureBlobLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

public class FleetItemNotFoundException extends FleetGenericException {
    @Autowired
    private AzureBlobLoggingService azureLogService;

    public FleetItemNotFoundException() {
        super();
    }

    public FleetItemNotFoundException(String message) {
        azureLogService.writeToLoggingFile(message);
    }
}
