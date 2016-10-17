package logic;

import Game.Player.PlayerType;

/**
 * Created by ido on 11/10/2016.
 */
public class User {
    private String username;
    private PlayerType playerType;
    public User(String username, PlayerType playerType) {
        this.username=username;
        this.playerType= playerType;
    }

    public String getUsername() {
        return username;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
