package nl.snowmanxl.clickbattle.messages.socket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.snowmanxl.clickbattle.game.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.game.ClickRaceGameUpdateMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.RoomUpdateMessage;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomUpdateMessage.class),
        @JsonSubTypes.Type(value = ClickRaceGameUpdateMessage.class),
        @JsonSubTypes.Type(value = ScoreForClickRaceMessage.class)
})
public interface SocketMessage {
}
