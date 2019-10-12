package nl.snowmanxl.socketapp.testclass;


import nl.snowmanxl.socketapp.messages.socket.OnSocketMessage;
import nl.snowmanxl.socketapp.socket.MessageListenerManager;

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

    @Override
    public String toString() {
        return "TestRoom{" +
                "value=" + value +
                '}';
    }
}
