package com.hunko.algostack.algorithm.domain.event.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.algorithm.domain.event.AlgorithmEventRepository;
import com.hunko.algostack.algorithm.domain.event.exception.JsonDeSerializeException;
import com.hunko.algostack.algorithm.domain.event.exception.JsonSerializeException;
import com.hunko.algostack.algorithm.domain.event.entity.AlgorithmEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Limit;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class AlgorithmEventQueue {

    private static final ConcurrentLinkedQueue<AlgorithmEvent> QUEUE = new ConcurrentLinkedQueue<>();

    private final AlgorithmEventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Setter
    private int MAX_QUEUE_SIZE = 5000;

    @Transactional
    public Object poll() {
        AlgorithmEvent algorithmEvent = QUEUE.poll();
        if (algorithmEvent == null) {
            synchronized (AlgorithmEventQueue.class) {
                algorithmEvent = QUEUE.poll();
                if (algorithmEvent == null) {
                    initQueue();
                    algorithmEvent = QUEUE.poll();
                }
            }
        }
        if (algorithmEvent == null) {
            return null;
        }
        algorithmEvent.success();

        try {
            Class<?> aClass = Class.forName(algorithmEvent.getEventName());
            return objectMapper.readValue(algorithmEvent.getData(), aClass);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            throw new JsonDeSerializeException(String.format("클래스명 : %s data : %s",algorithmEvent.getEventName(), algorithmEvent.getData()), e);
        }
    }

    @Transactional
    public void add(Object event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            String eventName = event.getClass().getName();
            AlgorithmEvent algorithmEvent = new AlgorithmEvent(eventName, json);
            eventRepository.save(algorithmEvent);
        } catch (JsonProcessingException e) {
            throw new JsonSerializeException("Json 변환 불가", e);
        }
    }

    private void initQueue() {
        List<AlgorithmEvent> histories = eventRepository.findByIsSuccessIsFalse(Limit.of(MAX_QUEUE_SIZE));
        if (!histories.isEmpty()) {
            QUEUE.addAll(histories);
        }
    }
}
