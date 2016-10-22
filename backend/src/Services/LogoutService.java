package Services;

import logic.UserManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.USERNAME;

/**
 * Created by idope on 10/22/2016.
 */
@WebServlet(name = "LogoutService", urlPatterns = {"/logout"})
public class LogoutService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        userManager.removeUser(usernameFromParameter);
    }
}
