package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.ScoreSubmit;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class RoomManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);

    private final Map<Integer, Room> rooms = new HashMap<>();
    private final List<Consumer<Room>> roomChangeListeners = new ArrayList<>();

    public void addRoom(Integer id, RoomConfig config) {
        var room = new Room(id, config.getGameType(), config.getMaxPlayerCount());
        rooms.put(id, room);
        LOGGER.debug("Room added {}", room);
        broadCastChange(room);
    }

    private void broadCastChange(Room room) {
        LOGGER.debug("Broadcast room: {}", room);
        roomChangeListeners.forEach(listener -> listener.accept(room));
    }

    public void addRoomNotificationListener(Consumer<Room> listener) {
        this.roomChangeListeners.add(listener);
    }

    public Optional<Room> getRoom(Integer id) {
        return Optional.ofNullable(rooms.get(id));
    }

    public void deleteRoom(Integer id) {
        rooms.remove(id);
    }

    public Integer joinRoom(Integer id) {
        var optional = getRoom(id);
        if (optional.isPresent()) {
            var room = optional.get();
            broadCastChange(room);
            return room.addPlayer(new Player());
        }
        return -1;
    }

    public void updatePlayer(Integer id, Player player) {
        executeRoomAction(id, room -> {
            room.updatePlayer(player);
            broadCastChange(room);
        });
    }

    public SocketMessage<ScoreBroadcast> submitScore(Integer id, ScoreSubmit scoreSubmit) {
        return getRoom(id).map(room -> {
            var scores = room.scoreForPlayer(scoreSubmit.getPlayerId(), scoreSubmit.getPoints());
            var state = room.getGameState();
            var socketMessage = new SocketMessage<ScoreBroadcast>();
            socketMessage.setPayload(new ScoreBroadcast(scores, state));
            return socketMessage;
        }).orElse(null);
    }

    private void executeRoomAction(Integer id, Consumer<Room> action) {
        getRoom(id).ifPresent(action);
    }

    public void startGame(Integer id) {
        executeRoomAction(id, room -> {
            room.startGame();
            broadCastChange(room);
        });
    }

    public void resetGame(Integer id) {
        executeRoomAction(id, room -> {
            room.resetGame();
            broadCastChange(room);
        });
    }

    public void stopGame(Integer id) {
        executeRoomAction(id, room -> {
            room.stopGame();
            broadCastChange(room);
        });
    }

    public GameType getGameTypeOf(Integer id) {
        return getRoom(id).map(Room::getGameType).orElse(null);
    }
}
