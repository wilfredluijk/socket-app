package nl.snowmanxl.clickbattle.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.snowmanxl.clickbattle.model.GameType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomControllerTest.class);
    private static final RoomConfig CONFIG = new RoomConfig(100, GameType.CLICK_RACE);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomManager service;

    @Test
    void createNewRoomUsesUniqueRoomNames() throws Exception {
        var valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(service).addRoom(valueCapture.capture(), eq(CONFIG));

        int roomCount = 9999;
        for (int idx = 1; idx <= roomCount; idx++) {
            createNewRoomByHttpCall();
        }

        var uniques = new HashSet<>();
        var allValues = valueCapture.getAllValues();
        Assert.assertEquals("Incorrect number of calls received", roomCount, allValues.size());
        allValues.forEach(val -> Assert.assertTrue(uniques.add(val)));
    }

    @Test
    void joinRoom() {
    }

    @Test
    void updatePlayer() {
    }

    @Test
    void getGameType() {
    }

    @Test
    void startGame() {
    }

    @Test
    void resetGame() {
    }

    @Test
    void deleteGame() throws Exception {
       deleteRoomByHttpCall(100);
    }

    @Test
    void stopGame() {


    }

    private void deleteRoomByHttpCall(int id) throws Exception {
        mvc.perform(get("/room/{id}/delete",id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void createNewRoomByHttpCall() throws Exception {
        mvc.perform(post("/room/new")
                .content(new ObjectMapper().writeValueAsString(CONFIG))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}