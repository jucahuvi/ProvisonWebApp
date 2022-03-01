import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {



    public Properties properties ;


    public Properties getProperties () {
        properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");

            if (inputStream == null){
                System.out.println("Properties File not found");
            }

            properties.load(inputStream);


            return properties;
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
    }


}