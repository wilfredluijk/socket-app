package nl.snowmanxl.clickbattle.model;

public class Player {
    String id;
    int team;

    public Player() {
    }

    public Player(String id, int team) {
        this.id = id;
        this.team = team;
    }

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
                "id='" + id + '\'' +
                ", team=" + team +
                '}';
    }
}
