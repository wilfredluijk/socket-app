package nl.snowmanxl.clickbattle.activities;

import nl.snowmanxl.clickbattle.messages.socket.pl.ClickRaceGameUpdateMessage;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ResetSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StartSocketGameMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.StopSocketGameMessage;
import nl.snowmanxl.clickbattle.model.ClickRaceData;
import nl.snowmanxl.clickbattle.model.GameState;
import nl.snowmanxl.clickbattle.model.ClickRaceScore;
import nl.snowmanxl.clickbattle.model.Player;
import nl.snowmanxl.clickbattle.room.Participant;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ClickRace implements SocketGame {

    // Personal Score
    // TeamScore
    // Find Team For Person
    //
    private final Map<Participant, Player> participantPlayerMap = new HashMap<>();

    private GameState gameState = GameState.WAITING;
    private ClickRaceScore score = new ClickRaceScore();
    private Consumer<SocketMessage> messageConsumer;

    //todo: create config variables
    private int maxPlayerCount = 50;
    private int maxScore = 5000;

    @Override
    public void start(StartSocketGameMessage message) {
        gameState = GameState.STARTED;
        broadcastUpdate();
    }

    @Override
    public void reset(ResetSocketGameMessage message) {
        gameState = GameState.WAITING;
        score = new ClickRaceScore();
        broadcastUpdate();
    }

    @Override
    public void stop(StopSocketGameMessage message) {
        gameState = GameState.STOPPED;
        broadcastUpdate();
    }

    @OnSocketMessage(ScoreForClickRaceMessage.class)
    public void scoreForClickRace(ScoreForClickRaceMessage message) {
        String playerId = message.getPlayerId();
        participantPlayerMap.values().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst().ifPresent(this::scoreForPlayer);
        broadcastUpdate();
    }

    private void scoreForPlayer(Player player) {
        score.scoreFor(player.getTeam(), getWeightedScore(player.getTeam()));
        if(score.limitIsReached(maxScore)) {
            gameState = GameState.FINISHED;
        }
    }

    private int getWeightedScore(int team) {
        var teamCount = participantPlayerMap.values()
                .stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.counting())).get(team);
        return Math.toIntExact(maxPlayerCount / teamCount);
    }

    private void broadcastUpdate() {
        messageConsumer.accept(new ClickRaceGameUpdateMessage(new ClickRaceData(gameState, score)));
    }

    @Override
    public void registerMessageDispatcher(Consumer<SocketMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void consumeParticipantUpdate(Participant participant) {
        AtomicReference<Player> value = new AtomicReference<>();
        boolean deleted = participantPlayerMap.entrySet()
                .removeIf(entry -> {
                    if (entry.getKey().getId().equals(participant.getId())) {
                        value.set(entry.getValue());
                        return true;
                    }
                    return false;
                });
        if (deleted) {
            participantPlayerMap.put(participant, value.get());
        }
    }

    private Map<Integer, Long> getPlayerCountPerTeam() {
        return participantPlayerMap.values()
                .stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.counting()));
    }

    private int getNewTeam() {
        return getPlayerCountPerTeam().entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .limit(1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(1);
    }

    public void consumeParticipantCreation(Participant participant) {
        participantPlayerMap.put(participant, new Player(participant.getId(), getNewTeam()));
    }

    public void consumeParticipantRemoval(String id) {
        participantPlayerMap.entrySet().removeIf(entry -> entry.getKey().getId().equals(id));
    }

    @Override
    public ActivityData getActivityData() {
        return new ClickRaceActivityData(participantPlayerMap, gameState, score);
    }

    @Override
    public String toString() {
        return "ClickRace{" +
                "gameState=" + gameState +
                ", score=" + score +
                '}';
    }
}
