package nl.snowmanxl.clickbattle.socket.testclass;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;

public class TestMessage implements SocketMessage {

    private int value;

    public TestMessage(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}