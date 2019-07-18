package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.SocketGame;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.room.GameRoom;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.*;

public class GameRoomImpl implements GameRoom {
    private int id;
    private GameType gameType;
    private GameState gameState;
    private Integer maxPlayerCount;
    private final List<Participant> players = new ArrayList<>();
    private SocketGame game;

    private GameRoomImpl(int id, GameType gameType, Integer maxPlayerCount) {
        this.id = id;
        this.gameType = gameType;
        this.maxPlayerCount = maxPlayerCount;
        this.game = GameType.getGame(gameType);
        Objects.requireNonNull(game).registerGameStateChangeListener(this::handleGameStateChanges);
    }

    public static GameRoomImpl newGameRoom(int id, GameType gameType, Integer maxPlayerCount) {
        return new GameRoomImpl(id, gameType, maxPlayerCount);
    }

    private void handleGameStateChanges(GameState stateChange) {
        this.gameState = stateChange;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String addPlayer(Participant player) {
        var playerId = UUID.randomUUID().toString();
        player.setId(playerId);
        players.add(player);
        return playerId;
    }

    @Override
    public void updatePlayer(Participant participant) {
        if (players.removeIf(p -> p.getId().equals(participant.getId()))) {
            players.add(participant);
        }
    }

    @Override
    public Map<Integer, Integer> scoreForPlayer(String playerId, int points) {
        if (gameIsStarted()) {
            players.stream()
                    .filter(participant -> participant.getId().equals(playerId))
                    .findAny()
                    .ifPresent(participant -> this.game.scoreFor(participant.getTeam(), points));
        }
        return game.getScore();
    }

    @Override
    public void startGame() {
        this.game.start();
    }

    @Override
    public void resetGame() {
        this.game.reset();
    }

    @Override
    public void stopGame() {
        game.stop();
    }


    private boolean gameIsStarted() {
        return this.gameState == GameState.STARTED;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getPlayerCount() {
        return players.size();
    }

    @Override
    public GameType getGameType() {
        return gameType;
    }

    @Override
    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    @Override
    public List<Participant> getPlayers() {
        return players;
    }

    @Override
    public SocketGame getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "GameRoomImpl{" +
                "id=" + id +
                ", playerCount=" + players.size() +
                ", gameType=" + gameType +
                ", gameState=" + gameState +
                ", maxPlayerCount=" + maxPlayerCount +
                ", players=" + players +
                ", game=" + game +
                '}';
    }


}
