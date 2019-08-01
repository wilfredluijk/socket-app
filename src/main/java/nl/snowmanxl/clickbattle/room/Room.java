package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

import java.util.Set;

public interface Room {

    String addParticipant(Participant participant);

    void updateParticipant(Participant participant);

    @OnSocketMessage(RemoveParticipantMessage.class)
    void removeParticipant(RemoveParticipantMessage message);

    void configureRoom(int id, RoomConfig config);

    int getId();

    RoomConfig getConfig();

    Set<Participant> getParticipants();


}
