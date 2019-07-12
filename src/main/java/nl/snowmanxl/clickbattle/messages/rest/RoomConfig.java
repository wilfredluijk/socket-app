package nl.snowmanxl.clickbattle.messages.rest;

import nl.snowmanxl.clickbattle.model.GameType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomConfig that = (RoomConfig) o;
        return Objects.equals(maxPlayerCount, that.maxPlayerCount) &&
                gameType == that.gameType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPlayerCount, gameType);
    }
}
