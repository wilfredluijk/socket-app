package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface RoomManager {
    int createRoom(RoomConfig config);

    Optional<Room> getRoom(int id);

    void deleteRoom(int id);

    String joinRoom(int id);

    void updateParticipant(int id, Participant participant);

    default IllegalArgumentException noRoomFoundException(int id) {
        return new IllegalArgumentException("No room found for id: " + id);
    }
}
