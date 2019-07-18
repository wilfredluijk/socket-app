package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.room.GameRoom;
import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.room.RoomFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class RoomFactoryImpl implements RoomFactory {

    private final Map<Class<? extends Room>, BiFunction<Integer, Activity, Room>> supplierMap = new HashMap<>();

    public RoomFactoryImpl() {


    }

    @Override
    public <T extends Room> T getRoom(Class<T> type) {
        return null;
    }
}
