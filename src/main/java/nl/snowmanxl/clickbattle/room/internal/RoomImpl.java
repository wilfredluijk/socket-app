package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ActivityFactory;
import nl.snowmanxl.clickbattle.socket.MessageDispatcher;
import nl.snowmanxl.clickbattle.socket.MessageListenerManager;
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

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoomImpl implements Room, MessageListenerCapable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomImpl.class);

    private final MessageListenerManager manager;
    private final ActivityFactory activityFactory;
    private final MessageDispatcher messageDispatcher;

    private int id;
    private RoomData roomData;
    private Activity activity;

    public RoomImpl(MessageListenerManager manager, ActivityFactory activityFactory, MessageDispatcher messageDispatcher) {
        this.manager = manager;
        this.activityFactory = activityFactory;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void configureRoom(int id, RoomConfig config) {
        this.id = id;
        roomData = new RoomData(id, config);
        activity = this.activityFactory.createNewActivity(config.getActivityType());
        activity.registerMessageDispatcher(this::dispatchMessage);

        enableMessageListeners();
        broadcastUpdate();
    }

    @Override
    public String addParticipant(Participant participant) {
        String id = roomData.addParticipant(participant);
        activity.consumeParticipantCreation(participant);
        broadcastUpdate();
        return id;
    }

    @Override
    public void updateParticipant(Participant participant) {
       roomData.updateParticipant(participant);
       activity.consumeParticipantUpdate(participant);
       broadcastUpdate();
    }

    @Override
    public void enableMessageListeners() {
        manager.createRoomBasedListeners(id, this, activity);
    }

    @Override
    public void removeParticipant(Participant participant) {
        removeParticipant(participant.getId());
        broadcastUpdate();
    }

    @Override
    public int getId() {
        return id;
    }

    private void removeParticipant(String id) {
        roomData.removeParticipant(id);
        activity.consumeParticipantRemoval(id);
        broadcastUpdate();
    }

    private void dispatchMessage(SocketMessage message) {
        messageDispatcher.dispatchToRoom(id, message);
    }

    private void broadcastUpdate() {
        var message = new RoomUpdateMessage(roomData, activity.getActivityData());
        LOGGER.trace("Dispatch room update: {}", this);
        dispatchMessage(message);
    }

    @Override
    public String toString() {
        return "RoomImpl{" +
                "manager=" + manager +
                ", activityFactory=" + activityFactory +
                ", activity=" + activity +
                ", id=" + id +
                '}';
    }
}
