package nl.snowmanxl.clickbattle.messages.rest;

import nl.snowmanxl.clickbattle.model.*;

import java.util.List;

public class RoomConfig {
    private Integer maxPlayerCount;
    private GameType gameType;
    private GameState gameState;

    public RoomConfig() {
    }

    public RoomConfig(Integer maxPlayerCount, GameType gameType, GameState gameState) {
        this.maxPlayerCount = maxPlayerCount;
        this.gameType = gameType;
        this.gameState = gameState;
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
