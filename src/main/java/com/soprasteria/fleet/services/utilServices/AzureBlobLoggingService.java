package com.soprasteria.fleet.services.utilServices;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.AppendBlobClient;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
public class AzureBlobLoggingService {
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=tfefleetstorage;AccountKey=WxW7nXPlyPLNgkLbHvmqrTVDyhqZc4qZmcikoG6rHfvWBcN5F9eP95bIY46FimZuwVWpbzBdI+/0aCVJw1wsEw==;EndpointSuffix=core.windows.net";

    // Create a BlobServiceClient object which will be used to create a container client
    private static final BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

    // Retrieve blob container
    private static final BlobContainerClient bcc = blobServiceClient.getBlobContainerClient("logs");

    private int ATTEMPTS = 0;

    public int writeToLoggingFile(String newLog) {
        newLog = "\n" + LocalDateTime.now() + ":::" + newLog;
        try {
            ATTEMPTS++;
            InputStream is = new ByteArrayInputStream(newLog.getBytes());
            AppendBlobClient blobClient = bcc.getBlobClient("logs.txt").getAppendBlobClient();
            if (!blobClient.exists()) {
                blobClient.create();
            }
            return blobClient.appendBlockWithResponse(is, newLog.length(), null, null, null, null).getStatusCode();

        } catch (Exception e) {
            if (ATTEMPTS < 2) {
                writeToLoggingFile(newLog);
            }
            return 500;
        }
    }
}
