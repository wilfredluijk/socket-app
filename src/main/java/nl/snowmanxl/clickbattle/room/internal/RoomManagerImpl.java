package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.messages.socket.MessageDispatcher;
import nl.snowmanxl.clickbattle.messages.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.messages.socket.RoomUpdateMessage;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;
import nl.snowmanxl.clickbattle.room.Participant;
import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.room.RoomData;
import nl.snowmanxl.clickbattle.room.RoomIdService;
import nl.snowmanxl.clickbattle.room.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class RoomManagerImpl implements RoomManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);

    private final Map<Integer, Room> rooms = new HashMap<>();
    private final List<Consumer<Room>> roomChangeListeners = new ArrayList<>();
    private final RoomIdService idService;
    private final ApplicationContext context;
    private final MessageListenerManager messageListenerManager;
    private final MessageDispatcher messageDispatcher;

    public RoomManagerImpl(RoomIdService idService, ApplicationContext context, MessageListenerManager messageListenerManager,
                           MessageDispatcher messageDispatcher) {
        this.idService = idService;
        this.context = context;
        this.messageListenerManager = messageListenerManager;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public int createRoom(RoomConfig config) {
        var id = idService.getNewRoomId();
        var room = context.getBean(Room.class);
        room.configureRoom(id, config);
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
        messageListenerManager.removeRoomListeners(id);
    }

    @Override
    public String joinRoom(int id) {
        return getRoom(id).map(room -> {
            var playerId = room.addParticipant(new SimpleParticipant());
            broadCastChange(room);
            return playerId;
        }).orElseThrow(() -> noRoomFoundException(id));
    }

    @Override
    public void updateParticipant(int id, Participant participant) {
        executeAndBroadcastRoomAction(id, room -> room.updateParticipant(participant));
    }

    private void executeAndBroadcastRoomAction(int id, Consumer<Room> roomConsumer) {
        getRoom(id).ifPresent(room -> {
            roomConsumer.accept(room);
            broadCastChange(room);
        });
    }

    private void broadCastChange(Room room) {
        LOGGER.trace("Dispatch room update: {}", room);
        messageDispatcher.dispatchToRoom(room.getId(), new RoomUpdateMessage(RoomData.of(room)));
    }

}
