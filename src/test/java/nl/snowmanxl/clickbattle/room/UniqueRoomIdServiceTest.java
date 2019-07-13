package nl.snowmanxl.clickbattle.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class UniqueRoomIdServiceTest {

    private static final int MAX_ROOM_COUNT = 90000;

    @Autowired
    RoomIdService service;

    @Test
    public void getAndReturnAllTheNumbersPlusOneGivesException() {
        IntStream.rangeClosed(1, MAX_ROOM_COUNT).forEach(idx -> service.getNewRoomId());
        Assertions.assertThrows(IllegalStateException.class, () -> service.getNewRoomId());
        resetRoomIds();
    }

    private void resetRoomIds() {
        IntStream.rangeClosed(10000, 89999).forEach(service::returnRoomId);
    }


}