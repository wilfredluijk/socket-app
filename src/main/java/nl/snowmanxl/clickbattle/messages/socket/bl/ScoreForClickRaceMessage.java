package nl.snowmanxl.clickbattle.messages.socket.bl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;

public class ScoreForClickRaceMessage implements SocketMessage {
    String playerId;

    public ScoreForClickRaceMessage() {
    }

    public ScoreForClickRaceMessage(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "ScoreForClickRaceMessage{" +
                "playerId='" + playerId + '\'' +
                '}';
    }
}
