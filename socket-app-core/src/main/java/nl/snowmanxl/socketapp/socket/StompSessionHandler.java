package nl.snowmanxl.socketapp.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class StompSessionHandler extends StompSessionHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(StompSessionHandler.class);

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
       LOGGER.debug("frame: {}, payload: {}", headers, payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        LOGGER.debug("session: {}, connectedHeaders: {}", session, connectedHeaders);
    }

    public StompSessionHandler() {
        LOGGER.debug("sessionHandler");
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        LOGGER.debug("payloadtype {}", headers );
        return super.getPayloadType(headers);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        LOGGER.debug("session: {}, StompCommand: {}, headers {}, payload: {}, exception: {}", session, command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        LOGGER.debug("session: {}, exception: {}", session, exception);
    }
}
