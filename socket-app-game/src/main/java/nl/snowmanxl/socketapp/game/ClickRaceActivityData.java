package nl.snowmanxl.socketapp.game;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.socketapp.activities.ActivityData;
import nl.snowmanxl.socketapp.activities.ParticipantKeyDeserializer;
import nl.snowmanxl.socketapp.model.GameState;
import nl.snowmanxl.socketapp.room.Participant;
import nl.snowmanxl.socketapp.model.ClickRaceScore;

import java.util.Map;

public class ClickRaceActivityData implements ActivityData {

    @JsonDeserialize(keyUsing = ParticipantKeyDeserializer.class)
    private Map<Participant, Player> participantPlayerMap;
    private GameState gameState;
    private ClickRaceScore score;

    public ClickRaceActivityData() {
    }

    public ClickRaceActivityData(Map<Participant, Player> participantPlayerMap, GameState gameState, ClickRaceScore score) {
        this.participantPlayerMap = participantPlayerMap;
        this.gameState = gameState;
        this.score = score;
    }

    public void setParticipantPlayerMap(Map<Participant, Player> participantPlayerMap) {
        this.participantPlayerMap = participantPlayerMap;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setScore(ClickRaceScore score) {
        this.score = score;
    }

    public Map<Participant, Player> getParticipantPlayerMap() {
        return participantPlayerMap;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ClickRaceScore getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ClickRaceActivityData{" +
                "participantPlayerMap=" + participantPlayerMap +
                ", gameState=" + gameState +
                ", score=" + score +
                '}';
    }
}
