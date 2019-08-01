package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.pl.ClickRaceGameUpdateMessage;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ResetSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StartSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StopSocketGameMessage;
import nl.snowmanxl.clickbattle.model.ClickRaceData;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.ClickRaceScore;

import java.util.function.Consumer;

public class ClickRace implements SocketGame {

    private GameState gameState = GameState.WAITING;
    private ClickRaceScore score = new ClickRaceScore();
    private Consumer<SocketMessage> messageConsumer;

    @Override
    public void start(StartSocketGameMessage message) {
        gameState = GameState.STARTED;
        broadcastUpdate();
    }

    @Override
    public void reset(ResetSocketGameMessage message) {
        gameState = GameState.WAITING;
        score = new ClickRaceScore();
        broadcastUpdate();
    }

    @Override
    public void stop(StopSocketGameMessage message) {
        gameState = GameState.STOPPED;
        broadcastUpdate();
    }

    @OnSocketMessage(ScoreForClickRaceMessage.class)
    public void scoreForClickRace(ScoreForClickRaceMessage message) {
        broadcastUpdate();
    }

    @Override
    public String toString() {
        return "ClickRace{" +
                "gameState=" + gameState +
                ", score=" + score +
                '}';
    }

    private void broadcastUpdate() {
        messageConsumer.accept(new ClickRaceGameUpdateMessage(new ClickRaceData(gameState, score)));
    }

    @Override
    public void registerUpdateListener(Consumer<SocketMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
