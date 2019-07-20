package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.GameType;

import java.util.Map;
import java.util.function.Consumer;

public interface SocketGame extends GameType  {
    Integer getMaxNrOfPlayers();
    Integer getMaxDuration();
    Integer getScoreLimit();

    boolean canStart();

    GameState getCurrentGameState();

    void start();
    void reset();
    void stop();

    void registerGameStateChangeListener(Consumer<GameState> listener);

    void scoreFor(Integer team, Integer points);

    Map<Integer, Integer> getScore();


}

