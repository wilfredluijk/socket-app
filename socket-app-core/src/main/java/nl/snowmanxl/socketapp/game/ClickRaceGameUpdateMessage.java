package nl.snowmanxl.socketapp.game;

import nl.snowmanxl.socketapp.messages.socket.SocketMessage;
import nl.snowmanxl.socketapp.model.ClickRaceData;

public class ClickRaceGameUpdateMessage implements SocketMessage {

    private ClickRaceData data;

    public ClickRaceGameUpdateMessage() {
    }

    public ClickRaceGameUpdateMessage(ClickRaceData data) {
        this.data = data;
    }

    public ClickRaceData getData() {
        return data;
    }

    public void setData(ClickRaceData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClickRaceGameUpdateMessage{" +
                "data=" + data +
                '}';
    }
}
