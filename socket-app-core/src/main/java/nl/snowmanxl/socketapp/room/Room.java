package nl.snowmanxl.socketapp.room;

import nl.snowmanxl.socketapp.room.internal.RoomConfig;

public interface Room {

    String addParticipant(Participant participant);

    void updateParticipant(Participant participant);

    void removeParticipant(Participant participant);

    void configureRoom(int id, RoomConfig config);

    int getId();

}
