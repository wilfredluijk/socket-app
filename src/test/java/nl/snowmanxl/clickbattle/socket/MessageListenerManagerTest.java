package nl.snowmanxl.clickbattle.socket;

import nl.snowmanxl.clickbattle.room.RoomIdService;
import nl.snowmanxl.clickbattle.socket.testclass.TestMessage;
import nl.snowmanxl.clickbattle.socket.testclass.TestRoom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageListenerManager.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MessageListenerManagerTest {

    @Autowired
    MessageListenerManager manager;

    @Test
    public void createdListenersAreUsable() {
        TestRoom testRoom = messageToRoom();
        Assert.assertEquals("Message is not processed properly, expected value to be set in room", 1337, testRoom.getValue());
    }

    @Test
    public void createdListenersAreRemovable() {
        TestRoom testRoom = messageToRoom();
        manager.removeRoomListeners(0);
        manager.messageToRoom(0, new GenericMessage<>(new TestMessage(2000)));
        Assert.assertEquals("Message is not processed properly, expected value to be set in room", 1337, testRoom.getValue());
    }

    private TestRoom messageToRoom() {
        var testRoom = new TestRoom(manager);
        manager.messageToRoom(0, new GenericMessage<>(new TestMessage(1337)));
        return testRoom;
    }

}
