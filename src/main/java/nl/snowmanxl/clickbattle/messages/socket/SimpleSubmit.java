package nl.snowmanxl.clickbattle.messages.socket;

public class SimpleSubmit implements ScoreSubmit {
    private String playerId;
    private Integer scoredPoints;

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public Integer getPoints() {
        return scoredPoints;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

}
