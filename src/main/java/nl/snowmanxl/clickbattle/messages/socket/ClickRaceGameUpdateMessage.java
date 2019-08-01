package nl.snowmanxl.clickbattle.messages.socket;

import nl.snowmanxl.clickbattle.model.ClickRaceData;

public class ClickRaceGameUpdateMessage implements SocketMessage {

    ClickRaceData data;

    public ClickRaceGameUpdateMessage(ClickRaceData data) {
        this.data = data;
    }

    public ClickRaceData getData() {
        return data;
    }

    public void setData(ClickRaceData data) {
        this.data = data;
    }
}
