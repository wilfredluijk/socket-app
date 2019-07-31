package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RoomImpl implements Room, MessageListenerCapable {

    @Autowired
    private MessageListenerManager manager;
    private int id;
    private RoomConfig config;
    private Set<Participant> participants = new HashSet<>();

    @Override
    public void enableListeners() {
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
        removeParticipant(participant);
        this.participants.add(participant);
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
    }

    private void removeParticipant(Participant participant) {
        this.participants.removeIf((part) -> part.getId().equals(participant.getId()));
    }
}
