package nl.snowmanxl.socketapp.room;

public interface RoomIdService {

    int getNewRoomId();

    void returnRoomId(int id);

}
