package nl.snowmanxl.clickbattle.model;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private Map<Integer, Integer> score = new HashMap<>();

    public void scoreFor(Integer team, Integer points) {
        Integer score = this.score.get(team);
        if(score == null) {
            score = 0;
        }
        score += points;
        this.score.put(team, score < 0 ? 0 : score);
    }

    public Map<Integer, Integer> getScore() {
        return score;
    }

    public boolean limitIsReached(Integer scoreLimit) {
        return score.values().stream()
                .anyMatch(score -> score >= scoreLimit);
    }
}
