package nl.snowmanxl.clickbattle.model;

import nl.snowmanxl.clickbattle.gametypes.ClickRace;
import nl.snowmanxl.clickbattle.gametypes.SmallToLarge;
import nl.snowmanxl.clickbattle.gametypes.SocketGame;

public enum GameType {
    CLICK_RACE, SMALL_TO_LARGE;

    public static SocketGame getGame(GameType type) {
        switch (type){
            case CLICK_RACE:
                return new ClickRace();
            case SMALL_TO_LARGE:
                return new SmallToLarge();
        }
        //Unreachable
        return null;
    }
}
