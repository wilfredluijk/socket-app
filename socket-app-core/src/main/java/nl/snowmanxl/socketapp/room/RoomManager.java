package nl.snowmanxl.socketapp.room;

import nl.snowmanxl.socketapp.room.internal.RoomConfig;

import java.util.Optional;

public interface RoomManager {
    int createRoom(RoomConfig config);

    Optional<Room> getRoom(int id);

    void deleteRoom(int id);

    String joinRoom(int id);

    void updateParticipant(int id, Participant participant);

    void removeParticipant(int id, Participant participant);

    default IllegalArgumentException noRoomFoundException(int id) {
        return new IllegalArgumentException("No room found for id: " + id);
    }
}
