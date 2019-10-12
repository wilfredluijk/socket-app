package nl.snowmanxl.socketapp.messages.socket.pl;

import nl.snowmanxl.socketapp.activities.ActivityData;
import nl.snowmanxl.socketapp.messages.socket.SocketMessage;
import nl.snowmanxl.socketapp.room.internal.RoomData;

public class RoomUpdateMessage implements SocketMessage {
    private RoomData room;
    private ActivityData activityData;


    public RoomUpdateMessage() {
    }

    public RoomUpdateMessage(RoomData room, ActivityData data) {
        this.room = room;
        this.activityData = data;
    }

    public ActivityData getActivityData() {
        return activityData;
    }

    public void setActivityData(ActivityData activityData) {
        this.activityData = activityData;
    }

    public RoomData getRoom() {
        return room;
    }

    public void setRoom(RoomData room) {
        this.room = room;
    }


    @Override
    public String toString() {
        return "RoomUpdateMessage{" +
                "room=" + room +
                ", activityData=" + activityData +
                '}';
    }
}
