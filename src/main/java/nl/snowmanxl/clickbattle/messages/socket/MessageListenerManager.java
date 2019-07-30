package nl.snowmanxl.clickbattle.messages.socket;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class MessageListenerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerManager.class);

    private final Map<Integer, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>>> consumersMaps
            = new HashMap<>();
    private Reflections reflections;
    private int roomId;

    public <T> void createListeners(int roomId, T inst) {
        reflections = new Reflections(inst.getClass(), new MethodAnnotationsScanner());
        this.roomId = roomId;
        initiateListeners(inst);
    }

    private <T> void initiateListeners(T instance) {
        Map<Integer, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>>> collect = new HashMap<>();
        collect.put(roomId, reflections.getMethodsAnnotatedWith(OnSocketMessage.class).stream()
                .collect(Collectors.groupingBy(method -> method.getDeclaredAnnotation(OnSocketMessage.class).value(),
                        Collectors.mapping(method -> mapMethodToConsumer(method, instance), Collectors.toSet()))));
        addListenersToMapping(collect);
    }

    @SuppressWarnings("unchecked")
    private <T> Consumer<SocketMessage> mapMethodToConsumer(Method method, T implInstance) {
        Class<? extends SocketMessage> methodParameterType = method.getDeclaredAnnotation(OnSocketMessage.class).value();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle factory = LambdaMetafactory.metafactory(
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

    public final void addListenersToMapping(Map<Integer, Map<Class<? extends SocketMessage>,
            Set<Consumer<SocketMessage>>>> newConsumers) {
        consumersMaps.putAll(newConsumers);
    }

    public Map<Integer, Map<Class<? extends SocketMessage>, Set<Consumer<SocketMessage>>>> getConsumersMaps() {
        return consumersMaps;
    }

    @MessageMapping("/{id}/message")
    public void message(@DestinationVariable Integer id, SocketMessage message) {
        Optional.ofNullable(consumersMaps.get(id)).ifPresent(
                consumersMap -> Optional.ofNullable(consumersMap.get(message.getClass())).ifPresent(
                        consumers -> consumers.forEach(
                                consumer -> consumer.accept(message))));
    }


}
