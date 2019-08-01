package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.room.Participant;
import nl.snowmanxl.clickbattle.room.Room;

import java.util.HashSet;
import java.util.Set;

public class RoomData {

    private int roomId;
    private RoomConfig config;
    private Set<Participant> participants = new HashSet<>();

    public RoomData(int roomId, RoomConfig config, Set<Participant> participants) {
        this.roomId = roomId;
        this.config = config;
        this.participants = participants;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public RoomConfig getConfig() {
        return config;
    }

    public void setConfig(RoomConfig config) {
        this.config = config;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public static RoomData of(Room room) {
       return new RoomData(room.getId(), room.getConfig(), room.getParticipants());
    }
}
