
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;


public class SetMessageQueue {

    public void setMessage(String messageToQueue) {

// Define the connection-string with your values.
        final String storageConnectionString =
                "DefaultEndpointsProtocol=https;AccountName=webhookstorageaccount2;AccountKey=AW/EtntAY3zlvON79O//C7Tr/6YbP/zU8bp0MJ/KNTCySiKw1c+x0OIQTDFKljBy4cuS+rYAxU8z+AStQhr+hQ==;EndpointSuffix=core.windows.net";
        try {

            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("webhookqueue");

            // Create the queue if it doesn't already exist.
            queue.createIfNotExists();

            // Create a message and add it to the queue.
            CloudQueueMessage message = new CloudQueueMessage(messageToQueue);

            queue.addMessage(message,-1,0,null,null);

        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public void setMessageRaw(String messageToQueueRaw) {

// Define the connection-string with your values.
        final String storageConnectionStringRaw =
                "DefaultEndpointsProtocol=https;AccountName=webhookstorageaccount2;AccountKey=AW/EtntAY3zlvON79O//C7Tr/6YbP/zU8bp0MJ/KNTCySiKw1c+x0OIQTDFKljBy4cuS+rYAxU8z+AStQhr+hQ==;EndpointSuffix=core.windows.net";
        try {

            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionStringRaw);

            // Create the queue client.
            CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

            // Retrieve a reference to a queue.
            CloudQueue queue = queueClient.getQueueReference("webhookqueueraw");

            // Create the queue if it doesn't already exist.
            queue.createIfNotExists();

            // Create a message and add it to the queue.
            CloudQueueMessage message = new CloudQueueMessage(messageToQueueRaw);

            queue.addMessage(message,-1,0,null,null);

        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
    }
