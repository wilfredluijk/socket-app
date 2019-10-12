package nl.snowmanxl.socketapp;

import nl.snowmanxl.socketapp.socket.StompSessionHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class TestUtil {


    private TestUtil() {
        //no instantiation
    }
    public static WebSocketStompClient getStompClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        stompClient.setMessageConverter(messageConverter);
        return stompClient;
    }

    private static List<Transport> createTransportClient() {
        return new ArrayList<>(1) {{
            add(new WebSocketTransport(new StandardWebSocketClient()));
        }};
    }

    public static StompSession getStompSession(final String URL, WebSocketStompClient stompClient)
            throws InterruptedException, ExecutionException, TimeoutException {
        return stompClient.connect(URL, new StompSessionHandler()).get(1, TimeUnit.SECONDS);
    }
}
