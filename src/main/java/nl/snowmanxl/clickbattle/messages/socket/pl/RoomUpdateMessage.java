package nl.snowmanxl.clickbattle.messages.socket.pl;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ActivityData;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.room.internal.RoomData;

public class RoomUpdateMessage implements SocketMessage {
    private RoomData room;
    private ActivityData activityData;

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

}
