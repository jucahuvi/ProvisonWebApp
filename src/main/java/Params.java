import java.util.Properties;

public class Params {

    String oauthEndPoint;
    Properties properties;
    public Params(){
        ReadProperties readProperties = new ReadProperties();
        properties=readProperties.getProperties();

    }
    public void setOauthEndPoint (String oauthEndPoint) {
        oauthEndPoint = oauthEndPoint;
    }

    public String getOauthEndPoint(){
        return properties.getProperty("oauth_ednpoint");

    }
}

