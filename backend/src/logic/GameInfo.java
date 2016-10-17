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

    public GameInfo(Game game) {
        this.title = game.getGameTitle();
        this.totalMoves = game.getTotalmoves();
        this.numberOfPlayers = game.getNumberOfPlayers();
    }

    public void registerPlayer() throws Exception {
        if (numberOfRegisteredPlayers >= numberOfPlayers) {
            throw new Exception("Game already have its maximum number of players allowed");
        }
        numberOfRegisteredPlayers++;
    }
}
