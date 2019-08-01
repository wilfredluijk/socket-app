package nl.snowmanxl.clickbattle.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.snowmanxl.clickbattle.activities.ActivityType;
import nl.snowmanxl.clickbattle.model.GameType;
import nl.snowmanxl.clickbattle.room.internal.RoomConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RoomControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomControllerTest.class);
    private static final RoomConfig CONFIG = new RoomConfig(100, ActivityType.CLICK_RACE);

    private final MockMvc mvc;

    @Autowired
    public RoomControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void createNewRoomUsesUniqueRoomNames() throws Exception {
        int roomCount = 100;
        for (int idx = 1; idx <= roomCount; idx++) {
            var uniques = new HashSet<>();
            Assert.assertTrue(uniques.add(createNewRoomByHttpCall()));
        }
    }

    @Test
    void createAndDeleteRoom() throws Exception {
        var response = createNewRoomByHttpCall();
        var id = response.split("\"")[3];
        mvc.perform(get("/room/{id}/delete", Integer.parseInt(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }


    private String createNewRoomByHttpCall() throws Exception {
        MvcResult result = mvc.perform(post("/room/new")
                .content(new ObjectMapper().writeValueAsString(CONFIG))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        return result.getResponse().getContentAsString();
    }
}
