package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.model.GameType;

import java.util.Objects;

public class RoomConfig {
    private int maxPlayerCount;
    private Activity type;

    public RoomConfig() {
    }

    public RoomConfig(int maxPlayerCount, Activity type) {
        this.maxPlayerCount = maxPlayerCount;
        this.type = type;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public Activity getGameType() {
        return type;
    }

    public void setGameType(GameType gameType) {
        this.type = gameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomConfig that = (RoomConfig) o;
        return Objects.equals(maxPlayerCount, that.maxPlayerCount) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPlayerCount, type);
    }
}
