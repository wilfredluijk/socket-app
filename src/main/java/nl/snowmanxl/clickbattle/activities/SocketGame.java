package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ResetSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StartSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StopSocketGameMessage;
import nl.snowmanxl.clickbattle.model.GameType;

public interface SocketGame extends GameType {

    @OnSocketMessage(StartSocketGameMessage.class)
    void start(StartSocketGameMessage message);

    @OnSocketMessage(ResetSocketGameMessage.class)
    void reset(ResetSocketGameMessage message);

    @OnSocketMessage(StopSocketGameMessage.class)
    void stop(StopSocketGameMessage message);
}
