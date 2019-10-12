package nl.snowmanxl.socketapp.model;

public class ClickRaceData {
    private GameState gameState = GameState.WAITING;
    private ClickRaceScore score = new ClickRaceScore();

    public ClickRaceData() {
    }

    public ClickRaceData(GameState gameState, ClickRaceScore score) {
        this.gameState = gameState;
        this.score = score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ClickRaceScore getScore() {
        return score;
    }

    public void setScore(ClickRaceScore score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ClickRaceData{" +
                "gameState=" + gameState +
                ", score=" + score +
                '}';
    }
}
