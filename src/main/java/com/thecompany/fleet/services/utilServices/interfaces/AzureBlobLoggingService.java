package com.soprasteria.fleet.services.utilServices.interfaces;

public interface AzureBlobLoggingService {
    int writeToLoggingFile(String logText);
}
