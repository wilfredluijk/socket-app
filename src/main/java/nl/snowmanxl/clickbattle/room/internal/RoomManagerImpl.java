package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.ScoreSubmit;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.room.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class RoomManagerImpl implements RoomManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);

    private final RoomIdService idService;
    private final Map<Integer, GameRoom> rooms = new HashMap<>();
    private final List<Consumer<GameRoom>> roomChangeListeners = new ArrayList<>();

    public RoomManagerImpl(RoomIdService idService) {
        this.idService = idService;
    }

    @Override
    public int createRoom(RoomConfig config) {
        var id = idService.getNewRoomId();
        var room = GameRoomImpl.newGameRoom(id, config.getGameType(), config.getMaxPlayerCount());
        rooms.put(id, room);
        LOGGER.trace("GameRoomImpl added {}", room);
        broadCastChange(room);
        return id;
    }

    @Override public void broadCastChange(GameRoom room) {
        LOGGER.trace("Broadcast room: {}", room);
        roomChangeListeners.forEach(listener -> listener.accept(room));
    }

    @Override public void addRoomNotificationListener(Consumer<GameRoom> listener) {
        this.roomChangeListeners.add(listener);
    }

    @Override public Optional<GameRoom> getRoom(int id) {
        return Optional.ofNullable(rooms.get(id));
    }

    @Override public void deleteRoom(int id) {
        rooms.remove(id);
        idService.returnRoomId(id);
    }

    @Override public String joinRoom(int id) {
        return getRoom(id).map(room -> {
            var playerId = room.addPlayer(new Player());
            broadCastChange(room);
            return playerId;
        }).orElseThrow(noRoomFoundExeptionSupplier(id));
    }

    @Override public void updatePlayer(int id, Participant participant) {
        executeAndBroadcastRoomAction(id, room -> {
            room.updatePlayer(participant);
        });
    }

    @Override public SocketMessage<ScoreBroadcast> submitScore(int id, ScoreSubmit scoreSubmit) {
        return getRoom(id).map(room -> {
            var scores = room.scoreForPlayer(scoreSubmit.getPlayerId(), scoreSubmit.getPoints());
            var state = room.getGameState();
            var socketMessage = new SocketMessage<ScoreBroadcast>();
            socketMessage.setPayload(new ScoreBroadcast(scores, state));
            return socketMessage;
        }).orElseThrow(noRoomFoundExeptionSupplier(id));
    }

    @Override public void executeAndBroadcastRoomAction(int id, Consumer<GameRoom> roomConsumer) {
        getRoom(id).ifPresent(room -> {
            roomConsumer.accept(room);
            broadCastChange(room);
        });
    }

    @Override
    public void startGame(int id) {
        executeAndBroadcastRoomAction(id, GameRoom::startGame);
    }

    @Override
    public void resetGame(int id) {
        executeAndBroadcastRoomAction(id, GameRoom::resetGame);
    }

    @Override
    public void stopGame(int id) {
        executeAndBroadcastRoomAction(id, GameRoom::stopGame);
    }

    @Override
    public GameType getGameTypeOf(int id) {
        return getRoom(id).map(GameRoom::getGameType).orElse(null);
    }


}
