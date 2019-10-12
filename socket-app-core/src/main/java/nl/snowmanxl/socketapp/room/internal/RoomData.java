package nl.snowmanxl.socketapp.room.internal;

import nl.snowmanxl.socketapp.room.Participant;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class RoomData {

    private int roomId;
    private RoomConfig config;
    private final Set<Participant> participants = new HashSet<>();

    public RoomData() {
    }

    public RoomData(int roomId, RoomConfig config) {
        this.roomId = roomId;
        this.config = config;
    }

    public String addParticipant(Participant participant) {
        var playerId = UUID.randomUUID().toString();
        participant.setId(playerId);
        participants.add(participant);
        return playerId;
    }

    public void updateParticipant(Participant participant) {
        Objects.requireNonNull(participant);
        if (participant.getId() != null) {
            removeParticipant(participant.getId());
            this.participants.add(participant);
        }
    }

    public void removeParticipant(String id) {
        this.participants.removeIf((part) -> Objects.equals(part.getId(), id));
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
        this.participants.addAll(participants);
    }


    @Override
    public String toString() {
        return "RoomData{" +
                "roomId=" + roomId +
                ", config=" + config +
                ", participants=" + participants +
                '}';
    }
}
