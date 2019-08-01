package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ActivityFactory;
import nl.snowmanxl.clickbattle.messages.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoomImpl implements Room, MessageListenerCapable {

    private final MessageListenerManager manager;
    private final ActivityFactory activityFactory;

    private Activity activity;
    private int id;
    private RoomConfig config;
    private Set<Participant> participants = new HashSet<>();
    private Consumer<Room> updateConsumer;

    public RoomImpl(MessageListenerManager manager, ActivityFactory activityFactory) {
        this.manager = manager;
        this.activityFactory = activityFactory;
    }

    @Override
    public void enableMessageListeners() {
        manager.createRoomBasedListeners(id, this);
    }

    @Override
    public String addParticipant(Participant participant) {
        var playerId = UUID.randomUUID().toString();
        participant.setId(playerId);
        participants.add(participant);
        return playerId;
    }

    @Override
    public void updateParticipant(Participant participant) {
        Objects.requireNonNull(participant);
        if (participant.getId() != null) {
            removeParticipant(participant);
            this.participants.add(participant);
        }
    }

    @Override
    @OnSocketMessage(RemoveParticipantMessage.class)
    public void removeParticipant(RemoveParticipantMessage message) {
        removeParticipant(message.getParticipant());
    }

    @Override
    public void configureRoom(int id, RoomConfig config) {
        this.id = id;
        this.config = config;
        activity = this.activityFactory.createNewActivity(config.getActivityType());
    }

    private void removeParticipant(Participant participant) {
        this.participants.removeIf((part) -> Objects.equals(part.getId(), participant.getId()));
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

    @Override
    public RoomData getRoomData() {
        return RoomData.of(this);
    }

    @Override
    public void registerUpdateConsumer(Consumer<Room> updateConsumer) {
        this.updateConsumer = updateConsumer;
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
