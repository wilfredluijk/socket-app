package nl.snowmanxl.clickbattle.messages.socket;

import nl.snowmanxl.clickbattle.model.GameState;

import java.util.Map;

public class ScoreBroadcast {
    private GameState state;
    private Map<Integer, Integer> score;

    public ScoreBroadcast() {
    }

    public ScoreBroadcast(Map<Integer, Integer> score, GameState State) {
        this.score = score;
    }

    public Map<Integer, Integer> getScore() {
        return score;
    }

    public void setScore(Map<Integer, Integer> score) {
        this.score = score;
    }
}
