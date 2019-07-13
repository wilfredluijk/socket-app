package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.messages.rest.RestResponse;
import nl.snowmanxl.clickbattle.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/room")
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);


    private final RoomManager roomManager;

    private final RoomIdService roomIdService;

    @Autowired
    public RoomController(RoomManager roomManager, RoomIdService roomIdService) {
        this.roomManager = roomManager;
        this.roomIdService = roomIdService;
    }

    @PostMapping(path = "/new")
    public RestResponse createNewRoom(@RequestBody RoomConfig config) {
        int newRoomId = roomIdService.getNewRoomId();
        roomManager.addRoom(newRoomId, config);
        return new RestResponse(String.valueOf(newRoomId), "RoomID");
    }

    @RequestMapping(path = "/{id}/join")
    public RestResponse joinRoom(@PathVariable("id") Integer id) {
        var integer = roomManager.joinRoom(id);
        return new RestResponse(String.valueOf(integer), "PlayerID");
    }

    @PostMapping(path = "/{id}/update-player")
    public RestResponse updatePlayer(@PathVariable("id") Integer id
            , @RequestBody Player player) {
        roomManager.updatePlayer(id, player);
        return new RestResponse("Player updated", "Confirm");
    }

    @GetMapping(path = "/{id}/getgametype")
    public RestResponse getGameType(@PathVariable("id") Integer id) {
        ;
        return new RestResponse(String.valueOf(roomManager.getGameTypeOf(id)), "GameType");
    }

    @RequestMapping(path = "/{id}/start")
    public RestResponse startGame(@PathVariable("id") Integer id) {
        roomManager.startGame(id);
        return new RestResponse("Game Started", "Confirm");
    }

    @RequestMapping(path = "/{id}/reset")
    public RestResponse resetGame(@PathVariable("id") Integer id) {
        roomManager.resetGame(id);
        return new RestResponse("Game reset", "Confirm");
    }

    @RequestMapping(path = "/{id}/delete")
    public RestResponse deleteGame(@PathVariable("id") Integer id) {
        roomManager.deleteRoom(id);
        roomIdService.returnRoomId(id);
        return new RestResponse("Game deleted", "Confirm");
    }

    @RequestMapping(path = "/{id}/stop")
    public RestResponse stopGame(@PathVariable("id") Integer id) {
        roomManager.stopGame(id);
        return new RestResponse("Game Stopped", "Confirm");
    }


}
