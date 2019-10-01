package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.game.ClickRace;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.function.Consumer;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface Activity {

    void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer);

    void consumeParticipantUpdate(Participant participant);

    void consumeParticipantCreation(Participant participant);

    void consumeParticipantRemoval(String id);

    ActivityData getActivityData();
}
