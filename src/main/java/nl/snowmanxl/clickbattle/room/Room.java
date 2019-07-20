package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

public interface Room<T extends Activity> {

    Class<T> getRoomType();

    String addParticipant(Participant player);

    void updatePlayer(Participant participant);

    void configureRoom(RoomConfig config);
}
