package Services;

import Game.Game;
import Game.GameMove;
import Game.Player.PlayerTurn;
import Game.Player.PlayerType;
import com.google.gson.JsonSyntaxException;
import logic.GameManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 * Created by ido on 29/10/2016.
 */
@WebServlet(name = "DoPlayerTurnService", urlPatterns = {"/doPlayerTurn"})
public class DoPlayerTurnService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gameTitle = request.getParameter(GAMETITLE);
        int playerId = Integer.parseInt(request.getParameter(PLAYER_ID));
        PlayerType playerType = PlayerType.valueOf(request.getParameter(PLAYERTPYE));
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        Game game = gameManager.getGame(gameTitle);
        BufferedReader br = request.getReader();
        if(playerType == PlayerType.Human) {
            try {
                List<GameMove> playTurn = Arrays.stream(gson.fromJson(br, GameMove[].class)).collect(Collectors.toList());
                game.doPlayerTurn(new PlayerTurn(playTurn));
                response.setStatus(200);
            } catch (JsonSyntaxException JsonIOException) {
                response.setStatus(400);
            }
        } else{
            game.doPlayerTurn(new PlayerTurn());
        }
    }

}
