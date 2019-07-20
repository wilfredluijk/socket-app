package nl.snowmanxl.clickbattle.room.internal;

import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ClickRace;
import nl.snowmanxl.clickbattle.activities.SmallToLarge;
import nl.snowmanxl.clickbattle.room.Room;
import nl.snowmanxl.clickbattle.room.RoomFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Service
public class RoomFactoryImpl implements RoomFactory {

    private final Map<Class<? extends Activity>, Supplier<Room<? extends Activity>>> roomMapping = new HashMap<>();
    private final ApplicationContext context;

    @Autowired
    public RoomFactoryImpl(ApplicationContext context) {
        this.context = context;
        roomMapping.put(ClickRace.class, this::getGameRoom);
        roomMapping.put(SmallToLarge.class, this::getGameRoom);
    }

    private GameRoomImpl getGameRoom() {
        return this.context.getBean(GameRoomImpl.class);
    }

    @SuppressWarnings("unchecked") //Casting due to unbounded wildcard type of Map
    @Override
    public <T extends Room> T getNewRoomFor(RoomConfig config) {
        T t = (T) Objects.requireNonNull(roomMapping.get(config.getGameType().getClass()));
        t.configureRoom(config);
        return t;
    }
}
