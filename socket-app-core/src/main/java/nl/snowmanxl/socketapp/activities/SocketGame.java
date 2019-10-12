package nl.snowmanxl.socketapp.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.socketapp.game.ClickRace;
import nl.snowmanxl.socketapp.messages.socket.OnSocketMessage;
import nl.snowmanxl.socketapp.game.ResetSocketGameMessage;
import nl.snowmanxl.socketapp.game.StartSocketGameMessage;
import nl.snowmanxl.socketapp.game.StopSocketGameMessage;
import nl.snowmanxl.socketapp.model.GameType;

@JsonDeserialize(as = ClickRace.class)
public interface SocketGame extends GameType {

    @OnSocketMessage(StartSocketGameMessage.class)
    void start(StartSocketGameMessage message);

    @OnSocketMessage(ResetSocketGameMessage.class)
    void reset(ResetSocketGameMessage message);

    @OnSocketMessage(StopSocketGameMessage.class)
    void stop(StopSocketGameMessage message);
}

