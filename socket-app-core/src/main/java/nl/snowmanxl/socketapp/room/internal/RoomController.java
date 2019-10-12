package nl.snowmanxl.socketapp.room.internal;

import nl.snowmanxl.socketapp.messages.rest.RestResponse;
import nl.snowmanxl.socketapp.room.Participant;
import nl.snowmanxl.socketapp.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
