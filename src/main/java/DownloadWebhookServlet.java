import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/downloadwebhook")
public class DownloadWebhookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String storageConnectionString =
                "DefaultEndpointsProtocol=https;AccountName=webhookstorageaccount2;AccountKey=AW/EtntAY3zlvON79O//C7Tr/6YbP/zU8bp0MJ/KNTCySiKw1c+x0OIQTDFKljBy4cuS+rYAxU8z+AStQhr+hQ==;EndpointSuffix=core.windows.net";
        StringBuilder sb = new StringBuilder();

        String responseBody;



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


            System.out.println("\nPeek at the messages in the queue...");

// Peek at messages in the queue
            String messageOut;
            int cont=0;

            queue.downloadAttributes();
            long queueLenght= queue.getApproximateMessageCount();

            System.out.println("\n\n\nLenght: " +queueLenght);

            for (long i=0; i<queueLenght;i++){
                messageOut = queue.retrieveMessage().getMessageContentAsString();
                System.out.println(messageOut);
                String top,botton;

                sb.append(messageOut+"\n");


            }

            writeToFile(sb.toString(),response);

        } catch (Exception e) {
            // Output the stack trace.
            System.out.println("Error writing queue to file....");

        }
    }

public void writeToFile (String webhookPayload,HttpServletResponse response){

        try {
            File webhookFile;
            // Write text to the file
            webhookFile = new File("webhook.txt");
            FileWriter writer = new FileWriter(webhookFile, false);
            writer.write(webhookPayload);
            writer.flush();
            writer.close();
            System.out.println("Webhook write to file....");



            String connectString = "DefaultEndpointsProtocol=https;AccountName=webhookstorageaccount2;AccountKey=AW/EtntAY3zlvON79O//C7Tr/6YbP/zU8bp0MJ/KNTCySiKw1c+x0OIQTDFKljBy4cuS+rYAxU8z+AStQhr+hQ==;EndpointSuffix=core.windows.net";



            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("webhookblob");

            // Define the path to a local file.
            final String filePath = "webhook.txt";

            CloudBlockBlob blob = container.getBlockBlobReference(filePath);
            File source = new File(filePath);
            blob.deleteIfExists();
            blob.upload(new FileInputStream(source),source.length());

              //  System.out.println("File upload to blob container");
                String fileNameToDownload = "webhook" + (LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)).toString() + ".txt";
            blob.downloadToFile(fileNameToDownload);
            download(fileNameToDownload,response);

        }catch (Exception e ){
            System.out.println("Failed to upload file....");
        }
}




public static String escape(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for( char c : s.toCharArray() ) {
            if( c == ' ' ) {
                if( previousWasASpace ) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch(c) {
                case '<': builder.append("&lt;"); break;
                case '>': builder.append("&gt;"); break;
                case '&': builder.append("&amp;"); break;
                case '"': builder.append("&quot;"); break;
                case '\n': builder.append("<br>"); break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;
                default:
                    if( c < 128 ) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int)c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    public void download (String filename, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();



        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");

        FileInputStream fileInputStream = new FileInputStream(filename);

        int i;
        while ((i=fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
        out.flush();
        out.close();
    }
}

