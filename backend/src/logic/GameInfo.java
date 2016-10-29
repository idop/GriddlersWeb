package logic;

import Game.Game;

/**
 * Created by ido on 17/10/2016.
 */
public class GameInfo {
    private String title;
    private int totalMoves;
    private int numberOfPlayers;
    private int numberOfRegisteredPlayers = 0;
    private int rows = 0;
    private int columns = 0;
    private String uploadedBy;

    public void setDisplay(boolean display) {
        this.display = display;
    }

    private boolean display = true;

    public GameInfo(Game game, String uploadedBy) {
        this.title = game.getGameTitle();
        this.totalMoves = game.getTotalRounds();
        this.numberOfPlayers = game.getNumberOfPlayers();
        this.uploadedBy = uploadedBy;
        this.columns = game.getColumns();
        this.rows = game.getRows();
    }

    public void registerPlayer() throws ServiceException {
        if (numberOfRegisteredPlayers >= numberOfPlayers) {
            throw new ServiceException("Game already have its maximum number of players allowed");
        }
        numberOfRegisteredPlayers++;
    }
}
