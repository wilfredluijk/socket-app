package nl.snowmanxl.clickbattle.controller;

import nl.snowmanxl.clickbattle.component.RoomManager;
import nl.snowmanxl.clickbattle.messages.rest.RestResponse;
import nl.snowmanxl.clickbattle.messages.rest.RoomConfig;
import nl.snowmanxl.clickbattle.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(path = "/room")
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
    private static final List<Integer> UNIQUE_IDs;

    static {
        var collect = IntStream.rangeClosed(10000, 99999)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(collect);
        UNIQUE_IDs = new CopyOnWriteArrayList<>(collect);
    }

    private final RoomManager roomManager;

    @Autowired
    public RoomController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @PostMapping(path = "/new")
    public RestResponse createNewRoom(@RequestBody RoomConfig config) {
        var random = getUniqueRandom(); //TODO: Generate unique number per session
        LOGGER.trace("create new room with id {}", random);
        roomManager.addRoom(random, config);
        return new RestResponse(String.valueOf(random), "RoomID");
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
        restoreRoomId(id);
        return new RestResponse("Game deleted", "Confirm");
    }

    @RequestMapping(path = "/{id}/stop")
    public RestResponse stopGame(@PathVariable("id") Integer id) {
        roomManager.stopGame(id);
        return new RestResponse("Game Stopped", "Confirm");
    }

    private synchronized int getUniqueRandom() {
        if (UNIQUE_IDs.isEmpty()) {
            throw new IllegalStateException("No more unique room numbers available!");
        }
        return UNIQUE_IDs.remove(0);
    }

    private synchronized void restoreRoomId(int roomId) {
        if (!UNIQUE_IDs.contains(roomId)) {
            UNIQUE_IDs.add(roomId);
            Collections.shuffle(UNIQUE_IDs);
        }
    }
}
