package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.model.GameState;

import java.util.Map;
import java.util.function.Consumer;

public class SmallToLarge implements SocketGame {
    @Override
    public Integer getMaxNrOfPlayers() {
        return null;
    }

    @Override
    public Integer getMaxDuration() {
        return null;
    }

    @Override
    public Integer getScoreLimit() {
        return null;
    }

    @Override
    public boolean canStart() {
        return false;
    }

    @Override
    public GameState getCurrentGameState() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void registerGameStateChangeListener(Consumer<GameState> listener) {

    }

    @Override
    public void scoreFor(Integer team, Integer points) {

    }

    @Override
    public Map<Integer, Integer> getScore() {
        return null;
    }
}
