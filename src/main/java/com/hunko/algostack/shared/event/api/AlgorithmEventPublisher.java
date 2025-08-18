package com.hunko.algostack.shared.event.api;

public interface AlgorithmEventPublisher {
    void publish(Object algorithmSolvedEvent);
}
