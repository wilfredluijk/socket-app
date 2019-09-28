package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.function.Consumer;
import java.util.function.Supplier;

@JsonDeserialize(as = ClickRace.class)
public interface Activity {

    void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer);

    void consumeParticipantUpdate(Participant participant);

    void consumeParticipantCreation(Participant participant);

    void consumeParticipantRemoval(String id);

    ActivityData getActivityData();
}
