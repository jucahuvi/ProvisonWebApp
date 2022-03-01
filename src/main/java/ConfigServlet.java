

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/configServlet")
public class ConfigServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // read form fields
        String botId = request.getParameter("botId");
        String clientId = request.getParameter("clientId");
        String clientSecret = request.getParameter("clientSecret");



        // get response writer
        PrintWriter writer = response.getWriter();

        // build HTML code
        String htmlRespone = "<html>";
        htmlRespone += "<h2>Bot ID: " + botId + "<br/>";
        htmlRespone += "Client ID: " + clientId + "</h2>";
        htmlRespone += "Client Secret: " + clientSecret+ "</h2>";
        htmlRespone += "</html>";



        // return response
        writer.println(htmlRespone);

        WriteProperties writeProperties = new WriteProperties();
        writeProperties.setProperties("client_id",botId);
        writeProperties.setProperties("client_id",clientId);
        writeProperties.setProperties("client_id",clientSecret);

        writeProperties.loadProperties();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request,response);
    }
}