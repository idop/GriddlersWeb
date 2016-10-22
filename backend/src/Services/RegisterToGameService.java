package Services;

import logic.GameManager;
import logic.ServiceException;
import logic.UserManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.GAMETITLE;
import static constants.Constants.USERNAME;

/**
 * Created by amitaihandler on 10/19/16.
 */
@WebServlet(name = "RegisterToGameService", urlPatterns = {"/registerToGame"})
public class RegisterToGameService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter(USERNAME);
        String gameTitle = request.getParameter(GAMETITLE);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        try {
            gameManager.registerUserToAGame(gameTitle, userManager.getUser(username));
        }
        catch (ServiceException e){
            response.setStatus(400);
            try (PrintWriter out = response.getWriter()) {
                sendErrorJsonResponse(out,e.getMessage());
            }
        }

}
}
