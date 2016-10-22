package Services;

import Game.Game;
import GameXmlParser.GameBoardXmlParser;
import GameXmlParser.GameDefinitionsXmlParserException;
import logic.GameManager;
import logic.UserManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by amitaihandler on 10/19/16.
 */
@WebServlet(name = "UploadGameService", urlPatterns = {"/uploadGame"})
@MultipartConfig
public class UploadGameService extends JsonHttpServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");
        try {
            GameBoardXmlParser gameBoardXmlParser = new GameBoardXmlParser(filePart.getInputStream());
            Game game = new Game(gameBoardXmlParser);
            GameManager gameManager = ServletUtils.getGameManager(getServletContext());

            gameManager.addGame(game);
        }
        catch (GameDefinitionsXmlParserException e) {

        }

    }
}