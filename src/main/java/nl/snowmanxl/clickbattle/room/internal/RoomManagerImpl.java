package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;
import nl.snowmanxl.clickbattle.room.Participant;
import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.room.RoomIdService;
import nl.snowmanxl.clickbattle.room.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RoomManagerImpl implements RoomManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);

    private final Map<Integer, Room> rooms = new HashMap<>();
    private final RoomIdService idService;
    private final ApplicationContext context;
    private final MessageListenerManager messageListenerManager;


    public RoomManagerImpl(RoomIdService idService, ApplicationContext context, MessageListenerManager messageListenerManager) {
        this.idService = idService;
        this.context = context;
        this.messageListenerManager = messageListenerManager;
    }

    @Override
    public int createRoom(RoomConfig config) {
        var id = idService.getNewRoomId();
        var room = context.getBean(Room.class);
        room.configureRoom(id, config);
        rooms.put(id, room);
        LOGGER.trace("GameRoomImpl added {}", room);
        return id;
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
        return getRoom(id)
                .map(room -> room.addParticipant(new SimpleParticipant()))
                .orElseThrow(() -> noRoomFoundException(id));
    }

    @Override
    public void updateParticipant(int id, Participant participant) {
        getRoom(id).ifPresent( room -> room.updateParticipant(participant));
    }

    @Override
    public void removeParticipant(int id, Participant participant) {
        getRoom(id).ifPresent( room -> room.removeParticipant(participant));
    }

}
