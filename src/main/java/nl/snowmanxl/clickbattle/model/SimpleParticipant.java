package nl.snowmanxl.clickbattle.model;

import nl.snowmanxl.clickbattle.room.Participant;

public class SimpleParticipant implements Participant {
    private String name;
    private String id;

    public SimpleParticipant(String id) {
        this.id = id;
    }

    public SimpleParticipant() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
