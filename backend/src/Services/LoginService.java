package Services;

import logic.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.*;

/**
 * Created by ido on 11/10/2016.
 */
@WebServlet(name = "LoginService", urlPatterns = {"/login"})
public class LoginService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if (usernameFromSession == null) {
            String usernameFromParameter = request.getParameter(USERNAME);
            usernameFromParameter = usernameFromParameter.trim();
            if (userManager.isUserExists(usernameFromParameter)) {
                response.setStatus(401);
                try (PrintWriter out = response.getWriter()) {
                    sendErrorJsonResponse(out,"Username " + usernameFromParameter + " already exists. Please enter a different username.");
                }
            } else {
                userManager.addUser(usernameFromParameter, request.getParameter(PLAYERTPYE));
                request.getSession(true).setAttribute(USERNAME, usernameFromParameter);
                response.setStatus(200);
                try (PrintWriter out = response.getWriter()) {
                    SuccessLoginJson success = new SuccessLoginJson();
                    success.username=usernameFromParameter;
                    String jsonResponse = gson.toJson(success);
                    out.print(jsonResponse);
                    out.flush();
                }
            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Login Service";
    }// </editor-fold>

    private class SuccessLoginJson extends SuccessJsonResponse
    {
        public String username;
    }
}