import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;
import com.google.gson.*;

public class GetToken {


    public String getToken() {

        ReadProperties readProperties = new ReadProperties();

        String token=null;
        Properties systemProperties = System.getProperties();

        System.out.println(readProperties.getProperties().getProperty("oauth_endpoint"));
        try{
            URL url = new URL(readProperties.getProperties().getProperty("oauth_endpoint"));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            String basic = readProperties.getProperties().getProperty("clinet_id")+ ":" + readProperties.getProperties().getProperty("client_secret");
            //String basicBase64 = Base64.getEncoder().encodeToString(basic.getBytes());

            //System.out.println(basicBase64);

            String payload = "grant_type=client_credentials&scope=botmessage&client_id=SkxGU0gxNjA1MTE1ODQzQGJvdHBsYXRmb3JtLnRlbGVmb25pY2EuZXM%3D&client_secret=Z1ZMMlNrMHlRSUNZcEl6cmxTOXFQRk9mcHJkQVM1VjQ%3D";
            OutputStream os = httpsURLConnection.getOutputStream();
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.close();
            int responseCode = httpsURLConnection.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String output;
                StringBuilder sb = new StringBuilder();

                while ((output = in.readLine()) != null) {
                    sb.append(output);
                }
                //System.out.println(sb.toString());
                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(sb.toString(), TokenResponse.class);

                //System.out.println(tokenResponse.getAccessToken());
                token= tokenResponse.getAccessToken();
            } else {
                token=null;
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return token;
    }
    }

