package nl.snowmanxl.socketapp.messages.socket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.snowmanxl.socketapp.game.ScoreForClickRaceMessage;
import nl.snowmanxl.socketapp.game.ClickRaceGameUpdateMessage;
import nl.snowmanxl.socketapp.messages.socket.pl.RoomUpdateMessage;

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
