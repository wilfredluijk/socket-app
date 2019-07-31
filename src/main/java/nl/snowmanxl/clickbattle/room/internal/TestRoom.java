package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.messages.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestRoom {

    @Autowired
    public TestRoom(MessageListenerManager manager) {
       manager.createRoomBasedListeners(0, this);
    }

    @OnSocketMessage(TestMessage.class)
    public void handleTestMessage(TestMessage message) {
        System.out.println(message);
    }


}
