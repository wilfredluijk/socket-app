package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.socket.MessageListenerManager;
import nl.snowmanxl.clickbattle.room.internal.TestMessage;
import nl.snowmanxl.clickbattle.room.internal.TestRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MessageListenerTest {

    @Autowired
    private TestRoom testRoom;

    @Autowired
    private MessageListenerManager manager;


    @Test
    public void testConsumerCreation() {
       manager.messageToRoom(0, new TestMessage());
    }

}
