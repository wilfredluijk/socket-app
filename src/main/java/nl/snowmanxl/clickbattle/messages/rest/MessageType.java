package nl.snowmanxl.clickbattle.messages.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.activities.ClickRaceActivityData;
import nl.snowmanxl.clickbattle.room.internal.RoomResponseType;

@JsonDeserialize(as = RoomResponseType.class)
public interface MessageType {
}
