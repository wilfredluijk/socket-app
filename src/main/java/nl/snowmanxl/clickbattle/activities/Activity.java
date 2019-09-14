package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Activity {

    void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer);

    void consumeParticipantUpdate(Participant participant);

    void consumeParticipantCreation(Participant participant);

    void consumeParticipantRemoval(String id);

    ActivityData getActivityData();
}
