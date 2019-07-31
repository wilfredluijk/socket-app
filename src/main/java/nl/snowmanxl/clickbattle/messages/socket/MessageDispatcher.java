package nl.snowmanxl.clickbattle.messages.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageDispatcher {

    private final SimpMessagingTemplate webSocket;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDispatcher.class);

    @Autowired
    public MessageDispatcher(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    void dispatchToRoom(int roomId, SocketMessage message) {
        LOGGER.debug("Dispatching: {}, messageToRoom: {}", roomId, message);
        webSocket.convertAndSend("/topic/" + roomId + "/messageToRoom", message);
    }
}
