package nl.snowmanxl.socketapp.socket.testclass;

import nl.snowmanxl.socketapp.messages.socket.SocketMessage;

public class TestMessage implements SocketMessage {

    private int value;

    public TestMessage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}