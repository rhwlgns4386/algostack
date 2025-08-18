package com.hunko.algostack.algorithm.domain.event.sender;


import com.hunko.algostack.algorithm.domain.event.queue.AlgorithmEventQueue;
import com.hunko.algostack.algorithm.domain.service.event.AlgorithmSolvedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlgorithmEventSender {

    private final AlgorithmEventQueue algorithmEventQueue;

    public void sendFrom(AlgorithmSolvedEvent algorithmSolvedEvent){
        algorithmEventQueue.add(algorithmSolvedEvent);
    }
}
