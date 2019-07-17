package nl.snowmanxl.clickbattle.room;

public interface Room {

    int getId();

    String addPlayer(Participant player);

    void updatePlayer(Participant participant);

}
