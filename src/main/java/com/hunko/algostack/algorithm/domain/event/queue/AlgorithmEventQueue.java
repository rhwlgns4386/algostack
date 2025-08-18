package com.hunko.algostack.algorithm.domain.event.queue;

import com.hunko.algostack.algorithm.domain.event.AlgorithmEventRepository;
import com.hunko.algostack.algorithm.domain.event.mapper.EventMapper;
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
    private final EventMapper eventMapper;

    @Setter private int maxQueueSize = 5000;

    @Transactional(dontRollbackOn = JsonDeSerializeException.class)
    public Object poll() throws JsonDeSerializeException{
        AlgorithmEvent algorithmEvent = getAlgorithmEvent();
        if (algorithmEvent == null) return null;
        return eventMapper.toEvent(algorithmEvent);
    }

    @Transactional
    public void add(Object event) throws JsonSerializeException {
        eventRepository.save(eventMapper.toEntity(event));
    }

    private AlgorithmEvent getAlgorithmEvent() {
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
        return algorithmEvent;
    }

    private void initQueue() {
        List<AlgorithmEvent> histories = eventRepository.findByIsSuccessIsFalse(Limit.of(maxQueueSize));
        if (!histories.isEmpty()) {
            QUEUE.addAll(histories);
        }
    }
}
