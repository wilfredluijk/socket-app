package nl.snowmanxl.clickbattle.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.snowmanxl.clickbattle.component.RoomManager;
import nl.snowmanxl.clickbattle.messages.socket.ScoreBroadcast;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.model.Room;
import nl.snowmanxl.clickbattle.model.Score;
import nl.snowmanxl.clickbattle.messages.socket.SimpleSubmit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RoomSocket {

    private final SimpMessagingTemplate webSocket;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomSocket.class);
    private final RoomManager manager;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public RoomSocket(SimpMessagingTemplate webSocket, RoomManager manager) {
        this.webSocket = webSocket;
        this.manager = manager;
        this.manager.addRoomNotificationListener(this::consumeRoomUpdate);
    }

    @MessageMapping("/{id}/submit")
    @SendTo("/topic/{id}/gamescore")
    public SocketMessage<ScoreBroadcast> clickCount(@DestinationVariable Integer id, SocketMessage<SimpleSubmit> message)  {
        return manager.submitScore(id, message.getPayload());
    }

    private void consumeRoomUpdate(Room room) {
        var message = new SocketMessage<>(room);


        //TODO: Make more elegant
        try {
            String payload = MAPPER.writeValueAsString(message);
            LOGGER.info("Dispatching message {}", payload);
            webSocket.convertAndSend("/topic/" + room.getId() + "/roomState", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
