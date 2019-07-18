package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.Score;

import java.util.Map;
import java.util.function.Consumer;

public class ClickRace implements SocketGame {

    private static final Integer MAX_NR_OF_PLAYERS = 250;
    private static final Integer SCORE_LIMIT = 10_000;
    private GameState gameState = GameState.WAITING;
    private Score score = new Score();
    private Consumer<GameState> gameStateConsumer;

    @Override
    public Integer getMaxNrOfPlayers() {
        return MAX_NR_OF_PLAYERS;
    }

    @Override
    public Integer getMaxDuration() {
        return -1;
    }

    @Override
    public Integer getScoreLimit() {
        return SCORE_LIMIT;
    }

    @Override
    public boolean canStart() {
        return gameState != GameState.STARTED;
    }

    @Override
    public GameState getCurrentGameState() {
        return gameState;
    }

    @Override
    public void start() {
        gameState = GameState.STARTED;
        emitStateChange();
    }

    @Override
    public void reset() {
        gameState = GameState.WAITING;
        score = new Score();
    }

    @Override
    public void stop() {
        gameState = GameState.STOPPED;
        emitStateChange();
    }

    @Override
    public void registerGameStateChangeListener(Consumer<GameState> listener) {
        gameStateConsumer = listener;
    }

    @Override
    public void scoreFor(Integer team, Integer points) {
        if (!score.limitIsReached(SCORE_LIMIT) && gameState == GameState.RUNNING) {
            score.scoreFor(team, points);
        } else {
            gameState = GameState.FINISHED;
            emitStateChange();
        }
    }

    @Override
    public Map<Integer, Integer> getScore() {
        return score.getScore();
    }

    private void emitStateChange() {
        if (gameStateConsumer != null) {
            gameStateConsumer.accept(gameState);
        }
    }

    @Override
    public String toString() {
        return "ClickRace{" +
                "gameState=" + gameState +
                ", score=" + score +
                '}';
    }
}
