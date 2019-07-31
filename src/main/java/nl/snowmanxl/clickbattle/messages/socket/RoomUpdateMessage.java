package nl.snowmanxl.clickbattle.messages.socket;

import nl.snowmanxl.clickbattle.room.Room;

public class RoomUpdateMessage {
    Room room;

    public RoomUpdateMessage(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
