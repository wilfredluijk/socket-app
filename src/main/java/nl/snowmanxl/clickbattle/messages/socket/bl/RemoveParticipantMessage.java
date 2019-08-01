package nl.snowmanxl.clickbattle.messages.socket.bl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.Participant;

public class RemoveParticipantMessage implements SocketMessage {

    private Participant participant;

    public RemoveParticipantMessage(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
