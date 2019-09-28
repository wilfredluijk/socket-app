package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.ActivityType;

import java.util.Objects;

public class RoomConfig {
    private int maxPlayerCount;
    private ActivityType activityType;

    public RoomConfig() {
    }

    public RoomConfig(int maxPlayerCount, ActivityType type) {
        this.maxPlayerCount = maxPlayerCount;
        this.activityType = type;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setGameType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomConfig that = (RoomConfig) o;
        return maxPlayerCount == that.maxPlayerCount &&
                activityType == that.activityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPlayerCount, activityType);
    }

    @Override
    public String toString() {
        return "RoomConfig{" +
                "maxPlayerCount=" + maxPlayerCount +
                ", activityType=" + activityType +
                '}';
    }
}
