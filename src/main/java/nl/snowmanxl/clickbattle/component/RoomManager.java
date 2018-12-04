package nl.snowmanxl.clickbattle.component;

import nl.snowmanxl.clickbattle.messages.rest.RoomConfig;
import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.ScoreSubmit;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.model.Room;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class RoomManager {

    private final Map<Integer, Room> rooms = new HashMap<>();
    private final List<Consumer<Room>> roomChangelisteners = new ArrayList<>();

    public void addRoom(Integer id, RoomConfig config) {
        var room = new Room(id, config.getGameType(), config.getMaxPlayerCount());
        rooms.put(id, room);
        broadCastChange(room);
    }

    private void broadCastChange(Room room) {
        roomChangelisteners.forEach(listener -> listener.accept(room));
    }

    public void addRoomNotificationListener(Consumer<Room> listener) {
        this.roomChangelisteners.add(listener);
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
        executeRoomAction(id, room -> room.updatePlayer(player));
    }

    public SocketMessage<ScoreBroadcast> submitScore(Integer id, ScoreSubmit scoreSubmit) {
        var optional = getRoom(id);
        if (optional.isPresent()) {
            var room = optional.get();
            var scores = room.scoreForPlayer(scoreSubmit.getPlayerId(), scoreSubmit.getPoints());
            var state = room.getGameState();
            var payload = new ScoreBroadcast(scores, state);
            var socketMessage = new SocketMessage<ScoreBroadcast>();
            socketMessage.setPayload(payload);
            return socketMessage;
        }
        return null;
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
}
