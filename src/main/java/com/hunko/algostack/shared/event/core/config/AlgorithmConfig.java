package com.hunko.algostack.shared.event.core.config;

import com.hunko.algostack.shared.event.core.mapper.EventMapper;
import com.hunko.algostack.shared.event.core.queue.AlgorithmEventQueue;
import com.hunko.algostack.shared.event.core.AlgorithmEventRepository;
import com.hunko.algostack.shared.event.core.sender.AlgorithmEventSaver;
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
    public AlgorithmEventSaver algorithmEventSender() {
        return new AlgorithmEventSaver(algorithmEventQueue());
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
