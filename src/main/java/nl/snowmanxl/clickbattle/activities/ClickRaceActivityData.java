package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.snowmanxl.clickbattle.model.ClickRaceScore;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.room.Participant;

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
