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

    void broadCastChange(GameRoom room);

    void addRoomNotificationListener(Consumer<GameRoom> listener);

    Optional<GameRoom> getRoom(int id);

    void deleteRoom(int id);

    String joinRoom(int id);

    void updatePlayer(int id, Participant participant);

    SocketMessage<ScoreBroadcast> submitScore(int id, ScoreSubmit scoreSubmit);

    void executeAndBroadcastRoomAction(int id, Consumer<GameRoom> roomConsumer);

    void startGame(int id);

    void resetGame(int id);

    void stopGame(int id);

    GameType getGameTypeOf(int id);

    default Supplier<IllegalArgumentException> noRoomFoundExeptionSupplier(int id) {
        return () -> new IllegalArgumentException("No room found for id: "+ id);
    }
}
