package nl.snowmanxl.clickbattle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.snowmanxl.clickbattle.component.RoomManager;
import nl.snowmanxl.clickbattle.messages.rest.RoomConfig;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomManager service;

    @Test
    @SuppressWarnings("unchecked")
    void createNewRoomUsesUniqueRoomNames() throws Exception {
        var config = new RoomConfig(100, GameType.CLICK_RACE);

        var valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(service).addRoom(valueCapture.capture(), eq(config));

        int roomCount = 9999;
        for (int idx = 1; idx <= roomCount; idx++) {
            mvc.perform(post("/room/new")
                    .content(new ObjectMapper().writeValueAsString(config))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
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
    void deleteGame() {
    }

    @Test
    void stopGame() {
    }
}