package com.thecompany.fleet.services.utilServices;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.AppendBlobClient;
import com.thecompany.fleet.services.utilServices.interfaces.AzureBlobLoggingService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
public final class AzureBlobLoggingServiceImpl implements AzureBlobLoggingService {
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=<ACCOUNT_NAME>;AccountKey=<ACCOUNT_KEY>;EndpointSuffix=core.windows.net";

    // Create a BlobServiceClient object which will be used to create a container client
    private static final BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

    // Retrieve blob container
    private static final BlobContainerClient bcc = blobServiceClient.getBlobContainerClient("logs");

    private int attempts = 0;

    /**
     * Write to logs.txt on Azure storage account
     * Append text in existing file
     * Try once again in case of failure
     * @param newLog The text to write in the logs
     * @return the code, starting with 2** if sucess (201), or 5** in case of failure
     */
    @Override
    public int writeToLoggingFile(String newLog) {
        newLog = "\nSERVER SIDE:::" + LocalDateTime.now() + ":::" + newLog;
        try {
            attempts++;
            InputStream is = new ByteArrayInputStream(newLog.getBytes());
            AppendBlobClient blobClient = bcc.getBlobClient("logs.txt").getAppendBlobClient();
            if (!blobClient.exists()) {
                blobClient.create();
            }
            return blobClient.appendBlockWithResponse(is, newLog.length(), null, null, null, null).getStatusCode();

        } catch (Exception e) {
            if (attempts < 2) {
                writeToLoggingFile(newLog);
            }
            return 500;
        }
    }
}
