package nl.snowmanxl.clickbattle.room;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UniqueRoomIdService implements RoomIdService {

    private final List<Integer> uniqueIds;

    public UniqueRoomIdService() {
        var collect = IntStream.rangeClosed(10000, 99999)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(collect);
        uniqueIds = new ArrayList<>(collect);
    }

    @Override
    public synchronized int getNewRoomId() {
        if (uniqueIds.isEmpty()) {
            throw new IllegalStateException("No more unique room numbers available!");
        }
        return uniqueIds.remove(0);
    }

    @Override
    public synchronized void returnRoomId(int id) {
        if (!uniqueIds.contains(id)) {
            uniqueIds.add(id);
        }
    }
}
