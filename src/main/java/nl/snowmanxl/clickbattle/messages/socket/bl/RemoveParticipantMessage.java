package nl.snowmanxl.clickbattle.messages.socket.bl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.Participant;

public class RemoveParticipantMessage implements SocketMessage {

    private String id;

    public RemoveParticipantMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
