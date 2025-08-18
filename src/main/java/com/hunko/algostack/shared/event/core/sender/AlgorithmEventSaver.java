package com.hunko.algostack.shared.event.core.sender;


import com.hunko.algostack.shared.event.api.AlgorithmEventPublisher;
import com.hunko.algostack.shared.event.core.queue.AlgorithmEventQueue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlgorithmEventSaver implements AlgorithmEventPublisher {

    private final AlgorithmEventQueue algorithmEventQueue;

    @Override
    public void publish(Object event){
        algorithmEventQueue.add(event);
    }
}
