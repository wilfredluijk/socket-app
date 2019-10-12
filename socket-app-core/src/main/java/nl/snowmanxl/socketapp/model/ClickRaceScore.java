package nl.snowmanxl.socketapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClickRaceScore {
    private Map<Integer, Integer> scores = new HashMap<>();

    public void scoreFor(int team, int points) {
        int scoreValue = Optional.ofNullable(this.scores.get(team)).orElse(0);
        scoreValue += points;
        this.scores.put(team, Math.max(scoreValue, 0));
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public boolean limitIsReached(int scoreLimit) {
        return scores.values().stream()
                .anyMatch(scoreValue -> scoreValue >= scoreLimit);
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + scores +
                '}';
    }
}
