package nl.snowmanxl.clickbattle.room;

import nl.snowmanxl.clickbattle.gametypes.SocketGame;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.GameType;

import java.util.List;
import java.util.Map;

public interface GameRoom extends Room {

    Map<Integer, Integer> scoreForPlayer(String playerId, int points);

    void startGame();

    void resetGame();

    void stopGame();

    GameState getGameState();

    int getPlayerCount();

    GameType getGameType();

    int getMaxPlayerCount();

    List<Participant> getPlayers();

    SocketGame getGame();
}
