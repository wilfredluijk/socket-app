package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.room.internal.RoomConfig;

public interface RoomFactory {

    <T extends Room> T getNewRoomFor(RoomConfig type);
}
