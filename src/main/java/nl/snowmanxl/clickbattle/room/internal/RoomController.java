package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.messages.rest.RestResponse;
import nl.snowmanxl.clickbattle.room.Participant;
import nl.snowmanxl.clickbattle.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomManager roomManager;

    @Autowired
    public RoomController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @PostMapping(path = "/new")
    public RestResponse createNewRoom(@RequestBody RoomConfig config) {
        return new RestResponse(String.valueOf(roomManager.createRoom(config)), RoomResponseType.GET_ROOM_ID);
    }

    @GetMapping(path = "/join/{id}")
    public RestResponse joinRoom(@PathVariable("id") int id) {
        var playerId = roomManager.joinRoom(id);
        return new RestResponse(playerId, RoomResponseType.GET_PLAYER_ID);
    }

    @PostMapping(path = "/update-player/{id}")
    public RestResponse updateParticipant(@PathVariable("id") int id, @RequestBody Participant participant) {
        roomManager.updateParticipant(id, participant);
        return new RestResponse("Participant updated", RoomResponseType.CONFIRMATION);
    }

    @RequestMapping(path = "/delete/{id}")
    public RestResponse deleteGame(@PathVariable("id") int id) {
        roomManager.deleteRoom(id);
        return new RestResponse("Game deleted", RoomResponseType.CONFIRMATION);
    }
}
