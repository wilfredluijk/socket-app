package nl.snowmanxl.clickbattle.socket.testclass;


import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.socket.MessageListenerManager;

public class TestRoom {

    int value;

    public TestRoom(MessageListenerManager manager) {
        manager.createRoomBasedListeners(0, this);
    }

    @OnSocketMessage(TestMessage.class)
    public void handleTestMessage(TestMessage message) {
        value = message.getValue();
    }

    public int getValue() {
        return value;
    }
}
