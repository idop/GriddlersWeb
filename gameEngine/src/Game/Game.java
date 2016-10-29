package Game;

import Game.Player.*;
import GameXmlParser.GameBoardXmlParser;
import GameXmlParser.Schema.Constraints;
import GameXmlParser.Schema.GameType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ido on 13/08/2016.
 */
public class Game {

    private final GameType gameType;
    private final int numberOfPlayers;
    private final SolutionBoard solutionBoard;
    private final String gameTitle;
    private List<Player> players;
    private int currentPlayerId = 0;
    private List<Constraints> rowConstraints;
    private List<Constraints> columnConstraints;
    private int maxColumnConstraints;
    private int maxRowConstraints;
    private boolean playerWon = false;
    private int currentRound;
    private boolean isGameEnded;
    private long startTime;
    private int totalRounds;
    private int rows;
    private int columns;
    private String status;
    private int moves;
    private boolean isGameStarted = false;
    private int currentNumberOfPlayers =0;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Game(GameBoardXmlParser gameBoardXmlParser) {
        currentRound = 1;
        gameType = gameBoardXmlParser.getGameType();
        rows = gameBoardXmlParser.getRows();
        columns = gameBoardXmlParser.getColumns();
        rowConstraints = gameBoardXmlParser.getRowConstraints();
        columnConstraints = gameBoardXmlParser.getColumnConstraints();
        solutionBoard = gameBoardXmlParser.getSolutionBoard();
        maxColumnConstraints = getMaxConstraints(columns, this.columnConstraints);
        maxRowConstraints = getMaxConstraints(rows, this.rowConstraints);
        startTime = System.currentTimeMillis();
        numberOfPlayers = gameBoardXmlParser.getTotalPlayers();
        players = new ArrayList<>(numberOfPlayers);
        totalRounds = gameBoardXmlParser.getTotalMoves();
        gameTitle = gameBoardXmlParser.getGameTitle();
        status = "Waiting for other Players";
        moves = 1;

    }

    public void addPlayer(String playerName, PlayerType playerType) {
        ++currentNumberOfPlayers;
        if(playerType == playerType.Human){
            players.add(new HumenPlayer(playerName, playerType, new GameBoard(rows, columns)));
        } else{
            players.add(new ComputerPlayer(playerName, playerType, new GameBoard(rows, columns)));
        }

        if (players.size() + 1 == numberOfPlayers) {
            status = "Game started current Player is: " + players.get(currentPlayerId).getName();
            isGameStarted = true;
        }
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public List<Constraints> getRowConstraints() {
        return rowConstraints;
    }

    public List<Constraints> getColumnConstraints() {
        return columnConstraints;
    }

    public GameType getGameType() {
        return gameType;
    }

    public GameBoard getGameBoard() {
        return players.get(currentPlayerId).getGameBoard();
    }

    public Constraints getRowConstraint(int i) {
        return rowConstraints.get(i);
    }

    public Constraints getColumnConstraint(int i) {
        return columnConstraints.get(i);
    }


    public int getMaxColumnConstraints() {
        return maxColumnConstraints;
    }

    public void printPlayerMoveHistory() {
        players.get(currentPlayerId).printMoveHistory();
    }

    public String getPlayerStatistics() {
        String res = players.get(currentPlayerId).getPlayerStatisticsString();
        String timeSinceStart = Long.toString((System.currentTimeMillis() - startTime) / 1000);
        res = res + " | Time since game begun (seconds): " + timeSinceStart;
        return res;
    }

    private int getMaxConstraints(int columns, List<Constraints> constraints) {
        int max = 0;
        for (int i = 0; i < columns; i++) {
            int currentListSize = constraints.get(i).size();
            if (currentListSize > max) {
                max = currentListSize;
            }
        }
        return max;
    }

    public int getMaxRowConstraints() {

        return maxRowConstraints;
    }

    public void doPlayerTurn(PlayerTurn turn) {
        Player currentPlayer = players.get(currentPlayerId);
        currentPlayer.doTurn(turn, solutionBoard);
        setPerfectConstraints();
        playerWon = isGameEnded = currentPlayer.checkIfPlayerWon();
        if (playerWon) {
            status = String.format("Player %s won!!!!", players.get(currentPlayerId).getName());
        } else{
            ++moves;
            if (moves > 2) {
                moves = 1;
                endRound();
            }
        }

    }


    public void undoTurn() throws PlayerTurnException {
        players.get(currentPlayerId).undoTurn(solutionBoard);
        setPerfectConstraints();
        --moves;
        moves = Math.max(1, moves);
    }

    public boolean checkIfPlayerWon() {
        return playerWon;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerId);
    }

    private void setPerfectConstraints() {
        //TODO
    }

    public void endRound() {
        int nextPlayerId = (currentPlayerId + 1) % currentNumberOfPlayers;
        currentPlayerId = nextPlayerId;
        status = "Game started current Player is: " + players.get(currentPlayerId).getName();
        int newRoundNumber;
        if (nextPlayerId == 0) {
            newRoundNumber = currentRound + 1;
            if (newRoundNumber > totalRounds) {
                isGameEnded = true;
                status = "Game ended due to all player exceeded the maximum number of rounds!! no player won :(";
            } else {
                currentRound = newRoundNumber;
            }
        }
    }

    public ActiveGameInfo getActiveGameInfo() {
        ActiveGamePlayerInfo[] playersInfo = new ActiveGamePlayerInfo[players.size()];

        for (int i = 0; i < players.size(); ++i) {
            Player currentPlayer = players.get(i);
            playersInfo[i] = new ActiveGamePlayerInfo(currentPlayer.getName(), currentPlayer.getPlayerType(), currentPlayer.getScoreString());
        }

        return new ActiveGameInfo(totalRounds, currentPlayerId, status, currentRound, moves, playersInfo, isGameStarted, isGameEnded);
    }

    public BoardSquare[][] getPlayerBoard(int playerId) {
        return players.get(playerId).getGameBoard().getBoard();
    }

    public void unRegisterPlayer(int playerId) {
        players.remove(playerId);
    }

    public class ActiveGameInfo {
        private int currentPlayerId;
        private String currentGameStatus;
        private int round;
        private int moves;
        private int totalRounds;
        private boolean isGameStarted;
        private boolean isGameEnded;
        ActiveGamePlayerInfo[] playersInfo;

        public ActiveGameInfo(int toatlRounds, int currentPlayerId, String currentGameStatus, int round, int moves, ActiveGamePlayerInfo[] playersInfo, boolean isGameStarted, boolean isGameEnded) {
            this.currentPlayerId = currentPlayerId;
            this.currentGameStatus = currentGameStatus;
            this.round = round;
            this.totalRounds = toatlRounds;
            this.moves = moves;
            this.playersInfo = playersInfo;
            this.isGameStarted = isGameStarted;
            this.isGameEnded = isGameEnded;
        }
    }

    public class ActiveGamePlayerInfo {
        private String name;
        private PlayerType type;
        private String playerScore;

        public ActiveGamePlayerInfo(String name, PlayerType type, String playerScore) {
            this.name = name;
            this.type = type;
            this.playerScore = playerScore;
        }
    }
}
