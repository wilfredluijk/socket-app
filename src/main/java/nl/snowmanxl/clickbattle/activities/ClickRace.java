package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.ResetSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.StartSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.StopSocketGameMessage;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.Score;

import java.util.Map;
import java.util.function.Consumer;

public class ClickRace implements SocketGame {

    //TODO: Listen for messages

    private GameState gameState = GameState.WAITING;
    private Score score = new Score();
    private Consumer<GameState> gameStateConsumer;

    @Override
    public void start(StartSocketGameMessage message) {
        gameState = GameState.STARTED;
    }

    @Override
    public void reset(ResetSocketGameMessage message) {
        gameState = GameState.WAITING;
        score = new Score();
    }

    @Override
    public void stop(StopSocketGameMessage message) {
        gameState = GameState.STOPPED;
    }

    @Override
    public String toString() {
        return "ClickRace{" +
                "gameState=" + gameState +
                ", score=" + score +
                '}';
    }
}
