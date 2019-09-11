package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

public interface Room {

    String addParticipant(Participant participant);

    void updateParticipant(Participant participant);

    void removeParticipant(Participant participant);

    void configureRoom(int id, RoomConfig config);

    int getId();

}
