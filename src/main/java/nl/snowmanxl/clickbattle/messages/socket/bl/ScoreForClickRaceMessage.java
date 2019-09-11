package nl.snowmanxl.clickbattle.messages.socket.bl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;

public class ScoreForClickRaceMessage implements SocketMessage {
    String playerId;
    int team;
}
