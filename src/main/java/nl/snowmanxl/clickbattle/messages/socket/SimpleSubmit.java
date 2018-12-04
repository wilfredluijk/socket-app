package nl.snowmanxl.clickbattle.messages.socket;

public class SimpleSubmit implements ScoreSubmit {
    private Integer playerId;
    private Integer scoredPoints;

    @Override
    public Integer getPlayerId() {
        return playerId;
    }

    @Override
    public Integer getPoints() {
        return scoredPoints;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

}
