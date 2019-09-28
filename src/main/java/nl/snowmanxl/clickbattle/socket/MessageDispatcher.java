package nl.snowmanxl.clickbattle.socket;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class MessageDispatcher {

    private final SimpMessagingTemplate webSocket;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDispatcher.class);

    @Autowired
    public MessageDispatcher(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    public void dispatchToRoom(int roomId, SocketMessage message) {
        String destination = "/topic/messageToRoom/" + roomId;
        LOGGER.debug("Dispatching to destination: {}, message: {}", destination, message);
        webSocket.convertAndSend(destination, message);
    }
}
