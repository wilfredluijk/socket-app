package nl.snowmanxl.clickbattle.messages.socket;

public class SocketMessage<T> {
    T payload;

    public SocketMessage() {
    }

    public SocketMessage(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "payload=" + payload +
                '}';
    }
}
