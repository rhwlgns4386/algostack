package com.hunko.algostack.algorithm.domain.event.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.algorithm.domain.event.queue.AlgorithmEventQueue;
import com.hunko.algostack.algorithm.domain.event.AlgorithmEventRepository;
import com.hunko.algostack.algorithm.domain.event.sender.AlgorithmEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AlgorithmConfig {

    private final AlgorithmEventRepository algorithmEventRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public AlgorithmEventSender algorithmEventSender() {
        return new AlgorithmEventSender(algorithmEventQueue());
    }

    @Bean
    public AlgorithmEventQueue algorithmEventQueue() {
        return new AlgorithmEventQueue(algorithmEventRepository, objectMapper);
    }
}
