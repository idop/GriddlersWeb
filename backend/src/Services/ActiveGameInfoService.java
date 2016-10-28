package Services;

import Game.Game;
import logic.GameManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.GAMETITLE;

/**
 * Created by ido on 24/10/2016.
 */
@WebServlet(name = "ActiveGameInfoService", urlPatterns = {"/getActiveGameInfo"})
public class ActiveGameInfoService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gameTitle = request.getParameter(GAMETITLE);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        Game game = gameManager.getGame(gameTitle);

        response.setStatus(200);
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(game.getActiveGameInfo());
            out.print(jsonResponse);
            out.flush();
        }

    }

}
