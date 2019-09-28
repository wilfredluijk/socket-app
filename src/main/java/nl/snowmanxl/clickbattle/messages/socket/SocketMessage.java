package nl.snowmanxl.clickbattle.messages.socket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import nl.snowmanxl.clickbattle.messages.socket.bl.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.ClickRaceGameUpdateMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.RoomUpdateMessage;

@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomUpdateMessage.class),
        @JsonSubTypes.Type(value = ClickRaceGameUpdateMessage.class),
        @JsonSubTypes.Type(value = ScoreForClickRaceMessage.class)
})
public interface SocketMessage {
}
