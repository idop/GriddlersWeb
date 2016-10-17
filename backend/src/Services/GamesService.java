package Services;

import logic.GameManager;
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
@WebServlet(name = "GamesService", urlPatterns = {"/games"})
public class GamesService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(APPLICATION_JSON);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(gameManager.getGamesList());
            out.print(jsonResponse);
            out.flush();
        }
    }

}
