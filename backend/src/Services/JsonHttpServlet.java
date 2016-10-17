package Services;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ido on 17/10/2016.
 */
public abstract class JsonHttpServlet extends HttpServlet {
    protected Gson gson = new Gson();

    protected class ErrorJsonResponse
    {
        public boolean error = true;
        public String message;
    }

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected class SuccessJsonResponse{
        public boolean success = true;
    }
}
