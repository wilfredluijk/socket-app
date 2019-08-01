package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ActivityFactory;
import nl.snowmanxl.clickbattle.socket.MessageDispatcher;
import nl.snowmanxl.clickbattle.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.messages.socket.bl.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.RoomUpdateMessage;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.MessageListenerCapable;
import nl.snowmanxl.clickbattle.room.Participant;
import nl.snowmanxl.clickbattle.room.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoomImpl implements Room, MessageListenerCapable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomImpl.class);

    private final MessageListenerManager manager;
    private final ActivityFactory activityFactory;
    private final MessageDispatcher messageDispatcher;

    private Activity activity;
    private int id;
    private RoomConfig config;
    private Set<Participant> participants = new HashSet<>();

    public RoomImpl(MessageListenerManager manager, ActivityFactory activityFactory, MessageDispatcher messageDispatcher) {
        this.manager = manager;
        this.activityFactory = activityFactory;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void configureRoom(int id, RoomConfig config) {
        this.id = id;
        this.config = config;
        activity = this.activityFactory.createNewActivity(config.getActivityType());
        activity.registerUpdateListener(this::dispatchMessage);
        broadcastUpdate();
    }

    @Override
    public String addParticipant(Participant participant) {
        var playerId = UUID.randomUUID().toString();
        participant.setId(playerId);
        participants.add(participant);
        broadcastUpdate();
        return playerId;
    }

    @Override
    public void updateParticipant(Participant participant) {
        Objects.requireNonNull(participant);
        if (participant.getId() != null) {
            removeParticipant(participant);
            this.participants.add(participant);
            broadcastUpdate();
        }
    }

    @Override
    public void enableMessageListeners() {
        manager.createRoomBasedListeners(id, this);
    }

    @Override
    public void removeParticipant(RemoveParticipantMessage message) {
        removeParticipant(message.getParticipant());
        broadcastUpdate();
    }

    private void removeParticipant(Participant participant) {
        this.participants.removeIf((part) -> Objects.equals(part.getId(), participant.getId()));
        broadcastUpdate();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public RoomConfig getConfig() {
        return config;
    }

    @Override
    public Set<Participant> getParticipants() {
        return participants;
    }

    private void dispatchMessage(SocketMessage message) {
        messageDispatcher.dispatchToRoom(id, message);
    }

    private void broadcastUpdate() {
            LOGGER.trace("Dispatch room update: {}", this);
            dispatchMessage(new RoomUpdateMessage(RoomData.of(this)));
    }

    @Override
    public String toString() {
        return "RoomImpl{" +
                "manager=" + manager +
                ", activityFactory=" + activityFactory +
                ", activity=" + activity +
                ", id=" + id +
                ", config=" + config +
                ", participants=" + participants +
                '}';
    }
}
