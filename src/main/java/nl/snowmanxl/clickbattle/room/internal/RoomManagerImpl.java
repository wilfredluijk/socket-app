package nl.snowmanxl.clickbattle.room.internal;

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

    private final Map<Integer, Room> rooms = new HashMap<>();
    private final List<Consumer<Room>> roomChangeListeners = new ArrayList<>();
    private final RoomIdService idService;
    private final RoomFactory roomFactory;


    public RoomManagerImpl(RoomIdService idService, RoomFactory roomFactory) {
        this.idService = idService;
        this.roomFactory = roomFactory;
    }

    @Override
    public int createRoom(RoomConfig config) {
        var id = idService.getNewRoomId();
        var room = roomFactory.getNewRoomFor(config);
        rooms.put(id, room);
        LOGGER.trace("GameRoomImpl added {}", room);
        broadCastChange(room);
        return id;
    }

    @Override
    public void addRoomNotificationListener(Consumer<Room> listener) {
        this.roomChangeListeners.add(listener);
    }

    @Override
    public Optional<Room> getRoom(int id) {
        return Optional.ofNullable(rooms.get(id));
    }

    @Override
    public void deleteRoom(int id) {
        rooms.remove(id);
        idService.returnRoomId(id);
    }

    @Override
    public String joinRoom(int id) {
        return getRoom(id).map(room -> {
            var playerId = room.addParticipant(new Player());
            broadCastChange(room);
            return playerId;
        }).orElseThrow(noRoomFoundExeptionSupplier(id));
    }

    @Override
    public void updatePlayer(int id, Participant participant) {
        executeAndBroadcastRoomAction(id, room -> {
            room.updatePlayer(participant);
        });
    }

    private void executeAndBroadcastRoomAction(int id, Consumer<Room> roomConsumer) {
        getRoom(id).ifPresent(room -> {
            roomConsumer.accept(room);
            broadCastChange(room);
        });
    }

    private void broadCastChange(Room room) {
        LOGGER.trace("Broadcast room: {}", room);
        roomChangeListeners.forEach(listener -> listener.accept(room));
    }

}
