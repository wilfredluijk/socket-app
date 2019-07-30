package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

public interface Room<T extends Activity> {

    Class<T> getRoomType();

    int getId();

    String addParticipant(Participant player);

    void updateParticipant(Participant participant);

    void configureRoom(RoomConfig config);
}
