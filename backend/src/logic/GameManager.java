package logic;

import Game.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ido on 17/10/2016.
 */
public class GameManager {

    private final Map<String, Game> games;
    private final Map<String, GameInfo> gamesInfo;

    public GameManager() {
        games = new HashMap<>();
        gamesInfo = new HashMap<>();
    }

    public List<GameInfo> getGamesList(){
        return gamesInfo.values().stream().collect(Collectors.toList());
    }

    public void addGame(Game game) {
        games.put(game.getGameTitle(), game);
        gamesInfo.put(game.getGameTitle(), new GameInfo(game));
    }

    public boolean isGameExists(String gameTitle) {
        return gamesInfo.containsKey(gameTitle);
    }

    public void registerUserToAGame(String gameTitle, User user) throws Exception {
        if(!isGameExists(gameTitle)){
            throw new Exception("Provided Game Does not exists");
        }

        GameInfo gameInfo = gamesInfo.get(gameTitle);
        Game game = games.get(gameTitle);

        gameInfo.registerPlayer();
        game.addPlayer(user.getUsername(),user.getPlayerType());
    }
}
