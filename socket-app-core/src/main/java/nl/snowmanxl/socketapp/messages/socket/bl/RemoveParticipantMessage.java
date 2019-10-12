package nl.snowmanxl.socketapp.messages.socket.bl;

import nl.snowmanxl.socketapp.messages.socket.SocketMessage;

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
