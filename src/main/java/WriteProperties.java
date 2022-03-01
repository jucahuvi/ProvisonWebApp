import java.io.*;
import java.util.Properties;

public class WriteProperties {


    Properties properties;
    OutputStream output;

    public WriteProperties () {
        properties = new Properties();
        try  {
            output = new FileOutputStream("config.properties");
        }catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void setProperties (String key, String value) {

        properties = new Properties();
        properties.setProperty(key, value);

    }

            public void loadProperties () {
               try{
                   properties.store(output,"");
               }catch (Exception e) {
                   e.printStackTrace();
                }

            }
    }


