package nl.snowmanxl.socketapp.activities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.snowmanxl.socketapp.messages.socket.SocketMessage;
import nl.snowmanxl.socketapp.room.Participant;

import java.util.function.Consumer;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface Activity {

    void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer);

    void consumeParticipantUpdate(Participant participant);

    void consumeParticipantCreation(Participant participant);

    void consumeParticipantRemoval(String id);

    ActivityData getActivityData();
}
