package Services;

import GameXmlParser.Schema.Constraints;
import logic.GameManager;
import utils.ServletUtils;
import Game.Game;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.*;

/**
 * Created by idope on 10/22/2016.
 */
@WebServlet(name = "ConstraintsService", urlPatterns = {"/getConstraints"})
public class ConstraintsService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gameTitle = request.getParameter(GAMETITLE);
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        Game game =gameManager.getGame(gameTitle);
        Map<String,List<Constraints>> constraints= new HashMap<>();
        constraints.put(ROW,game.getRowConstraints());
        constraints.put(COLUMN,game.getColumnConstraints());
        response.setStatus(200);
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(constraints);
            out.print(jsonResponse);
            out.flush();
        }

    }

}
