package nl.snowmanxl.socketapp.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomIdService.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UniqueRoomIdServiceTest {

    private static final int MAX_ROOM_COUNT = 90000;

    @Autowired
    public RoomIdService service;

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