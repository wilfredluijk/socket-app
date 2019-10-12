package nl.snowmanxl.socketapp.game;

import nl.snowmanxl.socketapp.Condition;
import nl.snowmanxl.socketapp.messages.rest.RestResponse;
import nl.snowmanxl.socketapp.messages.socket.SocketMessage;
import nl.snowmanxl.socketapp.model.SimpleParticipant;
import nl.snowmanxl.socketapp.room.internal.RoomConfig;
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

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocketGameTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketGameTest.class);
    private static final String WS_LOCALHOST = "ws://localhost:";
    private static final String PULL_THE_ROPE = "/pulltherope";
    private static final String SCORE_ENDPOINT = "/app/messageToRoom/";
    private static final String SCORE_LISTENING_ENDPOINT = "/topic/messageToRoom/";
    private static final String NEW_ROOM_URL = "/room/new";
    private static final String UPDATE_PLAYER_URL = "/room/update-player/";
    private static final String JOIN_ROOM_URL = "/room/join/";

    @Value("${local.server.port}")
    private int port;
    private String URL;
    private StompSession stompClientSession;
    private List<SocketMessage> receivedSocketMessages = new CopyOnWriteArrayList<>();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() throws InterruptedException, ExecutionException, TimeoutException {
        URL = WS_LOCALHOST + port + PULL_THE_ROPE;
        stompClientSession = TestUtil.getStompSession(URL, TestUtil.getStompClient());
        receivedSocketMessages.clear();
    }

    @Test
    public void testGameMessageIsReceived() {
        var roomId = getRoomIdByRestCall();
        subscribeToRoom(roomId);

        var playerId = joinRoomAndGetPlayerId(roomId);
        assertPlayerNameUpdated(roomId, playerId, "Henk");

        sendMessageToRoom(new ScoreForClickRaceMessage(playerId), roomId);
        waitForReceivedMessagesCount(3);
    }

    @Test
    public void testScoreUpdateMessageIsReceived() throws InterruptedException {
        var roomId = getRoomIdByRestCall();
        subscribeToRoom(roomId);

        var playerIdOne = joinRoomAndGetPlayerId(roomId);
        assertPlayerNameUpdated(roomId, playerIdOne, "Henk");

        var playerIdTwo = joinRoomAndGetPlayerId(roomId);
        assertPlayerNameUpdated(roomId, playerIdTwo, "Henk");

        sendMessageToRoom(new ScoreForClickRaceMessage(playerIdOne), roomId);
        sendMessageToRoom(new ScoreForClickRaceMessage(playerIdTwo), roomId);

        clearMessages();
        expectToReceiveMessage(ClickRaceGameUpdateMessage.class);
    }

    private void clearMessages() {
        receivedSocketMessages.clear();
    }

    private void expectToReceiveMessage(Class<?> clickRaceGameUpdateMessageClass)  {
        pollUntilTrueOrTimeout(() -> receivedSocketMessages.stream()
                .anyMatch(message -> message.getClass().equals(clickRaceGameUpdateMessageClass)));
    }

    private void subscribeToRoom(String roomId) {
        stompClientSession.subscribe(getRoomSubscriptionHeaders(roomId), new GameStompFrameHandler());
    }

    private void sendMessageToRoom(SocketMessage message, String roomId) {
        stompClientSession.send(SCORE_ENDPOINT + roomId, message);
    }

    private void waitForReceivedMessagesCount(int expectedCount) {
        pollUntilTrueOrTimeout(() -> expectedCount == receivedSocketMessages.size());
        Assert.assertEquals(getErorFromMessages(expectedCount), expectedCount, receivedSocketMessages.size());
    }

    private void pollUntilTrueOrTimeout(Condition condition)  {
        await().atMost(2, TimeUnit.SECONDS).until(condition::passes);
    }

    private String getErorFromMessages(int expectedCount) {
        StringBuilder builder = new StringBuilder();
        builder.append("Expected ")
                .append(expectedCount)
                .append(" found ")
                .append(receivedSocketMessages.size())
                .append(" messages, received the following messages: \n\n\n");
        receivedSocketMessages.forEach(message -> builder.append(message.toString()).append("\n"));
        return builder.toString();
    }

    private void assertPlayerNameUpdated(String roomId, String playerId, String playerName) {
        var updateParticipantResponse = testRestTemplate.postForObject(UPDATE_PLAYER_URL + roomId,
                new SimpleParticipant(playerName, playerId), RestResponse.class);
        Assert.assertEquals("Participant updated", updateParticipantResponse.getMessage());
    }

    private String joinRoomAndGetPlayerId(String roomId) {
        var roomJoinResponse = testRestTemplate.getForObject(JOIN_ROOM_URL + roomId, RestResponse.class);
        return roomJoinResponse.getMessage();
    }

    private StompHeaders getRoomSubscriptionHeaders(String roomId) {
        StompHeaders headers = new StompHeaders();
        headers.add(StompHeaders.DESTINATION, SCORE_LISTENING_ENDPOINT + roomId);
        headers.add("room_id", roomId);
        return headers;
    }

    private String getRoomIdByRestCall() {
        var responseMessage = testRestTemplate.postForObject(NEW_ROOM_URL,
                new RoomConfig(50, ActivityType.CLICK_RACE), RestResponse.class);
        LOGGER.info("Room creation response: {}", responseMessage);
        return responseMessage.getMessage();
    }

    private class GameStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return SocketMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            LOGGER.info("HandleFrame: {}, object: {}", stompHeaders, o);
            receivedSocketMessages.add((SocketMessage) o);
        }
    }
}
