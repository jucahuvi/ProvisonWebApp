import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
@WebServlet(value="/getToken")
public class GetTokenServlet extends HttpServlet {


    Properties properties;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        properties = new ReadProperties().getProperties();


        GetToken getToken = new GetToken();
        String token = getToken.getToken();
        System.out.println("token=" + token );


        response.setContentType("text/html;charset=utf-8");
        // Establecer el código de estado
        response.setStatus(500);
        //response.sendError(500);
        // Obtener flujo de salida
        PrintWriter out = response.getWriter();
        out.println("<p><strong>Token:"+ token +"</strong></p>");
        out.close();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request,response);
    }

}
