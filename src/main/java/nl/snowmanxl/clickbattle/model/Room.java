package nl.snowmanxl.clickbattle.model;

import nl.snowmanxl.clickbattle.gametypes.SocketGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Room {
    private Integer id;
    private Integer playerCount;
    private GameType gameType;
    private GameState gameState;
    private Integer maxPlayerCount;
    private final List<Player> players = new ArrayList<>();
    private SocketGame game;

    public Room(Integer id, GameType gameType, Integer maxPlayerCount) {
        this.id = id;
        this.gameType = gameType;
        this.maxPlayerCount = maxPlayerCount;
        this.game = GameType.getGame(gameType);
        Objects.requireNonNull(game).registerGameStateChangeListener(this::handleGameStateChanges);
    }

    private void handleGameStateChanges(GameState stateChange) {
        this.gameState = stateChange;
    }

    public Integer getId() {
        return id;
    }

    public Integer addPlayer(Player player) {
        var size = players.size();
        player.setId(size);
        players.add(player);
        this.playerCount = size;
        return size;
    }

    public void updatePlayer(Player player) {
        if (players.removeIf(p -> p.getId() == player.getId())) {
            players.add(player);
        }
    }

    public Map<Integer, Integer> scoreForPlayer(Integer playerId, Integer points) {
        if(gameIsStarted()) {
            players.stream()
                    .filter(player -> player.getId() == playerId)
                    .findAny()
                    .ifPresent(player -> this.game.scoreFor(player.getTeam(), points));
        }
        return game.getScore();
    }

    public void startGame() {
        this.game.start();
    }

    public void resetGame() {
        this.game.reset();
    }

    public void stopGame() {
        game.stop();
    }


    private boolean gameIsStarted() {
        return this.gameState == GameState.STARTED;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Integer getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public SocketGame getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", playerCount=" + playerCount +
                ", gameType=" + gameType +
                ", gameState=" + gameState +
                ", maxPlayerCount=" + maxPlayerCount +
                ", players=" + players +
                ", game=" + game +
                '}';
    }



}
