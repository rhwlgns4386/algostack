package com.hunko.algostack.algorithm.domain.scheduler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InMemoryLastedUpdateStore implements LastedUpdateStore {

    private LocalDateTime lastUpdateTime = LocalDateTime.now();

    @Override
    public LocalDateTime getLastedUpdate() {
        return lastUpdateTime;
    }

    @Override
    public void updateLastedDate(LocalDateTime createdAt) {
        lastUpdateTime = createdAt;
    }
}
