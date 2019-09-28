package nl.snowmanxl.clickbattle.socket;

import nl.snowmanxl.clickbattle.messages.socket.bl.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;
import nl.snowmanxl.clickbattle.room.RoomManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SocketSubscriptionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketSubscriptionManager.class);
    private final MessageListenerManager messageListenerManager;
    private final RoomManager roomManager;
    private final Map<String, RoomPlayerMapping> playerSessionMap = new HashMap<>();
    private final Map<String, String> organizersMap = new HashMap<>();

    @Autowired
    public SocketSubscriptionManager(MessageListenerManager messageListenerManager, RoomManager roomManager) {
        this.messageListenerManager = messageListenerManager;
        this.roomManager = roomManager;
    }

    @EventListener
    public void onSessionConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        LOGGER.info("Session connect: number of active connections: "+ event);
    }

    @EventListener
    public void onSessionSubscribedEvent(SessionSubscribeEvent event) {
        LOGGER.debug("Subscribed: {}", event);
        var sha = StompHeaderAccessor.wrap(event.getMessage());
        var playerId = sha.getFirstNativeHeader("player_id");
        var roomId = sha.getFirstNativeHeader("room_id");

        String sessionId = sha.getSessionId();
        if (playerId != null && roomId != null) {
            playerSessionMap.put(sessionId, new RoomPlayerMapping(roomId, playerId));
        } else if (roomId != null) {
            organizersMap.put(sessionId, roomId);
        }
    }

    @EventListener
    public void onSessionDisconnectEvent(SessionDisconnectEvent event) {
        var sha = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = sha.getSessionId();

        Optional.ofNullable(playerSessionMap.get(sessionId))
                .ifPresent(mapping -> {
                    roomManager.removeParticipant(Integer.parseInt(mapping.roomId), new SimpleParticipant(mapping.playerId));
                    playerSessionMap.remove(sessionId);
                });
        Optional.ofNullable(organizersMap.get(sessionId))
                .ifPresent(roomId -> {
                    organizersMap.remove(sessionId);
                    roomManager.deleteRoom(Integer.parseInt(roomId));
                });
    }

    private static class RoomPlayerMapping {
        private final String roomId;
        private final String playerId;

        public RoomPlayerMapping(String roomId, String playerId) {
            this.roomId = roomId;
            this.playerId = playerId;
        }
    }
}
