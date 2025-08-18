package com.hunko.algostack.algorithm.domain.event.config;

import com.hunko.algostack.algorithm.domain.event.mapper.EventMapper;
import com.hunko.algostack.algorithm.domain.event.queue.AlgorithmEventQueue;
import com.hunko.algostack.algorithm.domain.event.AlgorithmEventRepository;
import com.hunko.algostack.algorithm.domain.event.sender.AlgorithmEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AlgorithmConfig {

    private final AlgorithmEventRepository algorithmEventRepository;
    private final EventMapper eventMapper;
    @Value("${algostack.queue-size:#{null}}")
    private Integer queueSize;

    @Bean
    public AlgorithmEventSender algorithmEventSender() {
        return new AlgorithmEventSender(algorithmEventQueue());
    }

    @Bean
    public AlgorithmEventQueue algorithmEventQueue() {
        AlgorithmEventQueue queue = new AlgorithmEventQueue(algorithmEventRepository, eventMapper);
        if (queueSize != null) {
            queue.setMaxQueueSize(queueSize);
        }
        return queue;
    }
}
