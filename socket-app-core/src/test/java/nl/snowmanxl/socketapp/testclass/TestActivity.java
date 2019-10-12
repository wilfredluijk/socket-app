package nl.snowmanxl.socketapp.testclass;

import nl.snowmanxl.socketapp.activities.Activity;
import nl.snowmanxl.socketapp.activities.ActivityData;
import nl.snowmanxl.socketapp.messages.socket.SocketMessage;
import nl.snowmanxl.socketapp.room.Participant;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("test-activity")
@Scope("prototype")
public class TestActivity implements Activity  {

    @Override
    public void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer) {

    }

    @Override
    public void consumeParticipantUpdate(Participant participant) {

    }

    @Override
    public void consumeParticipantCreation(Participant participant) {

    }

    @Override
    public void consumeParticipantRemoval(String id) {

    }

    @Override
    public ActivityData getActivityData() {
        return null;
    }

    @Override
    public String toString() {
        return "TestActivity{}";
    }
}
