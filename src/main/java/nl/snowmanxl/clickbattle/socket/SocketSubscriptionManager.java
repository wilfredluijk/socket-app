package nl.snowmanxl.clickbattle.socket;

import nl.snowmanxl.clickbattle.messages.socket.bl.RemoveParticipantMessage;
import nl.snowmanxl.clickbattle.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SocketSubscriptionManager {

    private final MessageListenerManager manager;
    private final RoomManager roomManager;
    private final Map<String, RoomPlayerMapping> playerSessionMap = new HashMap<>();
    private final Map<String, String> organizersMap = new HashMap<>();

    @Autowired
    public SocketSubscriptionManager(MessageListenerManager manager, RoomManager roomManager) {
        this.manager = manager;
        this.roomManager = roomManager;
    }

    @EventListener
    public void onSessionSubscribedEvent(SessionSubscribeEvent event) {
        var sha = StompHeaderAccessor.wrap(event.getMessage());
        var player_id = sha.getFirstNativeHeader("player_id");
        var roomId = sha.getFirstNativeHeader("room_id");

        String sessionId = sha.getSessionId();
        if (player_id != null && roomId != null) {
            playerSessionMap.put(sessionId, new RoomPlayerMapping(roomId, player_id));
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
                    manager.messageToRoom(Integer.parseInt(mapping.roomId), new RemoveParticipantMessage(mapping.playerId));
                    playerSessionMap.remove(sessionId);
                });
        Optional.ofNullable(organizersMap.get(sessionId))
                .ifPresent(roomId -> {
                    organizersMap.remove(sessionId);
                    roomManager.deleteRoom(Integer.parseInt(roomId));
                });
    }

    private class RoomPlayerMapping {
        private final String roomId;
        private final String playerId;

        public RoomPlayerMapping(String roomId, String playerId) {
            this.roomId = roomId;
            this.playerId = playerId;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getPlayerId() {
            return playerId;
        }
    }
}
