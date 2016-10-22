package Services;

import logic.UserManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.APPLICATION_JSON;

/**
 * Created by ido on 17/10/2016.
 */

@WebServlet(name = "PlayersService", urlPatterns = {"/players"})
public class PlayersService extends JsonHttpServlet {


    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(userManager.getUsers());
            out.print(jsonResponse);
            out.flush();
        }
    }
}
