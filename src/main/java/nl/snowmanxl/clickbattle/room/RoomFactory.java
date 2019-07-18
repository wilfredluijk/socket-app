package nl.snowmanxl.clickbattle.room;

public interface RoomFactory {

    <T extends Room> T getRoom(Class<T> type);
}
