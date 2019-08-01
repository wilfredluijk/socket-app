package nl.snowmanxl.clickbattle.messages.socket;

import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.room.RoomData;

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
