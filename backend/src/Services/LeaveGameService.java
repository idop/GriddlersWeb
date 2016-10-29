package Services;

import logic.GameManager;
import logic.ServiceException;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.GAMETITLE;
import static constants.Constants.PLAYER_ID;

/**
 * Created by ido on 29/10/2016.
 */@WebServlet(name = "LeaveGameService", urlPatterns = {"/leaveGame"})
public class LeaveGameService  extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gameTitle = request.getParameter(GAMETITLE);
        int playerId = Integer.parseInt(request.getParameter(PLAYER_ID));
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        try {
            gameManager.unregisterPlayer(gameTitle,playerId);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.setStatus(400);
        }
        response.setStatus(200);
    }

}
