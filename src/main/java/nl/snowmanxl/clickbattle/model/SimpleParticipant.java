package nl.snowmanxl.clickbattle.model;

import nl.snowmanxl.clickbattle.room.Participant;

public class SimpleParticipant implements Participant {
    private String name;
    private String id;
    private int team;

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

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", team=" + team +
                '}';
    }
}
