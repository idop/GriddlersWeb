package Services;

import GameXmlParser.Schema.Constraint;
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
        Game game = gameManager.getGame(gameTitle);
        ;
        Map<String, Constraint[][]> constraints = new HashMap<>();

        constraints.put(ROW, createRowConstraintMatrix(game.getRowConstraints(), game.getMaxRowConstraints()));
        constraints.put(COLUMN, createColumnConstraintMatrix(game.getColumnConstraints(), game.getMaxColumnConstraints()));
        response.setStatus(200);
        try (PrintWriter out = response.getWriter()) {
            String jsonResponse = gson.toJson(constraints);
            out.print(jsonResponse);
            out.flush();
        }

    }

    private Constraint[][] createRowConstraintMatrix(List<Constraints> constraints, int maxNumberOfConstraints) {
        Constraint[][] res = new Constraint[constraints.size()][maxNumberOfConstraints];

        for (int i = 0; i < constraints.size(); ++i) {
            int k = 0;
            Constraints currentConstraints = constraints.get(i);
            for (int j = 0; j < maxNumberOfConstraints; ++j) {
                System.out.println(String.format("i:%d j:%d k:%d currentConstraints.size():%d maxNumberOfConstraints - 1 - j:%d",i,j,k,currentConstraints.size(),maxNumberOfConstraints - 1 - j));
                if ((maxNumberOfConstraints - j) < currentConstraints.size()) {
                    res[i][j] = currentConstraints.getConstraint(k);
                    ++k;
                }
            }
        }

        return res;
    }

    private Constraint[][] createColumnConstraintMatrix(List<Constraints> constraints, int maxNumberOfConstraints) {
        Constraint[][] res = new Constraint[maxNumberOfConstraints][constraints.size()];

        for (int i = 0; i < constraints.size(); ++i) {
            Constraints currentConstraints = constraints.get(i);
            int k = 0;
            for (int j = 0; j < maxNumberOfConstraints; ++j) {
                if ((maxNumberOfConstraints - j) < currentConstraints.size()) {
                    res[j][i] = currentConstraints.getConstraint(k);
                    ++k;
                }
            }
        }

        return res;
    }

}
