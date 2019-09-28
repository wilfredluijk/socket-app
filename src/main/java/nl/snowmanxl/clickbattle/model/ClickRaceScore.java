package nl.snowmanxl.clickbattle.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClickRaceScore {
    private Map<Integer, Integer> score = new HashMap<>();

    public void scoreFor(int team, int points) {
        int score = Optional.ofNullable(this.score.get(team)).orElse(0);
        score += points;
        this.score.put(team, Math.max(score, 0));
    }

    public Map<Integer, Integer> getScore() {
        return score;
    }

    public boolean limitIsReached(int scoreLimit) {
        return score.values().stream()
                .anyMatch(score -> score >= scoreLimit);
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                '}';
    }
}
