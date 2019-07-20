package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.ScoreSubmit;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface RoomManager {
    int createRoom(RoomConfig config);


    void addRoomNotificationListener(Consumer<Room> listener);

    Optional<Room> getRoom(int id);

    void deleteRoom(int id);

    String joinRoom(int id);

    void updatePlayer(int id, Participant participant);



    default Supplier<IllegalArgumentException> noRoomFoundExeptionSupplier(int id) {
        return () -> new IllegalArgumentException("No room found for id: "+ id);
    }
}
