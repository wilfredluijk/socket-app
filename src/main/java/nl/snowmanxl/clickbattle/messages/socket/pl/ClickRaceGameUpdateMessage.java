package nl.snowmanxl.clickbattle.messages.socket.pl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.ClickRaceData;

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
