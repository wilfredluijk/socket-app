package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.ScoreSubmit;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.room.internal.GameRoomImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class RoomManager {

    @Autowired
    private RoomIdService idService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);

    private final Map<Integer, GameRoom> rooms = new HashMap<>();
    private final List<Consumer<GameRoom>> roomChangeListeners = new ArrayList<>();

    public int addRoom(RoomConfig config) {
        var id = idService.getNewRoomId();
        var room = new GameRoomImpl(id, config.getGameType(), config.getMaxPlayerCount());
        rooms.put(id, room);
        LOGGER.trace("GameRoomImpl added {}", room);
        broadCastChange(room);
        return id;
    }

    private void broadCastChange(GameRoom room) {
        LOGGER.trace("Broadcast room: {}", room);
        roomChangeListeners.forEach(listener -> listener.accept(room));
    }

    public void addRoomNotificationListener(Consumer<GameRoom> listener) {
        this.roomChangeListeners.add(listener);
    }

    public Optional<GameRoom> getRoom(Integer id) {
        return Optional.ofNullable(rooms.get(id));
    }

    public void deleteRoom(Integer id) {
        rooms.remove(id);
        idService.returnRoomId(id);
    }

    public String joinRoom(int id) {
        return getRoom(id).map(room -> {
            var playerId = room.addPlayer(new Player());
            broadCastChange(room);
            return playerId;
        }).orElseThrow(noRoomFoundExeptionSupplier(id));
    }

    public void updatePlayer(Integer id, Participant participant) {
        executeAndBroadcastRoomAction(id, room -> {
            room.updatePlayer(participant);
        });
    }

    public SocketMessage<ScoreBroadcast> submitScore(Integer id, ScoreSubmit scoreSubmit) {
        return getRoom(id).map(room -> {
            var scores = room.scoreForPlayer(scoreSubmit.getPlayerId(), scoreSubmit.getPoints());
            var state = room.getGameState();
            var socketMessage = new SocketMessage<ScoreBroadcast>();
            socketMessage.setPayload(new ScoreBroadcast(scores, state));
            return socketMessage;
        }).orElseThrow(noRoomFoundExeptionSupplier(id));
    }

    private void executeAndBroadcastRoomAction(Integer id, Consumer<GameRoom> roomConsumer) {
        getRoom(id).ifPresent(room -> {
            roomConsumer.accept(room);
            broadCastChange(room);
        });
    }

    public void startGame(int id) {
        executeAndBroadcastRoomAction(id, GameRoom::startGame);
    }

    public void resetGame(Integer id) {
        executeAndBroadcastRoomAction(id, GameRoom::resetGame);
    }

    public void stopGame(Integer id) {
        executeAndBroadcastRoomAction(id, GameRoom::stopGame);
    }

    public GameType getGameTypeOf(Integer id) {
        return getRoom(id).map(GameRoom::getGameType).orElse(null);
    }

    private Supplier<IllegalArgumentException> noRoomFoundExeptionSupplier(int id) {
        return () -> new IllegalArgumentException("No room found for id: "+ id);
    }


}
