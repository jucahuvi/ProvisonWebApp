import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/showmessagesraw")
public class ShowMessagesRawServlet extends HttpServlet {

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
            CloudQueue queue = queueClient.getQueueReference("webhookqueueraw");

            // Create the queue if it doesn't already exist.
            queue.createIfNotExists();


           // System.out.println("\nPeek at the messages in the queue...");

// Peek at messages in the queue
            String messageOut;
            int cont=0;

            queue.downloadAttributes();
            long queueLenght= queue.getApproximateMessageCount();

            System.out.println("\n\n\nLenght: " +queueLenght);

            for (long i=0; i<queueLenght;i++){
                messageOut = queue.retrieveMessage().getMessageContentAsString();
               // System.out.println(messageOut);

                sb.append(messageOut+"\n");

            }

            responseBody= sb.toString();
            PrintWriter writer = response.getWriter();
            response.setContentType("text/html; charset=utf-8");
            writer.println(escape(responseBody));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();

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
}
