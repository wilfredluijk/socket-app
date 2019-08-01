package nl.snowmanxl.clickbattle.messages.socket.pl;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomData;

public class RoomUpdateMessage implements SocketMessage {
    RoomData room;

    public RoomUpdateMessage(RoomData room) {
        this.room = room;
    }

    public RoomData getRoom() {
        return room;
    }

    public void setRoom(RoomData room) {
        this.room = room;
    }

}
