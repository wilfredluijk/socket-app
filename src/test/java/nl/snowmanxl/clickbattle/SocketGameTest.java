package nl.snowmanxl.clickbattle;

import nl.snowmanxl.clickbattle.activities.ActivityType;
import nl.snowmanxl.clickbattle.messages.rest.RestResponse;
import nl.snowmanxl.clickbattle.messages.socket.bl.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.RoomUpdateMessage;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocketGameTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketGameTest.class);
    private static final String WS_LOCALHOST = "ws://localhost:";
    private static final String PULL_THE_ROPE = "/pulltherope";
    private static final String SCORE_ENDPOINT = "/app/messageToRoom/";
    private static final String SCORE_LISTENING_ENDPOINT = "/topic/messageToRoom/";
    private static final String SEND_CREATE_BOARD_ENDPOINT = "/app/create/";

    @Value("${local.server.port}")
    private int port;
    private String URL;

    private CompletableFuture<RoomUpdateMessage> completableFuture = new CompletableFuture<RoomUpdateMessage>();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        URL = WS_LOCALHOST + port + PULL_THE_ROPE;
    }

    @Test
    public void testStartOfRoom() throws InterruptedException, ExecutionException, TimeoutException {

        GameStompFrameHandler handler = new GameStompFrameHandler();
        WebSocketStompClient stompClient = TestUtil.getStompClient();
        StompSession stompSession = TestUtil.getStompSession(URL, stompClient);


        var responseMessage = testRestTemplate.postForObject("/room/new",
                new RoomConfig(50, ActivityType.CLICK_RACE), RestResponse.class);
        LOGGER.info("Response: {}", responseMessage);

        var roomId = responseMessage.getMessage();
        StompHeaders headers = new StompHeaders();
        headers.add(StompHeaders.DESTINATION, SCORE_LISTENING_ENDPOINT + roomId);
        headers.add("room_id", roomId);
        stompSession.subscribe(headers, handler);

        var roomJoinResponse = testRestTemplate.getForObject("/room/" + roomId + "/join", RestResponse.class);
        var playerId = roomJoinResponse.getMessage();

        var updateParticipantResponse = testRestTemplate.postForObject("/room/" + roomId + "/update-player",
                new SimpleParticipant("Henk", playerId), RestResponse.class);
        Assert.assertEquals("Participant updated", updateParticipantResponse.getMessage());

       testRestTemplate.postForObject("/room/" + roomId + "/update-player",
                new SimpleParticipant("Henk", playerId), RestResponse.class);
        StompSession.Receiptable send = stompSession.send(SCORE_ENDPOINT + roomId,
                new ScoreForClickRaceMessage(playerId));
        LOGGER.debug("Receipt: {}", send.getReceiptId());
        String uuid = UUID.randomUUID().toString();
        stompSession.send(SEND_CREATE_BOARD_ENDPOINT + uuid, null);
        completableFuture.get(4, TimeUnit.SECONDS);
    }

    private class GameStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return RoomUpdateMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            LOGGER.info("HandleFrame: {}, object: {}", stompHeaders, o);
            completableFuture.complete((RoomUpdateMessage) o);
        }
    }
}
