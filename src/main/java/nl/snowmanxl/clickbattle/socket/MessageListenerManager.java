package nl.snowmanxl.clickbattle.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.snowmanxl.clickbattle.messages.socket.OnSocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.SocketMessage;
import nl.snowmanxl.clickbattle.messages.socket.bl.ScoreForClickRaceMessage;
import nl.snowmanxl.clickbattle.messages.socket.pl.RoomUpdateMessage;
import nl.snowmanxl.clickbattle.model.ClickRaceScore;
import nl.snowmanxl.clickbattle.model.Player;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Controller
public class MessageListenerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerManager.class);
    private final Map<Integer, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>>> roomMessageListeners
            = new HashMap<>();

    public void removeRoomListeners(int roomId) {
        roomMessageListeners.remove(roomId);
    }

//    @MessageMapping("/create/{uuid}")
//    @SendTo("/topic/board/{uuid}")
//    public String createGame(@DestinationVariable String uuid) {
//        LOGGER.debug("Triggered Listener: {}", uuid);
//        return "test";
//    }

    @MessageMapping("/messageToRoom/{roomId}")
    public void messageToRoom(@DestinationVariable Integer roomId,
                              @Payload Message<ScoreForClickRaceMessage> rawMessage) {
        LOGGER.debug("Received raw message: {}", rawMessage);
        var message = rawMessage.getPayload();
        LOGGER.debug("Received message: {}", message);

        Optional.ofNullable(roomMessageListeners.get(roomId))
                .flatMap(consumersMap -> Optional.ofNullable(consumersMap.get(message.getClass())))
                .ifPresent(consumers -> consumers.forEach(
                        consumer -> consumer.accept(message)));
    }

    @SafeVarargs
    public final <T> void createRoomBasedListeners(int roomId, T... inst) {
        Arrays.stream(inst).forEach(instance -> createRoomBasedListeners(roomId, instance));
    }

    public <T> void createRoomBasedListeners(int roomId, T inst) {
        var reflections = new Reflections(inst.getClass(), new MethodAnnotationsScanner());
        Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>> listenersPerType
                = reflections.getMethodsAnnotatedWith(OnSocketMessage.class).stream()
                .collect(Collectors.groupingBy(method -> method.getDeclaredAnnotation(OnSocketMessage.class).value(),
                        Collectors.mapping(method -> mapMethodToConsumer(method, inst), Collectors.toSet())));

        Optional.ofNullable(listenersPerType).ifPresent(listeners -> addToListeners(roomId, listenersPerType));
    }

    private void addToListeners(int roomId, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>> maps) {
        Map<Integer, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>>> tmpMap = new HashMap<>();
        tmpMap.put(roomId, maps);
        roomMessageListeners.putAll(tmpMap);
    }

    @SuppressWarnings("unchecked")
    private <T> Consumer<SocketMessage> mapMethodToConsumer(Method method, T implInstance) {
        var methodParameterType = method.getDeclaredAnnotation(OnSocketMessage.class).value();
        var lookup = MethodHandles.lookup();
        try {
            var factory = LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(Consumer.class, method.getDeclaringClass()),
                    MethodType.methodType(void.class, Object.class),
                    lookup.unreflect(method),
                    MethodType.methodType(void.class, methodParameterType)
            ).getTarget();
            return (Consumer<SocketMessage>) factory.invoke(implInstance);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Cannot continue due to encountered exception: ", throwable);
        }
    }

}
