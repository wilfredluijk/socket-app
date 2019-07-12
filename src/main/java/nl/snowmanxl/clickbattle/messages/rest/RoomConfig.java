package nl.snowmanxl.clickbattle.messages.rest;

import nl.snowmanxl.clickbattle.model.GameType;

public class RoomConfig {
    private Integer maxPlayerCount;
    private GameType gameType;

    public RoomConfig() {
    }

    public RoomConfig(Integer maxPlayerCount, GameType gameType) {
        this.maxPlayerCount = maxPlayerCount;
        this.gameType = gameType;
    }

    public Integer getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(Integer maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

}
