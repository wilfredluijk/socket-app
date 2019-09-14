package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.model.ClickRaceScore;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.Map;

public class ClickRaceActivityData implements ActivityData {

    private final Map<Participant, Player> participantPlayerMap;
    private final GameState gameState;
    private final ClickRaceScore score;

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
}
