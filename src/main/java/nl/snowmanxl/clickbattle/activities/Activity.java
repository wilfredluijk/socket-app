package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;

import java.util.function.Consumer;

public interface Activity {
   void registerUpdateListener(Consumer<SocketMessage> messageConsumer);
}
