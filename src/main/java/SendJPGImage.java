import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;


@WebServlet(value="/sendJPGImage")
public class SendJPGImage extends javax.servlet.http.HttpServlet {

    Properties properties;
    String oauth_endpoint;


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        properties = new ReadProperties().getProperties();
        oauth_endpoint = properties.getProperty("oauth_endpoint");
        String message_endpoint = properties.getProperty("message_endpoint");
        String token = new GetToken().getToken();


        String responseJSON = this.sendMessage(token, request.getParameter("msisdn"), request.getParameter("text"), message_endpoint);

        PrintWriter writer = response.getWriter();

        // build HTML code
        String htmlRespone = "<html>";

        htmlRespone += responseJSON + "</h2>";
        htmlRespone += "</html>";
        writer.println(htmlRespone);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public String sendMessage(String token, String msisdn, String text, String endpoint) {


        try {

            URL url = new URL(endpoint);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);

            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            httpsURLConnection.setRequestProperty("Authorization", "Bearer " + token);


            Gson gson = new Gson();
            String fileName = "JPGImagePayload";

            File file = getFileFromResource(fileName);
            JsonReader reader = new JsonReader(new FileReader(file));

            RCSMessageJPGImage payload = gson.fromJson(reader, RCSMessageJPGImage.class);
            
            System.out.println(payload.toString());


            SetMessageQueue setMessageQueue = new SetMessageQueue();
            setMessageQueue.setMessage(payload.toString());

            OutputStream os = httpsURLConnection.getOutputStream();
            byte[] input = payload.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

            os.flush();
            os.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String output;
            StringBuilder sb = new StringBuilder();

            while ((output = in.readLine()) != null) {
                sb.append(output);
            }
            // System.out.println(sb.toString());

            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
            return "Failed sending message....";

        }

    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException, URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }
}

